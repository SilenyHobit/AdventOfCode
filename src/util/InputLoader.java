package util;

import advent2018.day4.events.Event;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class InputLoader {

    private static final Pattern pattern = Pattern.compile("advent(\\d{4})\\.(day\\d{1,2})");

    public static List<String> loadInput() throws Exception {
        return loadInput(3L);
    }

    public static <T> List<T> loadInputSorted(InputConverter<T> converter) throws Exception {
        return InputLoader.loadInput(3L).stream()
                .sorted()
                .map(converter::convert)
                .collect(Collectors.toList());
    }

    public static <T> List<T> loadInput(InputConverter<T> converter) throws Exception {
        return InputLoader.loadInput(3L).stream()
                .map(converter::convert)
                .collect(Collectors.toList());
    }

    private static List<String> loadInput(long skip) {
        return Arrays.stream(Thread.currentThread().getStackTrace())
                .map(StackTraceElement::toString)
                .skip(skip)
                .findFirst()
                .map(pattern::matcher)
                .filter(Matcher::find)
                .map(matcher -> CheckedWrapper.wrap(() -> Files.readAllLines(Paths.get("inputs", matcher.group(1), matcher.group(2)))))
                .orElseThrow(IllegalArgumentException::new);
    }

}
