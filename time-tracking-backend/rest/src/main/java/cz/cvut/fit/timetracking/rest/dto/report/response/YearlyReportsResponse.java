package cz.cvut.fit.timetracking.rest.dto.report.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.cvut.fit.timetracking.rest.dto.report.YearReportItemDTO;

import java.util.ArrayList;
import java.util.List;

public class YearlyReportsResponse {

    @JsonProperty("yearlyReportItems")
    private List<YearReportItemDTO> yearlyReportItems = new ArrayList<>();

    public List<YearReportItemDTO> getYearlyReportItems() {
        return yearlyReportItems;
    }

    public void setYearlyReportItems(List<YearReportItemDTO> yearlyReportItems) {
        this.yearlyReportItems = yearlyReportItems;
    }
}
