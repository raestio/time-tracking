package cz.cvut.fit.timetracking.search;

import cz.cvut.fit.timetracking.configuration.SearchTestsConfiguration;
import cz.cvut.fit.timetracking.search.dto.ProjectDocument;
import cz.cvut.fit.timetracking.search.dto.UserDocument;
import cz.cvut.fit.timetracking.search.helpers.SearchTestsHelper;
import cz.cvut.fit.timetracking.search.service.ProjectSearchService;
import cz.cvut.fit.timetracking.search.service.UserSearchService;
import org.awaitility.Awaitility;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

public class ProjectSearchServiceTests extends SearchTestsConfiguration {

    @Value("${time-tracking.search.elasticsearch.indexName.projects}")
    private String projectsIndexName;

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Autowired
    private ProjectSearchService projectSearchService;

    @Before
    public void init() throws InterruptedException {
        SearchTestsHelper.createIndex(projectsIndexName, restHighLevelClient);
        SearchTestsHelper.indexTestProject(projectsIndexName, restHighLevelClient);
        Awaitility.await().atMost(30, TimeUnit.SECONDS).until(() -> SearchTestsHelper.documentIndexed(projectsIndexName, SearchTestsHelper.createProjectDocument().getId(), restHighLevelClient));
    }

    @After
    public void cleanUp() {
        SearchTestsHelper.deleteIndex(projectsIndexName, restHighLevelClient);
    }

    @Test
    public void when_keywordIsAPI_expectProjectFound() {
        List<ProjectDocument> projects = projectSearchService.searchProjects("api");
        assertFoundAndEquals(projects);
    }

    @Test
    public void when_keywordIsGogle_expectProjectFound() {
        List<ProjectDocument> projects = projectSearchService.searchProjects("gogle");
        assertFoundAndEquals(projects);
    }

    @Test
    public void when_keywordIs2019_expectProjectNotFound() {
        List<ProjectDocument> projects = projectSearchService.searchProjects("2019");
        assertThat(projects).isEmpty();
    }

    @Test
    public void when_keywordIs25_4_2019_expectProjectFound() {
        List<ProjectDocument> projects = projectSearchService.searchProjects("25.4.2019");
        assertFoundAndEquals(projects);
    }

    @Test
    public void when_keywordIs2019_04_25_expectProjectFound() {
        List<ProjectDocument> projects = projectSearchService.searchProjects("2019-04-25");
        assertFoundAndEquals(projects);
    }

    @Test
    public void when_keywordIs25_4_2020_expectProjectNotFound() {
        List<ProjectDocument> projects = projectSearchService.searchProjects("25.4.2020");
        assertThat(projects).isEmpty();
    }

    @Test
    public void when_keywordIs25_expectProjectNotFound() {
        List<ProjectDocument> projects = projectSearchService.searchProjects("25");
        assertThat(projects).isEmpty();
    }

    private void assertFoundAndEquals(List<ProjectDocument> projects) {
        assertThat(projects).isNotEmpty();
        ProjectDocument projectDocument = projects.get(0);
        assertThat(projectDocument.getId()).isEqualTo(SearchTestsHelper.createProjectDocument().getId());
        assertThat(projectDocument.getDescription()).isEqualTo(SearchTestsHelper.createProjectDocument().getDescription());
        assertThat(projectDocument.getName()).isEqualTo(SearchTestsHelper.createProjectDocument().getName());
        assertThat(projectDocument.getStart()).isEqualTo(SearchTestsHelper.createProjectDocument().getStart());
        assertThat(projectDocument.getEnd()).isEqualTo(SearchTestsHelper.createProjectDocument().getEnd());
    }
}
