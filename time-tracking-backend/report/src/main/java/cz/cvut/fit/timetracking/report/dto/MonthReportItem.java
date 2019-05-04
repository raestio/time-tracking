package cz.cvut.fit.timetracking.report.dto;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class MonthReportItem {

    private Month month;
    private List<ProjectReportItem> projectReportItems = new ArrayList<>();

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public List<ProjectReportItem> getProjectReportItems() {
        return projectReportItems;
    }

    public void setProjectReportItems(List<ProjectReportItem> projectReportItems) {
        this.projectReportItems = projectReportItems;
    }
}
