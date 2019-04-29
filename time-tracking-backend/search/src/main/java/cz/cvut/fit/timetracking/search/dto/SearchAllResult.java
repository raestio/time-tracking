package cz.cvut.fit.timetracking.search.dto;

import java.util.List;

public class SearchAllResult {

    private List<UserDocument> users;
    private List<ProjectDocument> projects;
    private List<WorkRecordDocument> workRecords;

    public List<UserDocument> getUsers() {
        return users;
    }

    public void setUsers(List<UserDocument> users) {
        this.users = users;
    }

    public List<ProjectDocument> getProjects() {
        return projects;
    }

    public void setProjects(List<ProjectDocument> projects) {
        this.projects = projects;
    }

    public List<WorkRecordDocument> getWorkRecords() {
        return workRecords;
    }

    public void setWorkRecords(List<WorkRecordDocument> workRecords) {
        this.workRecords = workRecords;
    }
}
