package cz.cvut.fit.timetracking.rest.dto.workrecord;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class UpdateWorkRecordRequest {

    @JsonProperty("dateFrom")
    private LocalDateTime dateFrom;

    @JsonProperty("dateTo")
    private LocalDateTime dateTo;

    @JsonProperty("description")
    private String description;

    public LocalDateTime getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDateTime dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDateTime getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDateTime dateTo) {
        this.dateTo = dateTo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
