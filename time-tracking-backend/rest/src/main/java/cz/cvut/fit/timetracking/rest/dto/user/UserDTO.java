package cz.cvut.fit.timetracking.rest.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.cvut.fit.timetracking.user.dto.UserRole;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserDTO implements Serializable {

    @JsonProperty("id")
    private Integer id;

    @NotEmpty
    @JsonProperty("name")
    private String name;

    @NotEmpty
    @JsonProperty("surname")
    private String surname;

    @Email
    @JsonProperty("email")
    private String email;

    @JsonProperty("pictureUrl")
    private String pictureUrl;

    @JsonProperty("userRoles")
    private List<UserRoleDTO> userRoles = new ArrayList<>();

    public List<UserRoleDTO> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserRoleDTO> userRoles) {
        this.userRoles = userRoles;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
}
