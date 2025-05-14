package org.example.backend.model.league;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Data;
import org.example.backend.model.league.LeagueId;

@Data
@Entity
public class League {
    @EmbeddedId
    private LeagueId id;

    @Column(nullable = false)
    private String name;
}
