package cz.cvut.fit.timetracking.search.utils;

import org.elasticsearch.common.time.DateFormatter;

public class DateUtils {

    private DateUtils() {

    }

    public static boolean canBeParsed(String input, String format) {
        try {
            DateFormatter.forPattern(format).parse(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
