package org.example.backend.service;

import jakarta.transaction.Transactional;
import org.example.backend.model.rel_tournament_league.RelTournamentLeague;
import org.example.backend.repository.RelTournamentLeagueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RelTournamentLeagueService {

    @Autowired
    private RelTournamentLeagueRepository relTournamentLeagueRepository;

    @Transactional
    public RelTournamentLeague save(RelTournamentLeague relTournamentLeague){
        return relTournamentLeagueRepository.save(relTournamentLeague);
    }

    @Transactional
    public void deleteRelationsByTournamentId(Long id){
        relTournamentLeagueRepository.deleteAllByTournament_Id(id);
    }
}
