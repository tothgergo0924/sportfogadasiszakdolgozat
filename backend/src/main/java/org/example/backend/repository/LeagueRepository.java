package org.example.backend.repository;

import org.example.backend.model.league.League;
import org.example.backend.model.league.LeagueId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeagueRepository extends JpaRepository<League, LeagueId> {
}
