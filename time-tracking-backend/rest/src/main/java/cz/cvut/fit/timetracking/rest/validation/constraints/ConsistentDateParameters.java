package cz.cvut.fit.timetracking.rest.validation.constraints;

import cz.cvut.fit.timetracking.rest.validation.ConsistentDateParametersValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ConsistentDateParametersValidator.class)
@Target({ ElementType.TYPE })
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface ConsistentDateParameters {

    String message() default "Field is not after another specified field.";

    String field();

    String isAfter();

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
