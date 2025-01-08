package com.piisw.kino.persistence.entities;

import java.util.ArrayList;
import java.util.List;

import com.piisw.kino.persistence.enums.TicketStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "code"))
public class TicketEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private String code;

    @ManyToOne
    private AiringEntity airing;

    @Enumerated(EnumType.ORDINAL)
    @Column
    private TicketStatus status = TicketStatus.VALID;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String phoneNumber;

    @Column
    private List<Integer> seatsTaken = new ArrayList<Integer>();
    // Użycie list wewnątrz wiersza do trzymania które miejsca są zajęte przez który bilet jest niekoniecznie poprawne,
    // (p. Nguyen uczył o postaciach normalnych...) ALE, nie będę korzystać z tego do skomplikowanych operacji. Jedyne
    // co, to te listy będą dodawane do innej listy, dlatego uważam, że użycie tego w taki sposób jest uzasadnione.
}
