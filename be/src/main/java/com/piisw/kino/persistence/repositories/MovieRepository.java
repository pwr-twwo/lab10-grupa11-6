package com.piisw.kino.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.piisw.kino.persistence.entities.MovieEntity;

public interface MovieRepository extends JpaRepository<MovieEntity, Long> {}
