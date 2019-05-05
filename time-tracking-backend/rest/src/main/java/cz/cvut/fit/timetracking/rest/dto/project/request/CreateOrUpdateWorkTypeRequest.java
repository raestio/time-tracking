package cz.cvut.fit.timetracking.rest.dto.project.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateOrUpdateWorkTypeRequest {

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
