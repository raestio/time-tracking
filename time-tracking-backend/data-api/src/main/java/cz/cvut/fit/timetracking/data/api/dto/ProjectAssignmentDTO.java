package cz.cvut.fit.timetracking.data.api.dto;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class ProjectAssignmentDTO {

    private Integer id;
    private LocalDate validFrom;
    private LocalDate validTo;
    private UserDTO user;
    private ProjectDTO project;
    private Set<ProjectRoleDTO> projectRoles = new HashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public ProjectDTO getProject() {
        return project;
    }

    public void setProject(ProjectDTO project) {
        this.project = project;
    }

    public Set<ProjectRoleDTO> getProjectRoles() {
        return projectRoles;
    }

    public void setProjectRoles(Set<ProjectRoleDTO> projectRoles) {
        this.projectRoles = projectRoles;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDate getValidTo() {
        return validTo;
    }

    public void setValidTo(LocalDate validTo) {
        this.validTo = validTo;
    }
}
