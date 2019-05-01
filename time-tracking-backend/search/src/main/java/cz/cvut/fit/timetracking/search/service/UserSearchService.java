package cz.cvut.fit.timetracking.search.service;

import cz.cvut.fit.timetracking.search.dto.UserDocument;

import java.util.List;

public interface UserSearchService {
    List<UserDocument> searchUsers(String keyword);
}
