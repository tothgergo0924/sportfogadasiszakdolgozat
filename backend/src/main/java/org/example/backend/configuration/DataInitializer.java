package org.example.backend.configuration;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.backend.model.sport.Sport;
import org.example.backend.model.sport.SportType;
import org.example.backend.model.user.Role;
import org.example.backend.model.user.User;
import org.example.backend.repository.SportRepository;
import org.example.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;

    private final SportRepository sportRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) {

        createAdminIfNotExist();
        createSportIfNotExist();
    }

    private void createAdminIfNotExist() {
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@gmail.com");
            admin.setRole(Role.ADMIN);
            admin.setPassword(passwordEncoder.encode("asd"));

            userRepository.save(admin);
        }
    }

    private void createSportIfNotExist() {
        if (sportRepository.getSportByName("Football").isEmpty()) {
            Sport sport = new Sport();
            sport.setName("Football");
            sport.setDescription("Football is the world’s most popular sport, played by two teams of eleven players on a rectangular field with a goal at each end. The objective is to score by getting the ball into the opposing team’s goal using any part of the body except the hands and arms (goalkeepers are the exception). It emphasizes teamwork, strategy, and skill, and is known for its global competitions such as the FIFA World Cup and UEFA Champions League.");
            sport.setType(SportType.TEAM);

            sportRepository.save(sport);
        }
    }

}
