package cz.cvut.fit.timetracking.rest.dto.report;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class ProjectReportItemDTO {

    @JsonProperty("project")
    private ProjectReportDTO project;

    @JsonProperty("workReportItems")
    private List<WorkReportItemDTO> workReportItems = new ArrayList<>();

    public ProjectReportDTO getProject() {
        return project;
    }

    public void setProject(ProjectReportDTO project) {
        this.project = project;
    }

    public List<WorkReportItemDTO> getWorkReportItems() {
        return workReportItems;
    }

    public void setWorkReportItems(List<WorkReportItemDTO> workReportItems) {
        this.workReportItems = workReportItems;
    }
}
