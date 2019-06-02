package cz.cvut.fit.timetracking.search.service.impl;

import cz.cvut.fit.timetracking.search.component.ElasticsearchDocumentsMapper;
import cz.cvut.fit.timetracking.search.dto.ProjectDocument;
import cz.cvut.fit.timetracking.search.service.ElasticsearchService;
import cz.cvut.fit.timetracking.search.service.ProjectSearchService;
import cz.cvut.fit.timetracking.search.utils.DateUtils;
import cz.cvut.fit.timetracking.search.utils.StringUtils;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cz.cvut.fit.timetracking.search.constants.ElasticsearchProjectFieldNames.DESCRIPTION;
import static cz.cvut.fit.timetracking.search.constants.ElasticsearchProjectFieldNames.END;
import static cz.cvut.fit.timetracking.search.constants.ElasticsearchProjectFieldNames.NAME;
import static cz.cvut.fit.timetracking.search.constants.ElasticsearchProjectFieldNames.START;

@Service
public class ProjectSearchServiceImpl implements ProjectSearchService {
    private static final float NAME_BOOST = 3.0f;
    private static final float DESCRIPTION_BOOST = 1.0f;
    private static final String RANGE_QUERY_FORMAT = "dd.MM.yyyy||dd.M.yyyy||d.MM.yyyy||d.M.yyyy||yyyy-MM-dd";

    @Autowired
    private ElasticsearchService elasticsearchService;

    @Autowired
    private ElasticsearchDocumentsMapper elasticsearchDocumentsMapper;

    @Value("${time-tracking.search.elasticsearch.indexName.projects}")
    private String projectsIndexName;

    @Override
    public List<ProjectDocument> searchProjects(String keyword) {
        Assert.notNull(keyword, "keyword cannot be null");
        BoolQueryBuilder queryBuilder = createQueryForSearchingByKeyword(keyword);
        List<ProjectDocument> projects = elasticsearchService.search(projectsIndexName, queryBuilder, searchHit -> elasticsearchDocumentsMapper.mapHitToProject(searchHit));
        return projects;
    }

    private BoolQueryBuilder createQueryForSearchingByKeyword(String keyword) {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .should(new MultiMatchQueryBuilder(keyword).fields(getProjectFieldsForSearchByKeyword()).fuzziness(Fuzziness.AUTO))
                .should(new QueryStringQueryBuilder(StringUtils.wrapWordsInAsterisks(keyword)).fields(getProjectFieldsForSearchByKeyword()));
        if (DateUtils.canBeParsed(keyword, RANGE_QUERY_FORMAT)) {
            addRangeQueries(queryBuilder, keyword);
        }
        return queryBuilder;
    }

    private void addRangeQueries(BoolQueryBuilder queryBuilder, String keyword) {
        queryBuilder
                .should(new RangeQueryBuilder(START).format(RANGE_QUERY_FORMAT).gte(keyword).lte(keyword))
                .should(new RangeQueryBuilder(END).format(RANGE_QUERY_FORMAT).gte(keyword).lte(keyword));
    }

    private Map<String, Float> getProjectFieldsForSearchByKeyword() {
        Map<String, Float> projectFields = new HashMap<>();
        projectFields.put(NAME, NAME_BOOST);
        projectFields.put(DESCRIPTION, DESCRIPTION_BOOST);
        return projectFields;
    }
}
