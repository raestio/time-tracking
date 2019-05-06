package cz.cvut.fit.timetracking.workrecord.dto.input;

import java.time.LocalDateTime;

public class WorkRecordInput {

    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;
    private String description;
    private Integer projectId;
    private Integer workTypeId;
    private Integer userId;

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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
