package org.example.backend.model.rel_tournament_league;

import jakarta.persistence.*;
import lombok.Data;
import org.example.backend.model.league.League;
import org.example.backend.model.tournament.Tournament;

@Entity
@Data
@Table(
        name = "rel_tournament_league",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"league_season", "league_league_id", "tournament_id"}
        )
)public class RelTournamentLeague {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "league_season", referencedColumnName = "season"),
            @JoinColumn(name = "league_league_id", referencedColumnName = "leagueId")
    })
    private League league;

    @ManyToOne
    @JoinColumn(name = "tournament_id",nullable = false)
    private Tournament tournament;

}
