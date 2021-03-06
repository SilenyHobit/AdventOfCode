package util;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExecutionWatcher {

    private static final Pattern pattern = Pattern.compile("advent(\\d{4})\\.(day\\d{1,2})");

    private final StringBuilder builder;
    private final Instant start;
    private Instant parsed;
    private Duration parseDuration = Duration.ZERO;
    private Duration part1;
    private Duration part2;

    public ExecutionWatcher() {
        this.builder = new StringBuilder();
        Arrays.stream(Thread.currentThread().getStackTrace())
                .map(StackTraceElement::toString)
                .skip(2L)
                .findFirst()
                .map(pattern::matcher)
                .filter(Matcher::find)
                .ifPresent(matcher -> builder.append("RUNNING ").append(matcher.group(1)).append('-').append(matcher.group(2).replace("day", "")).append('\n')
                        .append("-----------------------------------------------------------------\n"));
        this.start = Instant.now();
    }

    public void parsed() {
        parsed = Instant.now();
        parseDuration = Duration.between(start, parsed);
        builder.append("Parsed input in ").append(parseDuration).append('\n')
                .append("-----------------------------------------------------------------\n");
    }

    public void part1(Object result) {
        var from = parsed == null ? start : parsed;
        part1 = Duration.between(from, Instant.now());
        builder.append("Part 1: ").append(result).append('\n')
                .append("Part 1 duration: ").append(part1).append('\n')
                .append("-----------------------------------------------------------------\n");
    }

    public void part2(Object result) {
        var from = parsed == null ? start : parsed;
        part2 = Duration.between(from, Instant.now());
        builder.append("Part 2: ").append(result).append('\n')
                .append("Part 2 duration: ").append(part2).append('\n')
                .append("Part 2 delay after part 1: ").append(part2.minus(part1)).append('\n')
                .append("-----------------------------------------------------------------\n");
    }

    public void finish() {
        Duration end = part1.compareTo(part2) > 0 ? part1 : part2;
        builder.append("Finished after ")
                .append(end.plus(parseDuration));

        System.out.println(builder.toString());
    }

}
