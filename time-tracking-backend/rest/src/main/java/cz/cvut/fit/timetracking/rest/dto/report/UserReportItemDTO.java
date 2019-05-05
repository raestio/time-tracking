package cz.cvut.fit.timetracking.rest.dto.report;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.cvut.fit.timetracking.rest.dto.user.UserDTOLight;

import java.util.ArrayList;
import java.util.List;

public class UserReportItemDTO {

    @JsonProperty("user")
    private UserDTOLight user;

    @JsonProperty("projectReportItems")
    private List<ProjectReportItemDTO> projectReportItems = new ArrayList<>();

    public UserDTOLight getUser() {
        return user;
    }

    public void setUser(UserDTOLight user) {
        this.user = user;
    }

    public List<ProjectReportItemDTO> getProjectReportItems() {
        return projectReportItems;
    }

    public void setProjectReportItems(List<ProjectReportItemDTO> projectReportItems) {
        this.projectReportItems = projectReportItems;
    }
}
