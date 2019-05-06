package cz.cvut.fit.timetracking.rest.dto.workrecord.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.cvut.fit.timetracking.rest.dto.project.ProjectDTO;
import cz.cvut.fit.timetracking.workrecord.dto.WorkRecordConflictInfo;

import java.util.ArrayList;
import java.util.List;

public class WorkRecordsJiraImportResponse {

    @JsonProperty("workRecordImports")
    private List<WorkRecordConflictInfo> workRecordConflictInfos = new ArrayList<>();

    @JsonProperty("availableProjects")
    private List<ProjectDTO> projects = new ArrayList<>();

    public List<WorkRecordConflictInfo> getWorkRecordConflictInfos() {
        return workRecordConflictInfos;
    }

    public void setWorkRecordConflictInfos(List<WorkRecordConflictInfo> workRecordConflictInfos) {
        this.workRecordConflictInfos = workRecordConflictInfos;
    }

    public List<ProjectDTO> getProjects() {
        return projects;
    }

    public void setProjects(List<ProjectDTO> projects) {
        this.projects = projects;
    }
}
