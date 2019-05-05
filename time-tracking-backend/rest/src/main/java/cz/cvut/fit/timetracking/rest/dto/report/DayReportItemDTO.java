package cz.cvut.fit.timetracking.rest.dto.report;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DayReportItemDTO {

    @JsonProperty("day")
    private LocalDate day;

    @JsonProperty("projectReportItems")
    private List<ProjectReportItemDTO> projectReportItems = new ArrayList<>();

    public LocalDate getDay() {
        return day;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }

    public List<ProjectReportItemDTO> getProjectReportItems() {
        return projectReportItems;
    }

    public void setProjectReportItems(List<ProjectReportItemDTO> projectReportItems) {
        this.projectReportItems = projectReportItems;
    }
}
