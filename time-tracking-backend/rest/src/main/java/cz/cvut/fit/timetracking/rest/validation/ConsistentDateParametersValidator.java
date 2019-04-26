package cz.cvut.fit.timetracking.rest.validation;

import cz.cvut.fit.timetracking.rest.validation.constraints.ConsistentDateParameters;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.Assert;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ConsistentDateParametersValidator implements ConstraintValidator<ConsistentDateParameters, Object> {

    private String field;
    private String isAfter;

    @Override
    public void initialize(ConsistentDateParameters constraintAnnotation) {
        field = constraintAnnotation.field();
        isAfter = constraintAnnotation.isAfter();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Object fieldValue = new BeanWrapperImpl(value).getPropertyValue(field);
        Object isAfterValue = new BeanWrapperImpl(value).getPropertyValue(isAfter);
        makeAssert(fieldValue);
        makeAssert(isAfterValue);
        LocalDateTime localDateTimeFieldValue = getLocalDateTime(fieldValue);
        LocalDateTime localDateTimeIsAfterValue = getLocalDateTime(isAfterValue);
        return localDateTimeFieldValue.isAfter(localDateTimeIsAfterValue);
    }

    private LocalDateTime getLocalDateTime(Object fieldValue) {
        LocalDateTime result;
        if (fieldValue instanceof LocalDate) {
            result = LocalDateTime.of((LocalDate) fieldValue, LocalTime.MIN);
        } else {
            result = (LocalDateTime) fieldValue;
        }
        return result;
    }

    private void makeAssert(Object fieldValue) {
        Assert.isTrue(fieldValue instanceof LocalDateTime || fieldValue instanceof LocalDate, "ConsistentDateParametersValidator supports LocalDate and LocalDateTime only.");
    }
}
