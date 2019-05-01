package cz.cvut.fit.timetracking.search.service.impl;

import cz.cvut.fit.timetracking.search.dto.ProjectDocument;
import cz.cvut.fit.timetracking.search.dto.SearchAllResult;
import cz.cvut.fit.timetracking.search.dto.UserDocument;
import cz.cvut.fit.timetracking.search.dto.WorkRecordDocument;
import cz.cvut.fit.timetracking.search.service.SearchService;
import cz.cvut.fit.timetracking.search.service.UserSearchService;
import org.elasticsearch.action.search.MultiSearchRequest;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {



    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Autowired
    private UserSearchService userSearchService;

    @Override
    public SearchAllResult searchAll(String keyword) {
        return null;
    }

    @Override
    public List<ProjectDocument> searchProjects(String keyword) {
        return null;
    }

    @Override
    public List<UserDocument> searchUsers(String keyword) {
        /*List<UserDocument> userDocuments = userSearchService.searchUsers(keyword);
        SearchSourceBuilder searchRequest = new SearchSourceBuilder();
        SearchRequest s = new SearchRequest();
        QueryBuilders
                .boolQuery()
                .should(new MultiMatchQueryBuilder().fuzziness(Fuzziness.AUTO));

        MultiSearchResponse items = restHighLevelClient.multiSearch(null, null);
        items.getResponses()[0].getResponse().getHits().*/
        return null;
    }

    @Override
    public List<WorkRecordDocument> searchWorkRecords(String keyword) {
        return null;
    }
}
