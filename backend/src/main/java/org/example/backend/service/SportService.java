package org.example.backend.service;

import org.example.backend.model.sport.Sport;
import org.example.backend.repository.SportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SportService {

    @Autowired
    private SportRepository sportRepository;


    public Sport getSportByName(String name){
        Optional<Sport> sport=sportRepository.getSportByName(name);

        if(sport.isEmpty()){
            throw new IllegalArgumentException("Sport not found: "+name);
        }
        return sport.get();

    }

}
