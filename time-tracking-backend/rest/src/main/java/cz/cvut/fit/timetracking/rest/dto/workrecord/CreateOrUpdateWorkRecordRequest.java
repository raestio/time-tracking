package cz.cvut.fit.timetracking.rest.dto.workrecord;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class CreateOrUpdateWorkRecordRequest {

    @JsonProperty("id")
    private Integer id;

    @NotNull
    @JsonProperty("dateFrom")
    private LocalDateTime dateFrom;

    @NotNull
    @JsonProperty("dateTo")
    private LocalDateTime dateTo;

    @NotEmpty
    @JsonProperty("description")
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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
