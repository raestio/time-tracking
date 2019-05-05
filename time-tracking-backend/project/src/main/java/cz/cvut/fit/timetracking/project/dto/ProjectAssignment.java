package cz.cvut.fit.timetracking.project.dto;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProjectAssignment {

    private Integer id;
    private Integer projectId;
    private LocalDate validFrom;
    private LocalDate validTo;
    private User user;
    private List<ProjectRole> projectRoles = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDate getValidTo() {
        return validTo;
    }

    public void setValidTo(LocalDate validTo) {
        this.validTo = validTo;
    }

    public List<ProjectRole> getProjectRoles() {
        return projectRoles;
    }

    public void setProjectRoles(List<ProjectRole> projectRoles) {
        this.projectRoles = projectRoles;
    }
}
