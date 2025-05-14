package org.example.backend.model.friendship;


import jakarta.persistence.*;
import lombok.Data;
import org.example.backend.model.user.User;

import java.util.Date;

@Data
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"initiator_user_id","receiver_user_id"}))
public class Friendship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "initiator_user_id", nullable = false)
    private User initiator;

    @ManyToOne
    @JoinColumn(name = "receiver_user_id", nullable = false)
    private User receiver;

    @Column(nullable = false,updatable = false)
    private Date createdAt=new Date();

}
