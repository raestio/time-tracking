package cz.cvut.fit.timetracking.data.api.dto;

import java.util.HashSet;
import java.util.Set;

public class UserDTO {

    private Integer id;
    private String name;
    private String surname;
    private String email;
    private Set<ProjectAssignmentDTO> projectAssignments = new HashSet<>();
    private Set<WorkRecordDTO> workRecords = new HashSet<>();
    private Set<UserRoleDTO> userRoles = new HashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<ProjectAssignmentDTO> getProjectAssignments() {
        return projectAssignments;
    }

    public void setProjectAssignments(Set<ProjectAssignmentDTO> projectAssignments) {
        this.projectAssignments = projectAssignments;
    }

    public Set<UserRoleDTO> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<UserRoleDTO> userRoles) {
        this.userRoles = userRoles;
    }

    public Set<WorkRecordDTO> getWorkRecords() {
        return workRecords;
    }

    public void setWorkRecords(Set<WorkRecordDTO> workRecords) {
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
