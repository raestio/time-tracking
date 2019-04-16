package cz.cvut.fit.timetracking.user.dto;

public class UserRole {
    private UserRoleName name;
    private String description;

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
