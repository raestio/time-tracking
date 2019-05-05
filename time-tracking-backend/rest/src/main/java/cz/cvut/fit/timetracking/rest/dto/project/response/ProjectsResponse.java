package cz.cvut.fit.timetracking.rest.dto.project.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.cvut.fit.timetracking.rest.dto.project.ProjectDTO;

import java.util.ArrayList;
import java.util.List;

public class ProjectsResponse {

    @JsonProperty("projects")
    private List<ProjectDTO> projects = new ArrayList<>();

    public List<ProjectDTO> getProjects() {
        return projects;
    }

    public void setProjects(List<ProjectDTO> projects) {
        this.projects = projects;
    }
}
