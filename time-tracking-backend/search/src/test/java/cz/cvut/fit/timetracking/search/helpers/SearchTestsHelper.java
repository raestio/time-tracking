package cz.cvut.fit.timetracking.search.helpers;

import cz.cvut.fit.timetracking.search.constants.ElasticsearchUserFieldNames;
import cz.cvut.fit.timetracking.search.dto.UserDocument;
import cz.cvut.fit.timetracking.search.utils.StringUtils;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SearchTestsHelper {

    public static void createIndex(String index, RestHighLevelClient restHighLevelClient) {
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(index);
        try {
            Assert.isTrue(restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT).isAcknowledged(), index + " cannot be created");
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public static void indexTestUser(String index, RestHighLevelClient restHighLevelClient) {
        IndexRequest indexRequest = new IndexRequest(index);
        indexRequest.id(String.valueOf(createUserDocument().getId()));
        try {
            indexRequest.source(buildUser(createUserDocument()));
            Assert.isTrue(restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT).status().getStatus() == RestStatus.CREATED.getStatus(), "document cannot be indexed in " + index);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    private static XContentBuilder buildUser(UserDocument userDocument) throws IOException {
        XContentBuilder xContentBuilder = XContentFactory.jsonBuilder();
        xContentBuilder.startObject();
        {
            xContentBuilder.field(ElasticsearchUserFieldNames.ID, userDocument.getId());
            xContentBuilder.field(ElasticsearchUserFieldNames.NAME, userDocument.getName());
            xContentBuilder.field(ElasticsearchUserFieldNames.SURNAME, userDocument.getSurname());
            xContentBuilder.field(ElasticsearchUserFieldNames.EMAIL, userDocument.getEmail());
            xContentBuilder.field(ElasticsearchUserFieldNames.PICTURE_URL, userDocument.getPictureUrl());
        }
        xContentBuilder.endObject();
        return xContentBuilder;
    }

    public static UserDocument createUserDocument() {
        UserDocument userDocument = new UserDocument();
        userDocument.setId(1);
        userDocument.setName("Rastislav");
        userDocument.setSurname("Zlacký");
        userDocument.setEmail("rastislav.zlacky@inventi.com");
        return userDocument;
    }

    public static void deleteIndex(String index, RestHighLevelClient restHighLevelClient) {
        try {
            Assert.isTrue(restHighLevelClient.indices().delete(new DeleteIndexRequest(index), RequestOptions.DEFAULT).isAcknowledged(), index + " cannot be deleted");
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public static Boolean documentIndexed(String index, Integer id, RestHighLevelClient restHighLevelClient) {
        QueryBuilder queryBuilder = QueryBuilders.termQuery("id", id);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder().query(queryBuilder);
        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.source(searchSourceBuilder);
        try {
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            return searchResponse.getHits().getHits().length > 0;
        } catch (IOException  e) {
            throw new RuntimeException();
        }
    }
}
