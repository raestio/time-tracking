package cz.cvut.fit.timetracking.report.dto;

import java.util.ArrayList;
import java.util.List;

public class UserReportItem {

    private User user;
    private List<ProjectReportItem> projectReportItems = new ArrayList<>();

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<ProjectReportItem> getProjectReportItems() {
        return projectReportItems;
    }

    public void setProjectReportItems(List<ProjectReportItem> projectReportItems) {
        this.projectReportItems = projectReportItems;
    }
}
