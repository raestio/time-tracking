package cz.cvut.fit.timetracking.search.service.impl;

import cz.cvut.fit.timetracking.search.component.ElasticsearchDocumentsMapper;
import cz.cvut.fit.timetracking.search.dto.UserDocument;
import cz.cvut.fit.timetracking.search.service.ElasticsearchService;
import cz.cvut.fit.timetracking.search.service.UserSearchService;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cz.cvut.fit.timetracking.search.constants.ElasticsearchUserFieldNames.NAME;
import static cz.cvut.fit.timetracking.search.constants.ElasticsearchUserFieldNames.SURNAME;

@Service
public class UserSearchServiceImpl implements UserSearchService {
    private static final float NAME_BOOST = 1.0f;
    private static final float SURNAME_BOOST = 1.0f;

    @Autowired
    private ElasticsearchDocumentsMapper elasticsearchDocumentsMapper;

    @Value("${time-tracking.search.elasticsearch.indexName.users}")
    private String usersIndexName;

    @Autowired
    private ElasticsearchService elasticsearchService;

    @Override
    public List<UserDocument> searchUsers(String keyword) {
        Assert.notNull(keyword, "keyword cannot be null");
        QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .should(new MultiMatchQueryBuilder(keyword).fields(getUserFieldsForSearchByKeyword()).fuzziness(Fuzziness.AUTO))
                .should(new QueryStringQueryBuilder(StringUtils.wrapWordsInAsterisks(keyword)).fields(getUserFieldsForSearchByKeyword()));
        List<UserDocument> users = elasticsearchService.search(usersIndexName, queryBuilder, searchHit -> elasticsearchDocumentsMapper.mapHitToUser(searchHit));
        return users;
    }

    private Map<String, Float> getUserFieldsForSearchByKeyword() {
        Map<String, Float> userFields = new HashMap<>();
        userFields.put(NAME, NAME_BOOST);
        userFields.put(SURNAME, SURNAME_BOOST);
        return userFields;
    }
}
