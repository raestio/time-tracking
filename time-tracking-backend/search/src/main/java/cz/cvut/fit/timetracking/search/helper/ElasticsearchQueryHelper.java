package cz.cvut.fit.timetracking.search.helper;

import org.springframework.util.Assert;

public class ElasticsearchQueryHelper {
    private static final String DAY_ROUND_FORMAT = "%s||/d";

    private ElasticsearchQueryHelper() {

    }

    public static String addDayRound(String value) {
        Assert.notNull(value, "value cannot be null");
        return String.format(DAY_ROUND_FORMAT, value);
    }
}
