package org.example.backend.repository;

import org.example.backend.model.sport.Sport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SportRepository extends JpaRepository<Sport,Long> {

    Optional<Sport> getSportByName(String name);
}
