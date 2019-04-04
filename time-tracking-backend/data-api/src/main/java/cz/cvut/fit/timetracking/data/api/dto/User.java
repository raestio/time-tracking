package cz.cvut.fit.timetracking.data.api.dto;

import java.util.HashSet;
import java.util.Set;

public class User {

    private Integer id;
    private String name;
    private String surname;
    private String email;
    private Set<ProjectAssignment> projectAssignments = new HashSet<>();
    private Set<WorkRecord> workRecords = new HashSet<>();
    private Set<UserRole> userRoles = new HashSet<>();

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
}
