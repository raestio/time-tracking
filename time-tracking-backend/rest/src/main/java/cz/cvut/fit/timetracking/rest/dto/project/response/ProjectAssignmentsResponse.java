package cz.cvut.fit.timetracking.rest.dto.project.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.cvut.fit.timetracking.rest.dto.project.ProjectAssignmentDTO;

import java.util.ArrayList;
import java.util.List;

public class ProjectAssignmentsResponse {

    @JsonProperty("projectAssignments")
    List<ProjectAssignmentDTO> projectAssignments = new ArrayList<>();

    public List<ProjectAssignmentDTO> getProjectAssignments() {
        return projectAssignments;
    }

    public void setProjectAssignments(List<ProjectAssignmentDTO> projectAssignments) {
        this.projectAssignments = projectAssignments;
    }
}
