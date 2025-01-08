package com.piisw.kino.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.piisw.kino.persistence.entities.AiringEntity;

public interface AiringRepository extends JpaRepository<AiringEntity, Long> {}
