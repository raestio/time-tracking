package cz.cvut.fit.timetracking.rest.dto.project.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.cvut.fit.timetracking.project.dto.ProjectRoleName;
import cz.cvut.fit.timetracking.rest.validation.constraints.ConsistentDateParameters;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ConsistentDateParameters(field = "validTo", isAfter = "validFrom", message = "validTo must be after validFrom")
public class CreateOrUpdateProjectAssignmentRequest {

    @JsonProperty("projectId")
    private Integer projectId;

    @JsonProperty("userId")
    private Integer userId;

    @JsonProperty("validFrom")
    private LocalDate validFrom;

    @JsonProperty("validTo")
    private LocalDate validTo;

    @JsonProperty("projectRoleNames")
    private List<ProjectRoleName> projectRoleNames = new ArrayList<>();

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public List<ProjectRoleName> getProjectRoleNames() {
        return projectRoleNames;
    }

    public void setProjectRoleNames(List<ProjectRoleName> projectRoleNames) {
        this.projectRoleNames = projectRoleNames;
    }
}
