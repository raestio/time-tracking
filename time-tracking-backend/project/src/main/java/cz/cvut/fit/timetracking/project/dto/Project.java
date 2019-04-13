package cz.cvut.fit.timetracking.project.dto;

import java.time.LocalDate;

/**
 * @author Rastislav Zlacky (rastislav.zlacky@inventi.cz) on 13.04.2019.
 */
public class Project {

    private Integer id;
    private String name;
    private String description;
    private LocalDate start;
    private LocalDate end;

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

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }
}
