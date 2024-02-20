package com.jlcc.examen.dto;

import java.util.List;

public class PokemonDTO {
	private String name;
    private int apiAccessCount;
    private List<String> evolutionChain;
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getApiAccessCount() {
		return apiAccessCount;
	}
	public void setApiAccessCount(int apiAccessCount) {
		this.apiAccessCount = apiAccessCount;
	}
	public List<String> getEvolutionChain() {
		return evolutionChain;
	}
	public void setEvolutionChain(List<String> evolutionChain) {
		this.evolutionChain = evolutionChain;
	}
    
    
    
    


}
