package cz.cvut.fit.timetracking.rest.dto.user.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.cvut.fit.timetracking.rest.dto.user.UserRoleDTO;

import java.util.ArrayList;
import java.util.List;

public class UserRolesResponse {

    @JsonProperty("userRoles")
    private List<UserRoleDTO> userRoles = new ArrayList<>();

    public List<UserRoleDTO> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserRoleDTO> userRoles) {
        this.userRoles = userRoles;
    }
}
