package cz.cvut.fit.timetracking.rest.dto.workrecord;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;

public class WorkRecordDTO {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("dateFrom")
    private ZonedDateTime dateFrom;

    @JsonProperty("dateTo")
    private ZonedDateTime dateTo;

    @JsonProperty("description")
    private String description;

    @JsonProperty("dateCreated")
    private ZonedDateTime dateCreated;

    @JsonProperty("dateUpdated")
    private ZonedDateTime dateUpdated;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public ZonedDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(ZonedDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public ZonedDateTime getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(ZonedDateTime dateUpdated) {
        this.dateUpdated = dateUpdated;
    }
}
