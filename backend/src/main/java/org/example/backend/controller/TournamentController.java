package org.example.backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.backend.dto.TournamentCreateDTO;
import org.example.backend.dto.TournamentUpdateDTO;
import org.example.backend.model.tournament.Tournament;
import org.example.backend.service.TournamentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/rest/tournaments")
public class TournamentController {

    private final TournamentService tournamentService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Tournament> listAllTournaments(){
        return tournamentService.getAllTournaments();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Tournament> createTournament(@Valid @RequestBody TournamentCreateDTO tournamentCreateDTO){
        return ResponseEntity.ok(tournamentService.createTournament(tournamentCreateDTO));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Tournament> editTournament(@PathVariable Long id,@Valid @RequestBody TournamentUpdateDTO tournamentUpdateDTO){
        return ResponseEntity.ok(tournamentService.updateTournament(tournamentUpdateDTO,id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> deleteTournament(@PathVariable Long id){
        tournamentService.deleteTournament(id);
        return ResponseEntity.ok("Deletion successful");
    }
}
