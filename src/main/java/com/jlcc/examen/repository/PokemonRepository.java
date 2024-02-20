package com.jlcc.examen.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jlcc.examen.model.Pokemon;

public interface PokemonRepository extends JpaRepository<Pokemon, Long> {

}
