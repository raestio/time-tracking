package cz.cvut.fit.timetracking.rest.dto.user;


import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class UserRoleDTO {

    @NotNull
    @JsonProperty("name")
    private UserRoleName name;

    @JsonProperty("description")
    private String description;

    public UserRoleName getName() {
        return name;
    }

    public void setName(UserRoleName name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
