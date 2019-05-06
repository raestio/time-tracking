package cz.cvut.fit.timetracking.rest.dto.workrecord.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JiraAvailabilityResponse {

    @JsonProperty("isUserAvailable")
    private Boolean isUserAvailable;

    public Boolean getUserAvailable() {
        return isUserAvailable;
    }

    public void setUserAvailable(Boolean userAvailable) {
        isUserAvailable = userAvailable;
    }
}
