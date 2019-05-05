package cz.cvut.fit.timetracking.rest.dto.report.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.cvut.fit.timetracking.rest.dto.report.DayReportItemDTO;

import java.util.ArrayList;
import java.util.List;

public class DailyReportsResponse {

    @JsonProperty("dailyReportItems")
    private List<DayReportItemDTO> dailyReportItems = new ArrayList<>();

    public List<DayReportItemDTO> getDailyReportItems() {
        return dailyReportItems;
    }

    public void setDailyReportItems(List<DayReportItemDTO> dailyReportItems) {
        this.dailyReportItems = dailyReportItems;
    }
}
