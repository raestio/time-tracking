package cz.cvut.fit.timetracking.data.api.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class WorkRecordDTO {

    private Integer id;
    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;
    private String description;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;
    private ProjectDTO project;
    private WorkTypeDTO workType;
    private UserDTOLight user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserDTOLight getUser() {
        return user;
    }

    public void setUser(UserDTOLight user) {
        this.user = user;
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

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDateTime getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(LocalDateTime dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkRecordDTO that = (WorkRecordDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
