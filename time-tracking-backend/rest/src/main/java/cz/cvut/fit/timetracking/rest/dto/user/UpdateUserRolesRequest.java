package cz.cvut.fit.timetracking.rest.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

public class UpdateUserRolesRequest {

    @JsonProperty("userRoles")
    private List<UserRoleName> userRoles = new ArrayList<>();

    public List<UserRoleName> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserRoleName> userRoles) {
        this.userRoles = userRoles;
    }
}
