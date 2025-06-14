package org.example.backend.service;

import jakarta.transaction.Transactional;
import org.example.backend.configuration.auth.JwtAuthenticationFilter;
import org.example.backend.dto.TournamentCreateDTO;
import org.example.backend.dto.TournamentUpdateDTO;
import org.example.backend.dto.UserDTO;
import org.example.backend.model.rel_tournament_league.RelTournamentLeague;
import org.example.backend.model.tournament.Tournament;
import org.example.backend.model.tournament.TournamentStatus;
import org.example.backend.repository.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class TournamentService {

    @Autowired
    private TournamentRepository tournamentRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private SportService sportService;

    @Autowired
    private LeagueService leagueService;

    @Autowired
    private RelTournamentLeagueService relTournamentLeagueService;

    public List<Tournament> getAllTournaments() {
        return tournamentRepository.findAll();
    }

    public Tournament createTournament(TournamentCreateDTO tournamentCreateDTO) {
        Tournament tournament=new Tournament();

        tournament.setReward(tournamentCreateDTO.getReward());
        UserDTO admin= Objects.requireNonNull(JwtAuthenticationFilter.AuthenticatedUser()).getUserDTO();

        if(admin==null){
            throw new IllegalArgumentException("Admin is not authenticated!");
        }

        tournament.setCreator(userService.getUserById(admin.getId()));
        tournament.setName(tournamentCreateDTO.getName());

        if( LocalDateTime.now().isAfter(tournamentCreateDTO.getStartDate())){
            tournament.setStatus(TournamentStatus.ONGOING);
        }else{
            tournament.setStatus(TournamentStatus.UPCOMING);
        }

        tournament.setStartDate(tournamentCreateDTO.getStartDate());
        tournament.setEndDate(tournamentCreateDTO.getEndDate());
        tournament.setSportType(sportService.getSportByName("Football"));

        Tournament savedTournament=tournamentRepository.save(tournament);
        tournamentCreateDTO.getLeagues().stream().forEach(leagueId->{
            RelTournamentLeague relTournamentLeague=new RelTournamentLeague();
            relTournamentLeague.setTournament(savedTournament);
            relTournamentLeague.setLeague(leagueService.getLeagueByLeagueId(leagueId));

            relTournamentLeagueService.save(relTournamentLeague);
        });

        return savedTournament;
    }

    @Transactional
    public Tournament updateTournament(TournamentUpdateDTO tournamentUpdateDTO,Long id) {
        Tournament tournament = tournamentRepository.findById(id).orElseThrow(() -> new RuntimeException("Tournament not found"));

        tournament.setReward(tournamentUpdateDTO.getReward());
        tournament.setName(tournamentUpdateDTO.getName());
        tournament.setStartDate(tournamentUpdateDTO.getStartDate());
        tournament.setEndDate(tournamentUpdateDTO.getEndDate());

        if( LocalDateTime.now().isAfter(tournamentUpdateDTO.getStartDate())){
            tournament.setStatus(TournamentStatus.ONGOING);
        }else{
            tournament.setStatus(TournamentStatus.UPCOMING);
        }

        tournament.setWinner(userService.getUserById(tournamentUpdateDTO.getWinner()));

        Tournament savedTournament=tournamentRepository.save(tournament);

        relTournamentLeagueService.deleteRelationsByTournamentId(id);
        tournamentUpdateDTO.getLeagues().stream().forEach(leagueId->{
            RelTournamentLeague relTournamentLeague=new RelTournamentLeague();
            relTournamentLeague.setTournament(savedTournament);
            relTournamentLeague.setLeague(leagueService.getLeagueByLeagueId(leagueId));

            relTournamentLeagueService.save(relTournamentLeague);
        });

        return savedTournament;
    }

    @Transactional
    public void deleteTournament(Long id) {
        tournamentRepository.deleteById(id);
    }
}
