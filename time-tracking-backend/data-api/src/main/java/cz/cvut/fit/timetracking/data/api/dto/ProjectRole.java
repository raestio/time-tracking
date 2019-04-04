package cz.cvut.fit.timetracking.data.api.dto;

import cz.cvut.fit.timetracking.data.api.enums.ProjectRoleName;

import java.util.HashSet;
import java.util.Set;

public class ProjectRole {

    private Integer id;
    private ProjectRoleName name;
    private String description;
    private Set<ProjectAssignment> projectAssignments = new HashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<ProjectAssignment> getProjectAssignments() {
        return projectAssignments;
    }

    public void setProjectAssignments(Set<ProjectAssignment> projectAssignments) {
        this.projectAssignments = projectAssignments;
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
