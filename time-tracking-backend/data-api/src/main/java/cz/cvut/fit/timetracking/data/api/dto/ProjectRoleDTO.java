package cz.cvut.fit.timetracking.data.api.dto;

import java.util.Objects;

public class ProjectRoleDTO {

    private Integer id;
    private ProjectRoleName name;
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ProjectRoleName getName() {
        return name;
    }

    public void setName(ProjectRoleName name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectRoleDTO that = (ProjectRoleDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
