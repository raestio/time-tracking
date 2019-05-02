package cz.cvut.fit.timetracking.search.service.impl;

import cz.cvut.fit.timetracking.search.component.ElasticsearchDocumentsMapper;
import cz.cvut.fit.timetracking.search.dto.ProjectDocument;
import cz.cvut.fit.timetracking.search.service.ElasticsearchService;
import cz.cvut.fit.timetracking.search.service.ProjectSearchService;
import cz.cvut.fit.timetracking.search.utils.StringUtils;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import static cz.cvut.fit.timetracking.search.constants.ElasticsearchProjectFieldNames.NAME;
import static cz.cvut.fit.timetracking.search.constants.ElasticsearchProjectFieldNames.DESCRIPTION;
import static cz.cvut.fit.timetracking.search.constants.ElasticsearchProjectFieldNames.START;
import static cz.cvut.fit.timetracking.search.constants.ElasticsearchProjectFieldNames.END;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProjectSearchServiceImpl implements ProjectSearchService {
    private static final float NAME_BOOST = 4.0f;
    private static final float DESCRIPTION_BOOST = 1.0f;
    private static final float START_BOOST = 1.5f;
    private static final float END_BOOST = 1.0f;

    @Autowired
    private ElasticsearchService elasticsearchService;

    @Autowired
    private ElasticsearchDocumentsMapper elasticsearchDocumentsMapper;

    @Value("${time-tracking.search.elasticsearch.indexName.projects}")
    private String projectsIndexName;

    @Override
    public List<ProjectDocument> searchProjects(String keyword) {
        Assert.notNull(keyword, "keyword cannot be null");
        QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .should(new MultiMatchQueryBuilder(keyword).fields(getProjectFieldsForSearchByKeyword()).fuzziness(Fuzziness.AUTO))
                .should(new QueryStringQueryBuilder(StringUtils.wrapWordsInAsterisks(keyword)).fields(getProjectFieldsForSearchByKeyword()));
        List<ProjectDocument> projects = elasticsearchService.search(projectsIndexName, queryBuilder, searchHit -> elasticsearchDocumentsMapper.mapHitToProject(searchHit));
        return projects;
    }

    private Map<String, Float> getProjectFieldsForSearchByKeyword() {
        Map<String, Float> projectFields = new HashMap<>();
        projectFields.put(NAME, NAME_BOOST);
        projectFields.put(DESCRIPTION, DESCRIPTION_BOOST);
        projectFields.put(START, START_BOOST);
        projectFields.put(END, END_BOOST);
        return projectFields;
    }
}
