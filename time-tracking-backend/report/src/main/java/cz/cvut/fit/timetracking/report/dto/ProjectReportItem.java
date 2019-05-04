package cz.cvut.fit.timetracking.report.dto;

import java.util.ArrayList;
import java.util.List;

public class ProjectReportItem {

    private Project project;
    private List<WorkReportItem> workReportItems = new ArrayList<>();

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<WorkReportItem> getWorkReportItems() {
        return workReportItems;
    }

    public void setWorkReportItems(List<WorkReportItem> workReportItems) {
        this.workReportItems = workReportItems;
    }
}
