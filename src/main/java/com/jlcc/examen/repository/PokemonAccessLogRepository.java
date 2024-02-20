package com.jlcc.examen.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jlcc.examen.model.PokemonAccessLog;

public interface PokemonAccessLogRepository extends JpaRepository<PokemonAccessLog, Long> {

}
