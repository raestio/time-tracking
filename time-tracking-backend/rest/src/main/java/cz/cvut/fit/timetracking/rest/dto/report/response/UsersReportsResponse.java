package cz.cvut.fit.timetracking.rest.dto.report.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.cvut.fit.timetracking.rest.dto.report.UserReportItemDTO;

import java.util.ArrayList;
import java.util.List;

public class UsersReportsResponse {

    @JsonProperty("userReportItems")
    private List<UserReportItemDTO> userReportItems = new ArrayList<>();

    public List<UserReportItemDTO> getUserReportItems() {
        return userReportItems;
    }

    public void setUserReportItems(List<UserReportItemDTO> userReportItems) {
        this.userReportItems = userReportItems;
    }
}
