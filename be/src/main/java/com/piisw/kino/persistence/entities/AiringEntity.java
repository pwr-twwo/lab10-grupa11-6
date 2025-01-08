package com.piisw.kino.persistence.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.piisw.kino.persistence.enums.CinemaHall;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class AiringEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private LocalDateTime time;

    @Enumerated(EnumType.ORDINAL)
    @Column
    private CinemaHall type;

    @OneToMany
    private List<TicketEntity> tickets = new ArrayList<TicketEntity>();

    @ManyToOne
    private MovieEntity movie;
}
