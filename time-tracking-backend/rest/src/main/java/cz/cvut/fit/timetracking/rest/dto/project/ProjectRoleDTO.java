package cz.cvut.fit.timetracking.rest.dto.project;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class ProjectRoleDTO {

    @NotNull
    @JsonProperty("id")
    private Integer id;

    @NotNull
    @JsonProperty("name")
    private ProjectRoleName name;

    @JsonProperty("description")
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ProjectRoleName getName() {
        return name;
    }

    public void setName(ProjectRoleName name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
