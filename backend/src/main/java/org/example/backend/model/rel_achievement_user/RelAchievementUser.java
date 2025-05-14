package org.example.backend.model.rel_achievement_user;


import jakarta.persistence.*;
import lombok.Data;
import org.example.backend.model.achievement.Achievement;
import org.example.backend.model.user.User;

@Data
@Entity
@Table(name="rel_achievement_user",uniqueConstraints = @UniqueConstraint(columnNames = {"user_id","achievement_id"}))
public class RelAchievementUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "achievement_id", nullable = false)
    private Achievement achievement;
}
