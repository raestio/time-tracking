package cz.cvut.fit.timetracking.rest.controller;

import cz.cvut.fit.timetracking.rest.dto.search.ProjectSearchDTO;
import cz.cvut.fit.timetracking.rest.dto.search.UserSearchDTO;
import cz.cvut.fit.timetracking.rest.dto.search.WorkRecordSearchDTO;
import cz.cvut.fit.timetracking.rest.dto.search.response.SearchProjectsResponse;
import cz.cvut.fit.timetracking.rest.dto.search.response.SearchUsersResponse;
import cz.cvut.fit.timetracking.rest.dto.search.response.SearchWorkRecordsResponse;
import cz.cvut.fit.timetracking.rest.mapper.RestModelMapper;
import cz.cvut.fit.timetracking.search.dto.ProjectDocument;
import cz.cvut.fit.timetracking.search.dto.UserDocument;
import cz.cvut.fit.timetracking.search.dto.WorkRecordDocument;
import cz.cvut.fit.timetracking.search.service.ProjectSearchService;
import cz.cvut.fit.timetracking.search.service.UserSearchService;
import cz.cvut.fit.timetracking.search.service.WorkRecordSearchService;
import cz.cvut.fit.timetracking.security.CurrentUser;
import cz.cvut.fit.timetracking.security.oauth2.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private WorkRecordSearchService workRecordSearchService;

    @Autowired
    private ProjectSearchService projectSearchService;

    @Autowired
    private UserSearchService userSearchService;

    @Autowired
    private RestModelMapper restModelMapper;

    @GetMapping("/users")
    public ResponseEntity<SearchUsersResponse> searchUsers(@RequestParam("keyword") String keyword) {
        List<UserDocument> userDocuments = userSearchService.searchUsers(keyword);
        List<UserSearchDTO> userSearchDTOs = userDocuments.stream().map(u -> restModelMapper.map(u, UserSearchDTO.class)).collect(Collectors.toList());
        return ResponseEntity.ok(new SearchUsersResponse(userSearchDTOs));
    }

    @GetMapping("/projects")
    public ResponseEntity<SearchProjectsResponse> searchProjects(@RequestParam("keyword") String keyword) {
        List<ProjectDocument> projectDocuments = projectSearchService.searchProjects(keyword);
        List<ProjectSearchDTO> projectSearchDTOs = projectDocuments.stream().map(u -> restModelMapper.map(u, ProjectSearchDTO.class)).collect(Collectors.toList());
        return ResponseEntity.ok(new SearchProjectsResponse(projectSearchDTOs));
    }

    @GetMapping("/work-records")
    public ResponseEntity<SearchWorkRecordsResponse> searchWorkRecords(@RequestParam("keyword") String keyword, @CurrentUser UserPrincipal userPrincipal) {
        List<WorkRecordDocument> workRecordDocuments = workRecordSearchService.searchWorkRecords(keyword, userPrincipal.getId());
        List<WorkRecordSearchDTO> workRecordDocumentsDTOs = workRecordDocuments.stream().map(u -> restModelMapper.map(u, WorkRecordSearchDTO.class)).collect(Collectors.toList());
        return ResponseEntity.ok(new SearchWorkRecordsResponse(workRecordDocumentsDTOs));
    }
}
