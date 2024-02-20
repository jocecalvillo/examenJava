package com.jlcc.examen.model;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class Pokemon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    private String name;

  
    private String evolutionChain;


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getEvolutionChain() {
		return evolutionChain;
	}


	public void setEvolutionChain(String evolutionChain) {
		this.evolutionChain = evolutionChain;
	}
    
    

}
