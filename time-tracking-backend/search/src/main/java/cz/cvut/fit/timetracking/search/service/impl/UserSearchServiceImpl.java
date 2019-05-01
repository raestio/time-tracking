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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cz.cvut.fit.timetracking.search.constants.ElasticsearchUserFieldNames.EMAIL;
import static cz.cvut.fit.timetracking.search.constants.ElasticsearchUserFieldNames.NAME;
import static cz.cvut.fit.timetracking.search.constants.ElasticsearchUserFieldNames.SURNAME;

@Service
public class UserSearchServiceImpl implements UserSearchService {
    public static final float NAME_BOOST = 3.0f;
    public static final float SURNAME_BOOST = 3.0f;
    public static final float EMAIL_BOOST = 1.0f;

    @Autowired
    private ElasticsearchDocumentsMapper elasticsearchDocumentsMapper;

    @Value("${time-tracking.search.elasticsearch.indexName.users}")
    private String usersIndexName;

    @Autowired
    private ElasticsearchService elasticsearchService;

    @Override
    public List<UserDocument> searchUsers(String keyword) {
        QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .should(new MultiMatchQueryBuilder(keyword).fields(getUserFieldsForSearch()).fuzziness(Fuzziness.AUTO))
                .should(new QueryStringQueryBuilder(StringUtils.wrapWordsInAsterisks(keyword)).fields(getUserFieldsForSearch()));
        List<UserDocument> users = elasticsearchService.search(usersIndexName, queryBuilder, searchHit -> elasticsearchDocumentsMapper.mapHitToUser(searchHit));
        return users;
    }

    private Map<String, Float> getUserFieldsForSearch() {
        Map<String, Float> userFields = new HashMap<>();
        userFields.put(NAME, NAME_BOOST);
        userFields.put(SURNAME, SURNAME_BOOST);
        userFields.put(EMAIL, EMAIL_BOOST);
        return userFields;
    }
}
