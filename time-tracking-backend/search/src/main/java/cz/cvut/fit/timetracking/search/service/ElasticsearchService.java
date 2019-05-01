package cz.cvut.fit.timetracking.search.service;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;

import java.util.List;
import java.util.function.Function;

public interface ElasticsearchService {
    <T> List<T> search(String index, QueryBuilder queryBuilder, Function<SearchHit, T> mapFunction);
}
