package cz.cvut.fit.timetracking.rest.handler;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

class ApiError {

    @JsonProperty("status")
    private Integer status;

    @JsonProperty("timestamp")
    private LocalDateTime timestamp;

    @JsonProperty("message")
    private String message;

    @JacksonInject("subErrors")
    private List<ApiSubError> subErrors = new ArrayList<>();

    private ApiError() {
        timestamp = LocalDateTime.now();
    }

    ApiError(int status) {
        this();
        this.status = status;
    }

    ApiError(int status, Throwable ex) {
        this();
        this.status = status;
        this.message = "Unexpected error";
    }

    ApiError(int status, String message) {
        this();
        this.status = status;
        this.message = message;
    }

    Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<ApiSubError> getSubErrors() {
        return subErrors;
    }

    public void setSubErrors(List<ApiSubError> subErrors) {
        this.subErrors = subErrors;
    }

    LocalDateTime getTimestamp() {
        return timestamp;
    }

    void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    String getMessage() {
        return message;
    }

    void setMessage(String message) {
        this.message = message;
    }
}
