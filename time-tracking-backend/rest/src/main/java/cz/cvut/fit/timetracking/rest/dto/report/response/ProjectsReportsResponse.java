package cz.cvut.fit.timetracking.rest.dto.report.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import cz.cvut.fit.timetracking.rest.dto.report.ProjectReportItemDTO;

import java.util.ArrayList;
import java.util.List;

public class ProjectsReportsResponse {

    @JsonProperty("projectReportItems")
    private List<ProjectReportItemDTO> projectReportItems = new ArrayList<>();

    public List<ProjectReportItemDTO> getProjectReportItems() {
        return projectReportItems;
    }

    public void setProjectReportItems(List<ProjectReportItemDTO> projectReportItems) {
        this.projectReportItems = projectReportItems;
    }
}
