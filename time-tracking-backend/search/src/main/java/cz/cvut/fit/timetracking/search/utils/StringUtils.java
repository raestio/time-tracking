package cz.cvut.fit.timetracking.search.utils;

import org.springframework.util.Assert;

import java.util.StringJoiner;
import java.util.stream.Stream;

public class StringUtils {
    public static final String ASTERISKS_WRAP_FORMAT = "*%s*";

    public static String wrapWordsInAsterisks(String str) {
        Assert.notNull(str, "String value cannot be null");
        StringJoiner stringJoiner = new StringJoiner(" ");
        Stream.of(str.split(" ")).map(String::trim).filter(s -> !s.isBlank()).forEach(s -> stringJoiner.add(String.format(ASTERISKS_WRAP_FORMAT, s)));
        String result = stringJoiner.toString();
        return result;
    }
}
