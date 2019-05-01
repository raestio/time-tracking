package cz.cvut.fit.timetracking.search;

import cz.cvut.fit.timetracking.configuration.SearchTestsConfiguration;
import cz.cvut.fit.timetracking.search.dto.UserDocument;
import cz.cvut.fit.timetracking.search.helpers.SearchTestsHelper;
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

public class UserSearchServiceTests extends SearchTestsConfiguration {

    @Value("${time-tracking.search.elasticsearch.indexName.users}")
    private String usersIndexName;

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Autowired
    private UserSearchService userSearchService;

    @Before
    public void init() throws InterruptedException {
        SearchTestsHelper.createIndex(usersIndexName, restHighLevelClient);
        SearchTestsHelper.indexTestUser(usersIndexName, restHighLevelClient);
        Awaitility.await().atMost(30, TimeUnit.SECONDS).until(() -> SearchTestsHelper.documentIndexed(usersIndexName, SearchTestsHelper.createUserDocument().getId(), restHighLevelClient));
    }

    @After
    public void cleanUp() {
        SearchTestsHelper.deleteIndex(usersIndexName, restHighLevelClient);
    }

    @Test
    public void when_keywordIsRast_expectUserFound() {
        List<UserDocument> users = userSearchService.searchUsers("rast");
        assertFoundAndEquals(users);
    }

    @Test
    public void when_keywordIsRastislavZlacky_expectUserFound() {
        List<UserDocument> users = userSearchService.searchUsers("rastislav zlacky");
        assertFoundAndEquals(users);
    }

    @Test
    public void when_keywordIsRastZla_expectUserFound() {
        List<UserDocument> users = userSearchService.searchUsers("rast zla");
        assertFoundAndEquals(users);
    }

    @Test
    public void when_keywordIsYlackz_expectUserFound() {
        List<UserDocument> users = userSearchService.searchUsers("ylackz");
        assertFoundAndEquals(users);
    }

    @Test
    public void when_keywordIsZlackyRastislav_expectUserFound() {
        List<UserDocument> users = userSearchService.searchUsers("zlacky rastislav");
        assertFoundAndEquals(users);
    }

    @Test
    public void when_keywordIsRastislawk_expectUserFound() {
        List<UserDocument> users = userSearchService.searchUsers("Rastislawk");
        assertFoundAndEquals(users);
    }

    @Test
    public void when_keywordIsRaskKlac_expectUserNotFound() {
        List<UserDocument> users = userSearchService.searchUsers("rask klac");
        assertThat(users).isEmpty();
    }

    @Test
    public void when_keywordIsZla_expectUserFound() {
        List<UserDocument> users = userSearchService.searchUsers("Zla");
        assertFoundAndEquals(users);
    }

    @Test
    public void when_keywordIsŘaštislavŽlacky_expectUserFound() {
        List<UserDocument> users = userSearchService.searchUsers("řaštislav žlacky");
        assertFoundAndEquals(users);
    }

    @Test
    public void when_keywordIsŠtislavŽlacky_expectUserFound() {
        List<UserDocument> users = userSearchService.searchUsers("štislav žlacky");
        assertFoundAndEquals(users);
    }

    private void assertFoundAndEquals(List<UserDocument> users) {
        assertThat(users).isNotEmpty();
        UserDocument userDocument = users.get(0);
        assertThat(userDocument.getId()).isEqualTo(SearchTestsHelper.createUserDocument().getId());
        assertThat(userDocument.getEmail()).isEqualTo(SearchTestsHelper.createUserDocument().getEmail());
        assertThat(userDocument.getName()).isEqualTo(SearchTestsHelper.createUserDocument().getName());
        assertThat(userDocument.getSurname()).isEqualTo(SearchTestsHelper.createUserDocument().getSurname());
        assertThat(userDocument.getPictureUrl()).isEqualTo(SearchTestsHelper.createUserDocument().getPictureUrl());
    }
}
