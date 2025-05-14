package org.example.backend.model.announcement;

import jakarta.persistence.*;
import lombok.Data;
import org.example.backend.model.tournament.Tournament;

import java.time.Instant;

@Data
@Entity
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tournament_id",nullable = false)
    private Tournament tournament;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    @Column(nullable = false)
    private String content;
}
