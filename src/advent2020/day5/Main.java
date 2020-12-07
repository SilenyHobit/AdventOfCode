package advent2020.day5;

import util.ExecutionWatcher;
import util.InputLoader;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws Exception {
        var watcher = new ExecutionWatcher();
        var passes = InputLoader.loadInput().stream()
                .map(Seat::new)
                .mapToInt(Seat::getId)
                .sorted()
                .toArray();

        watcher.parsed();

        watcher.part1(passes[passes.length-1]);

        int missingId = Arrays.stream(passes)
                .boxed()
                .reduce(new SeatAggregator(), SeatAggregator::next, (a1, a2) -> a1)
                .missingId();
        watcher.part2(missingId);

        watcher.finish();
    }

}
