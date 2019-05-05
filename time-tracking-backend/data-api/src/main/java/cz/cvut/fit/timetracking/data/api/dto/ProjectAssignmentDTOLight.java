package cz.cvut.fit.timetracking.data.api.dto;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class ProjectAssignmentDTOLight {

    private Integer id;
    private Integer projectId;
    private LocalDate validFrom;
    private LocalDate validTo;
    private Integer userId;
    private Set<ProjectRoleDTO> projectRoles = new HashSet<>();

    public Integer getId() {
        return id;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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
