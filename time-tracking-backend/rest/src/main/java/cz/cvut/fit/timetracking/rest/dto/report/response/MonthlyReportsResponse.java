package cz.cvut.fit.timetracking.rest.dto.report.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.cvut.fit.timetracking.rest.dto.report.MonthReportItemDTO;

import java.util.ArrayList;
import java.util.List;

public class MonthlyReportsResponse {

    @JsonProperty("monthlyReportItems")
    private List<MonthReportItemDTO> monthlyReportItems = new ArrayList<>();

    public List<MonthReportItemDTO> getMonthlyReportItems() {
        return monthlyReportItems;
    }

    public void setMonthlyReportItems(List<MonthReportItemDTO> monthlyReportItems) {
        this.monthlyReportItems = monthlyReportItems;
    }
}
