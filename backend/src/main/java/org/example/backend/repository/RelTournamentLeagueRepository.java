package org.example.backend.repository;

import org.example.backend.model.rel_tournament_league.RelTournamentLeague;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RelTournamentLeagueRepository extends JpaRepository<RelTournamentLeague,Long> {


    void deleteAllByTournament_Id(Long tournamentId);
}
