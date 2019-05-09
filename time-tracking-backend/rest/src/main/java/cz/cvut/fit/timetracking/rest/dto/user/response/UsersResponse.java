package cz.cvut.fit.timetracking.rest.dto.user.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.cvut.fit.timetracking.rest.dto.user.UserDTO;

import java.util.ArrayList;
import java.util.List;

public class UsersResponse {

    @JsonProperty("users")
    private List<UserDTO> users = new ArrayList<>();

    public List<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserDTO> users) {
        this.users = users;
    }
}
