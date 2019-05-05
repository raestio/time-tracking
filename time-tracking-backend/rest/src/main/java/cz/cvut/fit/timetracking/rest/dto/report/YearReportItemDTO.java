package cz.cvut.fit.timetracking.rest.dto.report;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class YearReportItemDTO {

    @JsonProperty("year")
    private Year year;

    @JsonProperty("projectReportItems")
    private List<ProjectReportItemDTO> projectReportItems = new ArrayList<>();

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    public List<ProjectReportItemDTO> getProjectReportItems() {
        return projectReportItems;
    }

    public void setProjectReportItems(List<ProjectReportItemDTO> projectReportItems) {
        this.projectReportItems = projectReportItems;
    }
}
