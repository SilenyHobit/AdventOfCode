package advent2020.day2;

import util.Conversion;
import util.ExecutionWatcher;
import util.InputConverter;
import util.InputLoader;

import java.util.Collections;
import java.util.regex.Pattern;

public class Main {

    private static final Conversion<Password> PASSWORD_CONVERSION = new Conversion<>(Pattern.compile("(\\d+)-(\\d+) (\\w): (\\w+)"),
            m -> new Password(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)), m.group(3), m.group(4)));

    public static void main(String[] args) throws Exception {
        var watcher = new ExecutionWatcher();

        var aggregator = InputLoader.loadInput(new InputConverter<>(Collections.singletonList(PASSWORD_CONVERSION))).stream()
                .reduce(new PasswordAggregator(), PasswordAggregator::next, (a1, a2) -> a1);

        watcher.part1(builder -> builder.append(aggregator.task1()));
        watcher.part2(builder -> builder.append(aggregator.task2()));

        watcher.finish();
    }

}
