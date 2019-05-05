package cz.cvut.fit.timetracking.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@NamedEntityGraph(
        name = "ProjectAssignment.projectRoles",
        attributeNodes = @NamedAttributeNode(value = "projectRoles")
)
@Entity
@Table(name = "project_assignment", schema = "time_tracking_schema")
public class ProjectAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotNull
    @Column(name = "valid_from")
    private LocalDate validFrom;

    @Column(name = "valid_to")
    private LocalDate validTo;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_project")
    private Project project;

    @ManyToMany
    @JoinTable(name = "project_roles_assignment", schema = "time_tracking_schema",
            joinColumns = @JoinColumn(name = "id_project_assignment"), inverseJoinColumns = @JoinColumn(name = "id_project_role"))
    private Set<ProjectRole> projectRoles = new HashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Set<ProjectRole> getProjectRoles() {
        return projectRoles;
    }

    public void setProjectRoles(Set<ProjectRole> projectRoles) {
        this.projectRoles = projectRoles;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectAssignment that = (ProjectAssignment) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
