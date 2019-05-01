package cz.cvut.fit.timetracking.search.service.impl;

import cz.cvut.fit.timetracking.search.exception.ElasticsearchSearchException;
import cz.cvut.fit.timetracking.search.service.ElasticsearchService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ElasticsearchServiceImpl implements ElasticsearchService {
    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Override
    public <T> List<T> search(String index, QueryBuilder queryBuilder, Function<SearchHit, T> mapFunction) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder().query(queryBuilder);
        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse;
        try {
            searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            LOGGER.error("Error occurred while searching in index: {} and query: {}", index, queryBuilder.toString());
            throw new ElasticsearchSearchException("Error occurred while searching in index: " + index, e);
        }
        List<T> result = Stream.of(searchResponse.getHits().getHits()).map(mapFunction).collect(Collectors.toList());
        return result;
    }
}
