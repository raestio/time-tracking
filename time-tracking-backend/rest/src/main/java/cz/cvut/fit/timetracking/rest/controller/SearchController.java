package cz.cvut.fit.timetracking.rest.controller;

import cz.cvut.fit.timetracking.search.service.ProjectSearchService;
import cz.cvut.fit.timetracking.search.service.UserSearchService;
import cz.cvut.fit.timetracking.search.service.WorkRecordSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private WorkRecordSearchService workRecordSearchService;

    @Autowired
    private ProjectSearchService projectSearchService;

    @Autowired
    private UserSearchService userSearchService;

    @GetMapping("/users")
    public ResponseEntity<Object> searchUsers() {

    }

    @GetMapping("/projects")
    public ResponseEntity<Object> searchUsers() {

    }

    @GetMapping("/work-records")
    public ResponseEntity<Object> searchUsers() {

    }
}
