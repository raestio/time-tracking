package cz.cvut.fit.timetracking.data.api.dto;

import cz.cvut.fit.timetracking.data.api.enums.UserRoleName;

import java.util.HashSet;
import java.util.Set;

public class UserRole {

    private Integer id;
    private UserRoleName name;
    private String description;
    private Set<User> users = new HashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public UserRoleName getName() {
        return name;
    }

    public void setName(UserRoleName name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
