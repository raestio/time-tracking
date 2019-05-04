package cz.cvut.fit.timetracking.workrecord.dto;

import java.util.Objects;

public class WorkType {

    private Integer id;
    private String name;
    private String description;

    public WorkType(Integer id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public WorkType(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public WorkType() {

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
        WorkType workType = (WorkType) o;
        return Objects.equals(id, workType.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
