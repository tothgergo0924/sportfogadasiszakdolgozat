package org.example.backend.model.user;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.backend.model.friend_request.FriendRequest;
import org.example.backend.model.friendship.Friendship;
import org.example.backend.model.rel_achievement_user.RelAchievementUser;
import org.example.backend.model.rel_notification_user.RelNotificationUser;
import org.example.backend.model.tournament.Tournament;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    private String profilePicUrl;

    private Integer streak;

    private Integer level;

    private String displayName;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RelAchievementUser> achievement;

    @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FriendRequest> sentFriendRequests;

    @OneToMany(mappedBy = "receiver", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FriendRequest> receivedFriendRequests;

    @OneToMany(mappedBy = "initiator")
    private List<Friendship> friendshipsInitiated;

    @OneToMany(mappedBy = "receiver")
    private List<Friendship> friendshipsReceived;

    @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tournament> createdTournaments;

    @OneToMany(mappedBy = "winner", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tournament> wonTournaments;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RelNotificationUser> notifications;

    @Transient
    public List<User> getFriends() {
        List<User> friends = new ArrayList<>();
        for (Friendship f : friendshipsInitiated) {
            friends.add(f.getInitiator());
        }
        for (Friendship f : friendshipsReceived) {
            friends.add(f.getReceiver());
        }
        return friends;
    }
}
