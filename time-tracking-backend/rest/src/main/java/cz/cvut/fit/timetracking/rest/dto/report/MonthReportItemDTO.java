package cz.cvut.fit.timetracking.rest.dto.report;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class MonthReportItemDTO {

    @JsonProperty("month")
    private Month month;

    @JsonProperty("projectReportItems")
    private List<ProjectReportItemDTO> projectReportItems = new ArrayList<>();

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public List<ProjectReportItemDTO> getProjectReportItems() {
        return projectReportItems;
    }

    public void setProjectReportItems(List<ProjectReportItemDTO> projectReportItems) {
        this.projectReportItems = projectReportItems;
    }
}
