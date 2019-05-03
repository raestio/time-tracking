package cz.cvut.fit.timetracking.search;

import cz.cvut.fit.timetracking.configuration.SearchTestsConfiguration;
import cz.cvut.fit.timetracking.search.dto.UserDocument;
import cz.cvut.fit.timetracking.search.dto.WorkRecordDocument;
import cz.cvut.fit.timetracking.search.helpers.SearchTestsHelper;
import cz.cvut.fit.timetracking.search.service.UserSearchService;
import cz.cvut.fit.timetracking.search.service.WorkRecordSearchService;
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

public class WorkRecordSearchServiceTests extends SearchTestsConfiguration {

    @Value("${time-tracking.search.elasticsearch.indexName.workRecords}")
    private String workRecordsIndex;

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Autowired
    private WorkRecordSearchService workRecordSearchService;

    @Before
    public void init() throws InterruptedException {
        SearchTestsHelper.createIndex(workRecordsIndex, restHighLevelClient);
        SearchTestsHelper.indexTestWorkRecords(workRecordsIndex, restHighLevelClient);
        Awaitility.await().atMost(30, TimeUnit.SECONDS).until(() -> SearchTestsHelper.documentIndexed(workRecordsIndex, SearchTestsHelper.createWorkRecordDocument().getId(), restHighLevelClient));
    }

    @After
    public void cleanUp() {
        SearchTestsHelper.deleteIndex(workRecordsIndex, restHighLevelClient);
    }

    @Test
    public void when_keywordIsVyvoj_expectWorkRecordFound() {
        List<WorkRecordDocument> workRecordDocuments = workRecordSearchService.searchWorkRecords("vyvoj");
        assertThat(workRecordDocuments).hasSize(1);
        assertFoundAndEquals(workRecordDocuments);
    }

    @Test
    public void when_keywordIsLyza_expectWorkRecordFound() {
        List<WorkRecordDocument> workRecordDocuments = workRecordSearchService.searchWorkRecords("lyza");
        assertThat(workRecordDocuments).hasSize(2);
    }

    @Test
    public void when_keywordIsLyzaAndUserIdIs1_expectWorkRecordFound() {
        List<WorkRecordDocument> workRecordDocuments = workRecordSearchService.searchWorkRecords("lyza", 1);
        assertThat(workRecordDocuments).hasSize(1);
    }

    @Test
    public void when_keywordIs25_4_2019_expectWorkRecordFound() {
        List<WorkRecordDocument> workRecordDocuments = workRecordSearchService.searchWorkRecords("25.4.2019");
        assertThat(workRecordDocuments).hasSize(3);
    }

    @Test
    public void when_keywordIs25_4_2019AndUserIdIs2_expectWorkRecordFound() {
        List<WorkRecordDocument> workRecordDocuments = workRecordSearchService.searchWorkRecords("25.4.2019", 2);
        assertThat(workRecordDocuments).hasSize(1);
    }

    private void assertFoundAndEquals(List<WorkRecordDocument> workRecordDocuments) {
        assertThat(workRecordDocuments).isNotEmpty();
        WorkRecordDocument workRecord = workRecordDocuments.get(0);
        var createdWorkRecord = SearchTestsHelper.createWorkRecordDocument();
        assertThat(workRecord.getId()).isEqualTo(createdWorkRecord.getId());
        assertThat(workRecord.getDescription()).isEqualTo(createdWorkRecord.getDescription());
        assertThat(workRecord.getIdUser()).isEqualTo(createdWorkRecord.getIdUser());
        assertThat(workRecord.getDateFrom()).isEqualTo(createdWorkRecord.getDateFrom());
        assertThat(workRecord.getDateTo()).isEqualTo(createdWorkRecord.getDateTo());
    }
}
