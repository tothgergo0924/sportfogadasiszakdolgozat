package org.example.backend.model.sport;

import jakarta.persistence.*;
import lombok.Data;
import org.example.backend.model.sport.SportType;

@Entity
@Data
public class Sport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private SportType type;
}
