package org.example.backend.model.tournament;

import jakarta.persistence.*;
import lombok.Data;
import org.example.backend.model.rel_tournament_league.RelTournamentLeague;
import org.example.backend.model.sport.Sport;
import org.example.backend.model.user.User;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class Tournament {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String reward;

    @ManyToOne
    @JoinColumn(name = "creator_user_id", nullable = false)
    private User creator;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private TournamentStatus status;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    @ManyToOne
    @JoinColumn(name = "sport_id")
    private Sport sportType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "winner_user_id")
    private User winner;

    @OneToMany(mappedBy = "tournament", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RelTournamentLeague> tournamentLeagues;

}
