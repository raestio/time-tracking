package cz.cvut.fit.timetracking.rest.dto.search.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.cvut.fit.timetracking.rest.dto.search.UserSearchDTO;

import java.util.ArrayList;
import java.util.List;

public class SearchUsersResponse {

    @JsonProperty("users")
    private List<UserSearchDTO> users = new ArrayList<>();

    public SearchUsersResponse(List<UserSearchDTO> users) {
        this.users = users;
    }

    public SearchUsersResponse() {

    }

    public List<UserSearchDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserSearchDTO> users) {
        this.users = users;
    }
}
