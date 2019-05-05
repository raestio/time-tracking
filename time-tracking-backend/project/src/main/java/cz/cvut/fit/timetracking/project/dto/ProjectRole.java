package cz.cvut.fit.timetracking.project.dto;

import cz.cvut.fit.timetracking.data.api.dto.ProjectRoleName;

public class ProjectRole {

    private Integer id;
    private ProjectRoleName name;
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
