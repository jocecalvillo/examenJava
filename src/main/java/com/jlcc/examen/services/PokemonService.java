package com.jlcc.examen.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.jlcc.examen.model.Pokemon;
import com.jlcc.examen.model.PokemonAccessLog;
import com.jlcc.examen.repository.PokemonAccessLogRepository;
import com.jlcc.examen.repository.PokemonRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Service
public class PokemonService {

	
	 private static final Logger logger = LogManager.getLogger(PokemonService.class);
	 
	 
    @Autowired
    private PokemonRepository pokemonRepository;

    @Autowired
    private PokemonAccessLogRepository accessLogRepository;

    @Autowired
    private RestTemplate restTemplate;

    public Pokemon getPokemonById(Long id) {
    	
    	
    	
        String apiUrl = "https://pokeapi.co/api/v2/pokemon/" + id;

        try {
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    apiUrl, HttpMethod.GET, null, new ParameterizedTypeReference<Map<String, Object>>() {});

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                // Guardar acceso a la API en la base de datos
                saveAccessLog(apiUrl);

                // Obtener datos del Pokémon desde la respuesta de la API
                Map<String, Object> pokemonData = response.getBody();
                String name = (String) pokemonData.get("name");
                List<String> evolutionChainNames = getEvolutionChainNamesFromApi(id);

                // Guardar algunos datos del Pokémon en la base de datos
                Pokemon pokemon = new Pokemon();
                pokemon.setName(name);

                // Verificar si hay evoluciones antes de guardar en la base de datos
                if (!evolutionChainNames.isEmpty()) {
                    String evolutionChain = evolutionChainNames.stream().collect(Collectors.joining(","));
                    pokemon.setEvolutionChain(evolutionChain);
                }

                pokemonRepository.save(pokemon);

                return pokemon;
            } else {
                // Manejar errores de la API
                return null;
            }
        } catch (RestClientException e) {
            // Manejar errores de la comunicación con la API
        	 logger.error(e);
            return null;
        }
    }

    private void saveAccessLog(String apiUrl) {
        PokemonAccessLog accessLog = new PokemonAccessLog();
        accessLog.setAccessTime(LocalDateTime.now());
        accessLog.setApiUrl(apiUrl);
        accessLogRepository.save(accessLog);
    }

    private List<String> getEvolutionChainNamesFromApi(Long id) {
        List<String> evolutionChainNames = new ArrayList<>();

        String evolutionChainUrl = "https://pokeapi.co/api/v2/evolution-chain/" + id + "/";

        try {
            ResponseEntity<Map<String, Object>> evolutionChainResponse = restTemplate.exchange(
                    evolutionChainUrl, HttpMethod.GET, null, new ParameterizedTypeReference<Map<String, Object>>() {});

            if (evolutionChainResponse.getStatusCode() == HttpStatus.OK && evolutionChainResponse.getBody() != null) {
                Map<String, Object> responseMap = evolutionChainResponse.getBody();
                @SuppressWarnings("unchecked")
				Map<String, Object> chainData = (Map<String, Object>) responseMap.get("chain");
                
               
                parseEvolutionChainRecursiveForSpecies(chainData, evolutionChainNames);
            }
        } catch (RestClientException e) {
        	logger.error(e);
        }

        return evolutionChainNames;
    }

    
    @SuppressWarnings("unchecked")
	private void parseEvolutionChainRecursiveForSpecies(Map<String, Object> chainData, List<String> speciesNames) {
        if (chainData != null) {
            Map<String, Object> speciesData = (Map<String, Object>) chainData.get("species");
            if (speciesData != null) {
                String speciesName = (String) speciesData.get("name");
                speciesNames.add(speciesName);

                List<Map<String, Object>> evolvesTo = (List<Map<String, Object>>) chainData.get("evolves_to");
                if (evolvesTo != null && !evolvesTo.isEmpty()) {
                    for (Map<String, Object> evolution : evolvesTo) {
                        parseEvolutionChainRecursiveForSpecies(evolution, speciesNames);
                    }
                }
            }
        }
   
    }

}