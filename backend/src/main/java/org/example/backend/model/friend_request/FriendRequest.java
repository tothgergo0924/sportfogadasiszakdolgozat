package org.example.backend.model.friend_request;


import jakarta.persistence.*;
import lombok.Data;
import org.example.backend.model.user.User;

import java.time.Instant;

@Data
@Entity
public class FriendRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestId;

    @ManyToOne
    @JoinColumn(name = "receiver_user_id", nullable = false)
    private User receiver;

    @ManyToOne
    @JoinColumn(name = "sender_user_id", nullable = false)
    private User sender;

    @Enumerated(EnumType.STRING)
    private FriendRequestStatus status;

    @Column(nullable = false, updatable = false)
    private Instant sendAt = Instant.now();
}
