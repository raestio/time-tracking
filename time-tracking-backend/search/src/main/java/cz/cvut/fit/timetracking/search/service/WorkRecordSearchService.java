package cz.cvut.fit.timetracking.search.service;

import cz.cvut.fit.timetracking.search.dto.WorkRecordDocument;

import java.util.List;

public interface WorkRecordSearchService {
    List<WorkRecordDocument> searchWorkRecords(String keyword);
    List<WorkRecordDocument> searchWorkRecords(String keyword, Integer userId);
}
