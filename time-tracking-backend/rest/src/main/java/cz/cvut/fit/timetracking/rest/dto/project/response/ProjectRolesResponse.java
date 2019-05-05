package cz.cvut.fit.timetracking.rest.dto.project.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.cvut.fit.timetracking.rest.dto.project.ProjectRoleDTO;

import java.util.ArrayList;
import java.util.List;

public class ProjectRolesResponse {

    @JsonProperty("projectRoles")
    private List<ProjectRoleDTO> projectRoles = new ArrayList<>();

    public List<ProjectRoleDTO> getProjectRoles() {
        return projectRoles;
    }

    public void setProjectRoles(List<ProjectRoleDTO> projectRoles) {
        this.projectRoles = projectRoles;
    }
}
