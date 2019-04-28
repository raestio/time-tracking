package cz.cvut.fit.timetracking.jira.utils;

import java.time.LocalDate;

public class DateUtils {

    private DateUtils() {

    }

    public static boolean isBetween(LocalDate localDate, LocalDate fromInclusive, LocalDate toExclusive) {
        if (fromInclusive != null && toExclusive != null) {
            return (localDate.isEqual(fromInclusive) || localDate.isAfter(fromInclusive)) && localDate.isBefore(toExclusive);
        } else if (fromInclusive != null) {
            return (localDate.isEqual(fromInclusive) || localDate.isAfter(fromInclusive));
        } else if (toExclusive != null) {
            return localDate.isBefore(toExclusive);
        }
        return true;
    }
}
