package cz.cvut.fit.timetracking.rest.dto.search.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.cvut.fit.timetracking.rest.dto.search.ProjectSearchDTO;

import java.util.ArrayList;
import java.util.List;

public class SearchProjectsResponse {

    @JsonProperty("projects")
    private List<ProjectSearchDTO> projects = new ArrayList<>();

    public SearchProjectsResponse(List<ProjectSearchDTO> projects) {
        this.projects = projects;
    }

    public SearchProjectsResponse() {

    }

    public List<ProjectSearchDTO> getProjects() {
        return projects;
    }

    public void setProjects(List<ProjectSearchDTO> projects) {
        this.projects = projects;
    }
}
