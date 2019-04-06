package cz.cvut.fit.timetracking.data.api.dto;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class ProjectDTO {

    private Integer id;
    private String name;
    private String description;
    private LocalDate start;
    private LocalDate end;
    private Set<ProjectAssignmentDTO> projectAssignments = new HashSet<>();
    private Set<WorkRecordDTO> workRecords = new HashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<WorkRecordDTO> getWorkRecords() {
        return workRecords;
    }

    public void setWorkRecords(Set<WorkRecordDTO> workRecords) {
        this.workRecords = workRecords;
    }

    public Set<ProjectAssignmentDTO> getProjectAssignments() {
        return projectAssignments;
    }

    public void setProjectAssignments(Set<ProjectAssignmentDTO> projectAssignments) {
        this.projectAssignments = projectAssignments;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }
}
