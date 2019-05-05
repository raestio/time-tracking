package cz.cvut.fit.timetracking.rest.dto.project;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.cvut.fit.timetracking.rest.dto.user.UserDTOLight;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProjectAssignmentDTO {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("validFrom")
    private LocalDate validFrom;

    @JsonProperty("validTo")
    private LocalDate validTo;

    @JsonProperty("user")
    private UserDTOLight user;

    @JsonProperty("projectRoles")
    private List<ProjectRoleDTO> projectRoles = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public UserDTOLight getUser() {
        return user;
    }

    public void setUser(UserDTOLight user) {
        this.user = user;
    }

    public List<ProjectRoleDTO> getProjectRoles() {
        return projectRoles;
    }

    public void setProjectRoles(List<ProjectRoleDTO> projectRoles) {
        this.projectRoles = projectRoles;
    }
}
