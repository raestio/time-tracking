package cz.cvut.fit.timetracking.report.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DayReportItem {

    private LocalDate day;
    private List<ProjectReportItem> projectReportItems = new ArrayList<>();

    public LocalDate getDay() {
        return day;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }

    public List<ProjectReportItem> getProjectReportItems() {
        return projectReportItems;
    }

    public void setProjectReportItems(List<ProjectReportItem> projectReportItems) {
        this.projectReportItems = projectReportItems;
    }
}
