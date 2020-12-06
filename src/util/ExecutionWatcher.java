package util;

import java.time.Duration;
import java.time.Instant;
import java.util.function.Consumer;

public class ExecutionWatcher {

    private final StringBuilder builder = new StringBuilder();
    private final Instant start = Instant.now();
    private Duration part1;
    private Duration part2;

    public void part1(Consumer<StringBuilder> consumer) {
        part1 = Duration.between(start, Instant.now());
        consumer.accept(builder.append("Part 1: "));
        builder.append("\nPart 1 duration: ").append(part1);
        builder.append("\n-----------------------------------------------------------------\n");
    }

    public void part2(Consumer<StringBuilder> consumer) {
        part2 = Duration.between(start, Instant.now());
        consumer.accept(builder.append("Part 2: "));
        builder.append("\nPart 2 duration: ").append(part2);
        builder.append("\nPart 2 delay after part 1: ").append(part2.minus(part1));
        builder.append("\n-----------------------------------------------------------------\n");
    }

    public void finish() {
        Duration end = part1.compareTo(part2) > 0 ? part1 : part2;
        builder.append("Finished after ")
                .append(end);

        System.out.println(builder.toString());
    }

}
