package cz.cvut.fit.timetracking.search.service.impl;

import cz.cvut.fit.timetracking.search.component.ElasticsearchDocumentsMapper;
import cz.cvut.fit.timetracking.search.dto.WorkRecordDocument;
import cz.cvut.fit.timetracking.search.service.ElasticsearchService;
import cz.cvut.fit.timetracking.search.service.WorkRecordSearchService;
import cz.cvut.fit.timetracking.search.utils.StringUtils;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cz.cvut.fit.timetracking.search.constants.ElasticsearchWorkRecordFieldNames.DATE_FROM;
import static cz.cvut.fit.timetracking.search.constants.ElasticsearchWorkRecordFieldNames.DATE_TO;
import static cz.cvut.fit.timetracking.search.constants.ElasticsearchWorkRecordFieldNames.DESCRIPTION;
import static cz.cvut.fit.timetracking.search.constants.ElasticsearchWorkRecordFieldNames.ID_USER;

@Service
public class WorkRecordSearchServiceImpl implements WorkRecordSearchService {
    private static final float DESCRIPTION_BOOST = 1.0f;
    private static final float FROM_BOOST = 1.0f;
    private static final float TO_BOOST = 1.0f;

    @Autowired
    private ElasticsearchService elasticsearchService;

    @Autowired
    private ElasticsearchDocumentsMapper elasticsearchDocumentsMapper;

    @Value("${time-tracking.search.elasticsearch.indexName.workRecords}")
    private String workRecordsIndexName;

    @Override
    public List<WorkRecordDocument> searchWorkRecords(String keyword) {
        Assert.notNull(keyword, "keyword cannot be null");
        QueryBuilder queryBuilder = createQueryForSearchByKeyword(keyword);
        return executeWorkRecordQuery(queryBuilder);
    }

    @Override
    public List<WorkRecordDocument> searchWorkRecords(String keyword, Integer userId) {
        Assert.notNull(keyword, "keyword cannot be null");
        Assert.notNull(userId, "userId cannot be null");
        QueryBuilder queryBuilder = createQueryForSearchByKeyword(keyword).filter(QueryBuilders.termQuery(ID_USER, userId));
        return executeWorkRecordQuery(queryBuilder);
    }

    private List<WorkRecordDocument> executeWorkRecordQuery(QueryBuilder queryBuilder) {
        List<WorkRecordDocument> workRecords = elasticsearchService.search(workRecordsIndexName, queryBuilder, searchHit -> elasticsearchDocumentsMapper.mapHitToWorkRecord(searchHit));
        return workRecords;
    }

    private BoolQueryBuilder createQueryForSearchByKeyword(String keyword) {
        return QueryBuilders.boolQuery()
                .should(new MultiMatchQueryBuilder(keyword).fields(getWorkRecordFieldsForSearchByKeyword()).fuzziness(Fuzziness.AUTO))
                .should(new QueryStringQueryBuilder(StringUtils.wrapWordsInAsterisks(keyword)).fields(getWorkRecordFieldsForSearchByKeyword()));
    }

    private Map<String, Float> getWorkRecordFieldsForSearchByKeyword() {
        Map<String, Float> workRecordFields = new HashMap<>();
        workRecordFields.put(DESCRIPTION, DESCRIPTION_BOOST);
        workRecordFields.put(DATE_FROM, FROM_BOOST);
        workRecordFields.put(DATE_TO, TO_BOOST);
        return workRecordFields;
    }
}
