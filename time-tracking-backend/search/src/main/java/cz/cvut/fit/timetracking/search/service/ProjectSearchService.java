package cz.cvut.fit.timetracking.search.service;

import cz.cvut.fit.timetracking.search.dto.ProjectDocument;

import java.util.List;

public interface ProjectSearchService {
    List<ProjectDocument> searchProjects(String keyword);
}
