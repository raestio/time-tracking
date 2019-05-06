package cz.cvut.fit.timetracking.rest.dto.workrecord;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.cvut.fit.timetracking.rest.dto.project.ProjectDTO;
import cz.cvut.fit.timetracking.rest.dto.project.WorkTypeDTO;

import java.time.LocalDateTime;

public class WorkRecordDTO {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("dateFrom")
    private LocalDateTime dateFrom;

    @JsonProperty("dateTo")
    private LocalDateTime dateTo;

    @JsonProperty("description")
    private String description;

    @JsonProperty("project")
    private ProjectDTO project;

    @JsonProperty("workType")
    private WorkTypeDTO workType;

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

    public ProjectDTO getProject() {
        return project;
    }

    public void setProject(ProjectDTO project) {
        this.project = project;
    }

    public WorkTypeDTO getWorkType() {
        return workType;
    }

    public void setWorkType(WorkTypeDTO workType) {
        this.workType = workType;
    }
}
