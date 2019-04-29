package cz.cvut.fit.timetracking.search.service;

import cz.cvut.fit.timetracking.search.dto.ProjectDocument;
import cz.cvut.fit.timetracking.search.dto.SearchAllResult;
import cz.cvut.fit.timetracking.search.dto.UserDocument;
import cz.cvut.fit.timetracking.search.dto.WorkRecordDocument;

import java.util.List;

public interface SearchService {
    SearchAllResult searchAll(String keyword);
    List<ProjectDocument> searchProjects(String keyword);
    List<UserDocument> searchUsers(String keyword);
    List<WorkRecordDocument> searchWorkRecords(String keyword);
}
