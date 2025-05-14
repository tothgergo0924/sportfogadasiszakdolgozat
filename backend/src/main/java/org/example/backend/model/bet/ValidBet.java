package org.example.backend.model.bet;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.example.backend.model.bet.BetValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = BetValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidBet {
    String message() default "Invalid bet based on betType";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
