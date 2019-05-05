package cz.cvut.fit.timetracking.rest.dto.search;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class WorkRecordSearchDTO {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("idProject")
    private Integer idProject;

    @JsonProperty("idUser")
    private Integer idUser;

    @JsonProperty("dateFrom")
    private LocalDateTime dateFrom;

    @JsonProperty("dateTo")
    private LocalDateTime dateTo;

    @JsonProperty("description")
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdProject() {
        return idProject;
    }

    public void setIdProject(Integer idProject) {
        this.idProject = idProject;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
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
