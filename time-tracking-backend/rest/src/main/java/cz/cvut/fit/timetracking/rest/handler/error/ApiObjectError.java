package cz.cvut.fit.timetracking.rest.handler.error;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ApiObjectError extends ApiSubError {

    @JsonProperty("object")
    private String object;

    @JsonProperty("message")
    private String message;

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
