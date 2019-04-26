package cz.cvut.fit.timetracking.data.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "project", schema = "time_tracking_schema")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotEmpty
    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "start")
    private LocalDate start;

    @Column(name = "\"end\"")
    private LocalDate end;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private Set<ProjectAssignment> projectAssignments = new HashSet<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private Set<WorkRecord> workRecords = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "project_work_type", schema = "time_tracking_schema",
            joinColumns = @JoinColumn(name = "id_project"), inverseJoinColumns = @JoinColumn(name = "id_work_type"))
    private Set<WorkType> workTypes = new HashSet<>();

    public Project() {

    }

    public Set<WorkType> getWorkTypes() {
        return workTypes;
    }

    public void setWorkTypes(Set<WorkType> workTypes) {
        this.workTypes = workTypes;
    }

    public Project(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<WorkRecord> getWorkRecords() {
        return workRecords;
    }

    public void setWorkRecords(Set<WorkRecord> workRecords) {
        this.workRecords = workRecords;
    }

    public Set<ProjectAssignment> getProjectAssignments() {
        return projectAssignments;
    }

    public void setProjectAssignments(Set<ProjectAssignment> projectAssignments) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return Objects.equals(id, project.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
