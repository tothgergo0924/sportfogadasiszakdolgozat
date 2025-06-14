package org.example.backend.service;

import org.example.backend.model.league.League;
import org.example.backend.model.league.LeagueId;
import org.example.backend.model.user.User;
import org.example.backend.repository.LeagueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LeagueService {

    @Autowired
    private LeagueRepository leagueRepository;

    public League getLeagueByLeagueId(LeagueId leagueId){
        Optional<League> league = leagueRepository.findById(leagueId);
        if(league.isEmpty()) {
            throw new IllegalArgumentException("League not found.");
        }

        return league.get();
    }
}
