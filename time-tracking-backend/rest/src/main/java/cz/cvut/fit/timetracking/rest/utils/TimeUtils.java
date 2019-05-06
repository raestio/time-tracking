package cz.cvut.fit.timetracking.rest.utils;

import java.time.LocalDate;
import java.util.function.UnaryOperator;

public class TimeUtils {

    private TimeUtils() {

    }

    public static LocalDate getFrom(LocalDate fromInclusive, LocalDate toExclusive, LocalDate defaultIfValuseNull, UnaryOperator<LocalDate> ifToExclusiveNotNull) {
        if (fromInclusive == null && toExclusive == null) {
            return defaultIfValuseNull;
        } else if (fromInclusive == null) {
            return ifToExclusiveNotNull.apply(toExclusive);
        } else {
            return fromInclusive;
        }
    }

    public static LocalDate getTo(LocalDate toExclusive, LocalDate defaultIfToExclusiveNull) {
        if (toExclusive == null) {
            return defaultIfToExclusiveNull;
        }
        return toExclusive;
    }
}
