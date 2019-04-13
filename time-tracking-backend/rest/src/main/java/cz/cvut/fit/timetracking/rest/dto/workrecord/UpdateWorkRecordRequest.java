package cz.cvut.fit.timetracking.rest.dto.workrecord;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;

public class UpdateWorkRecordRequest {

    @JsonProperty("dateFrom")
    private ZonedDateTime dateFrom;

    @JsonProperty("dateTo")
    private ZonedDateTime dateTo;

    @JsonProperty("description")
    private String description;

    public ZonedDateTime getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(ZonedDateTime dateFrom) {
        this.dateFrom = dateFrom;
    }

    public ZonedDateTime getDateTo() {
        return dateTo;
    }

    public void setDateTo(ZonedDateTime dateTo) {
        this.dateTo = dateTo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
