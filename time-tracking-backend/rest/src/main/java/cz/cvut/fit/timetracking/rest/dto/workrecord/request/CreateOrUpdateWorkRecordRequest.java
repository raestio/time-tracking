package cz.cvut.fit.timetracking.rest.dto.workrecord.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.cvut.fit.timetracking.rest.validation.constraints.ConsistentDateParameters;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@ConsistentDateParameters(field = "dateTo", isAfter = "dateFrom", message = "dateTo must be after dateFrom")
public class CreateOrUpdateWorkRecordRequest {

    @NotNull
    @JsonProperty("dateFrom")
    private LocalDateTime dateFrom;

    @NotNull
    @JsonProperty("dateTo")
    private LocalDateTime dateTo;

    @NotEmpty
    @JsonProperty("description")
    private String description;

    @NotNull
    @JsonProperty("projectId")
    private Integer projectId;

    @NotNull
    @JsonProperty("workTypeId")
    private Integer workTypeId;

    @JsonProperty("userId")
    private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getWorkTypeId() {
        return workTypeId;
    }

    public void setWorkTypeId(Integer workTypeId) {
        this.workTypeId = workTypeId;
    }
}
