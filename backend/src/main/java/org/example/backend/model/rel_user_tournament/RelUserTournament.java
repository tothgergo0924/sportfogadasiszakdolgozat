package org.example.backend.model.rel_user_tournament;

import jakarta.persistence.*;
import lombok.Data;
import org.example.backend.model.tournament.Tournament;
import org.example.backend.model.user.User;

@Data
@Entity
@Table(name = "rel_user_tournament",uniqueConstraints = @UniqueConstraint(columnNames = {"tournament","participant"}))
public class RelUserTournament {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tournament_id", nullable = false)
    private Tournament tournament;

    @ManyToOne
    @JoinColumn(name = "participant_id", nullable = false)
    private User participant;

    private Integer points;
}
