package advent2020.day4;

import util.ExecutionWatcher;
import util.InputLoader;

public class Main {

    public static void main(String[] args) throws Exception {
        var watcher = new ExecutionWatcher();
        var passportAggregator = InputLoader.loadInput().stream()
                .reduce(new PassportParser(), PassportParser::nextLine, (a1, a2) -> a1)
                .finish()
                .stream()
                .reduce(new PassportAggregator(), PassportAggregator::nextPassport, (a1, a2) -> a1);

        watcher.part1(builder -> builder.append(passportAggregator.part1()));
        watcher.part2(builder -> builder.append(passportAggregator.part2()));

        watcher.finish();
    }

}
