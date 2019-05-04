package cz.cvut.fit.timetracking.report.dto;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class YearReportItem {

    private Year year;
    private List<ProjectReportItem> projectReportItems = new ArrayList<>();

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    public List<ProjectReportItem> getProjectReportItems() {
        return projectReportItems;
    }

    public void setProjectReportItems(List<ProjectReportItem> projectReportItems) {
        this.projectReportItems = projectReportItems;
    }
}
