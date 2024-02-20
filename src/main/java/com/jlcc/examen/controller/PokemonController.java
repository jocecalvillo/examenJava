package com.jlcc.examen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jlcc.examen.model.Pokemon;
import com.jlcc.examen.services.PokemonService;

@RestController
@RequestMapping("/api/pokemon")
public class PokemonController {
	
	@Autowired
    private PokemonService pokemonService;

	 @GetMapping("/{id}")
	    public ResponseEntity<Pokemon> getPokemon(@PathVariable Long id) {
	        Pokemon pokemon = pokemonService.getPokemonById(id);
	        return ResponseEntity.ok(pokemon);
	    }
}
