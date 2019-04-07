package cz.cvut.fit.timetracking.rest.handler;

import com.fasterxml.jackson.annotation.JsonProperty;

class ApiValidationError extends ApiSubError {

    @JsonProperty("object")
    private String object;

    @JsonProperty("field")
    private String field;

    @JsonProperty("rejectedValue")
    private Object rejectedValue;

    @JsonProperty("message")
    private String message;

    ApiValidationError() {
    }

    ApiValidationError(String object, String message) {
        this.object = object;
        this.message = message;
    }

    String getObject() {
        return object;
    }

    void setObject(String object) {
        this.object = object;
    }

    String getField() {
        return field;
    }

    void setField(String field) {
        this.field = field;
    }

    Object getRejectedValue() {
        return rejectedValue;
    }

    void setRejectedValue(Object rejectedValue) {
        this.rejectedValue = rejectedValue;
    }

    String getMessage() {
        return message;
    }

    void setMessage(String message) {
        this.message = message;
    }
}
