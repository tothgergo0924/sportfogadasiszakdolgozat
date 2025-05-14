package org.example.backend.model.bet;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class BetValidator implements ConstraintValidator<ValidBet, Bet> {
    @Override
    public void initialize(ValidBet constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Bet bet, ConstraintValidatorContext context) {
        if (bet == null || bet.getType() == null) {
            return true;
        }

        boolean valid = true;

        switch (bet.getType()) {
            case EXACT_SCORE:
                valid = bet.getPredictedHomeScore() != null &&
                        bet.getPredictedAwayScore() != null &&
                        bet.getPredictedResult() == null;
                break;
            case MATCH_RESULT:
                valid = bet.getPredictedResult() != null &&
                        bet.getPredictedHomeScore() == null &&
                        bet.getPredictedAwayScore() == null;
                break;
        }

        return valid;

    }
}
