package advent2020.day6;

import util.ExecutionWatcher;
import util.InputLoader;

public class Main {

    public static void main(String[] args) throws Exception {
        var watcher = new ExecutionWatcher();
        var groupAggregator = InputLoader.loadInput().stream()
                .reduce(new GroupAggregator(), GroupAggregator::next, (a1,a2) -> a1)
                .finish();
        watcher.part1(builder -> builder.append(groupAggregator.sum()));
        watcher.part2(builder -> builder.append(groupAggregator.sum2()));

        watcher.finish();
    }

}
