package org.example.backend.model.bet;

import jakarta.persistence.*;
import lombok.Data;
import org.example.backend.model.match.Match;
import org.example.backend.model.user.User;

import java.time.Instant;

@ValidBet
@Data
@Entity
public class Bet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name="match_id",nullable = false)
    private Match match;

    @Enumerated(EnumType.STRING)
    private BetType type;

    @Enumerated(EnumType.STRING)
    private BetResult predictedResult;

    private Integer predictedHomeScore;

    private Integer predictedAwayScore;

    @Enumerated(EnumType.STRING)
    private BetStatus status;

    @Column(nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    @Column(nullable = false)
    private Integer stake;


    private Double odds;

    @PrePersist
    @PreUpdate
    private void calculateOdds() {
        this.odds = calculateBasedOnLogic();
    }

    private double calculateBasedOnLogic() {
        if(type==BetType.EXACT_SCORE){
            if(predictedHomeScore>predictedAwayScore){
                return match.getHomeOdd()*2;
            }else if(predictedAwayScore>predictedHomeScore){
                return match.getAwayOdd()*2;
            }else{
                return match.getTieOdd()*2;
            }
        }else{
            if(predictedResult==BetResult.HOME_WIN){
                return match.getHomeOdd();
            } else if (predictedResult==BetResult.AWAY_WIN) {
                return match.getAwayOdd();
            }else{
                return match.getTieOdd();
            }
        }
    }
}
