package cz.cvut.fit.timetracking.search;

import cz.cvut.fit.timetracking.configuration.SearchTestsConfiguration;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class ElasticsearchTests extends SearchTestsConfiguration {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Test
    public void testCreateIndex() throws IOException {
        CreateIndexRequest createIndexRequest = new CreateIndexRequest("test_index");
        assertThat(restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT).isAcknowledged()).isTrue();
    }

}
