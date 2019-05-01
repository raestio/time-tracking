package cz.cvut.fit.timetracking.data.entity;

import cz.cvut.fit.timetracking.data.entity.enums.AuthProviderEnum;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@NamedEntityGraph(name = "User.userRoles", attributeNodes = @NamedAttributeNode("userRoles"))
@Entity
@Table(name = "user", schema = "time_tracking_schema")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotEmpty
    @Column(name = "name")
    private String name;

    @NotEmpty
    @Column(name = "surname")
    private String surname;

    @NotEmpty
    @Email
    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "picture_url")
    private String pictureUrl;

    @NotNull
    @Column(name = "auth_provider")
    @Enumerated(EnumType.STRING)
    private AuthProviderEnum authProvider;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<ProjectAssignment> projectAssignments = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<WorkRecord> workRecords = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "user_roles_assignment", schema = "time_tracking_schema",
               joinColumns = @JoinColumn(name = "id_user"), inverseJoinColumns = @JoinColumn(name = "id_user_role"))
    private Set<UserRole> userRoles = new HashSet<>();

    public User(Integer id) {
        this.id = id;
    }

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<ProjectAssignment> getProjectAssignments() {
        return projectAssignments;
    }

    public void setProjectAssignments(Set<ProjectAssignment> projectAssignments) {
        this.projectAssignments = projectAssignments;
    }

    public Set<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public Set<WorkRecord> getWorkRecords() {
        return workRecords;
    }

    public void setWorkRecords(Set<WorkRecord> workRecords) {
        this.workRecords = workRecords;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public AuthProviderEnum getAuthProvider() {
        return authProvider;
    }

    public void setAuthProvider(AuthProviderEnum authProvider) {
        this.authProvider = authProvider;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
