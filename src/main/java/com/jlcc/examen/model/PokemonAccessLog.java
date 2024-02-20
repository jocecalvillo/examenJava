package com.jlcc.examen.model;

import java.time.LocalDateTime;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity

public class PokemonAccessLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

 
    private LocalDateTime accessTime;

   
    private String apiUrl;


	public LocalDateTime getAccessTime() {
		return accessTime;
	}


	public void setAccessTime(LocalDateTime accessTime) {
		this.accessTime = accessTime;
	}


	public String getApiUrl() {
		return apiUrl;
	}


	public void setApiUrl(String apiUrl) {
		this.apiUrl = apiUrl;
	}
    
    

 
}
