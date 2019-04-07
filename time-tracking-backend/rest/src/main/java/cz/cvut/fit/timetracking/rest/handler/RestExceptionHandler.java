package cz.cvut.fit.timetracking.rest.handler;

import cz.cvut.fit.timetracking.user.exception.UserNotFoundException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST.value(), "Validation failed");
        List<ApiSubError> subErrors = ex.getBindingResult().getFieldErrors().stream().map(this::mapValidationError).collect(Collectors.toList());
        apiError.setSubErrors(subErrors);
        return buildResponseEntity(apiError, HttpStatus.BAD_REQUEST);
    }

    private ApiSubError mapValidationError(FieldError fieldError) {
        ApiValidationError apiValidationError = new ApiValidationError();
        apiValidationError.setMessage(fieldError.getDefaultMessage());
        apiValidationError.setObject(fieldError.getObjectName());
        apiValidationError.setRejectedValue(fieldError.getRejectedValue());
        apiValidationError.setField(fieldError.getField());
        return apiValidationError;
    }

    @ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<Object> handleUserNotFound(UserNotFoundException ex) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return buildResponseEntity(apiError, HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError, HttpStatus httpStatus) {
        return new ResponseEntity<>(apiError, httpStatus);
    }
}
