package cz.cvut.fit.timetracking.search.service.impl;

import cz.cvut.fit.timetracking.search.dto.ProjectDocument;
import cz.cvut.fit.timetracking.search.dto.SearchAllResult;
import cz.cvut.fit.timetracking.search.dto.UserDocument;
import cz.cvut.fit.timetracking.search.dto.WorkRecordDocument;
import cz.cvut.fit.timetracking.search.service.SearchService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    @Override
    public SearchAllResult searchAll(String keyword) {
        return null;
    }

    @Override
    public List<ProjectDocument> searchProjects(String keyword) {
        return null;
    }

    @Override
    public List<UserDocument> searchUsers(String keyword) {
        return null;
    }

    @Override
    public List<WorkRecordDocument> searchWorkRecords(String keyword) {
        return null;
    }
}
