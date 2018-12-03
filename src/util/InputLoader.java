package util;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputLoader {

    private static final Pattern pattern = Pattern.compile("advent(\\d{4})\\.(day\\d{1,2})");

    public static List<String> loadInput() throws Exception {
        return Arrays.stream(Thread.currentThread().getStackTrace())
                .map(StackTraceElement::toString)
                .skip(2L)
                .findFirst()
                .map(pattern::matcher)
                .filter(Matcher::find)
                .map(matcher -> CheckedWrapper.wrap(() -> Files.readAllLines(Paths.get("inputs", matcher.group(1), matcher.group(2)))))
                .orElseThrow(IllegalArgumentException::new);
    }

}
