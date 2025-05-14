package org.example.backend.model.match;

import jakarta.persistence.*;
import lombok.Data;
import org.example.backend.model.league.League;

import java.time.LocalDateTime;

@Entity
@Data
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "league_season", referencedColumnName = "season"),
            @JoinColumn(name = "league_league_id", referencedColumnName = "leagueId")
    })
    private League league;

    @Column(nullable = false)
    private LocalDateTime matchDate;

    @Column(nullable = false)
    private Integer homeScore;

    @Column(nullable = false)
    private Integer awayScore;

    @Column(nullable = false)
    private Double homeOdd;

    @Column(nullable = false)
    private Double awayOdd;

    @Column(nullable = false)
    private Double tieOdd;

    @Enumerated(EnumType.STRING)
    private MatchStatus status;

    @Enumerated(EnumType.STRING)
    private MatchType type;
}
