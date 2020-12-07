package advent2020.day3;

import util.ExecutionWatcher;
import util.InputLoader;

public class Main {

    public static void main(String[] args) throws Exception {
        var watcher = new ExecutionWatcher();

        char[][] input = InputLoader.loadInput().stream()
                .map(String::toCharArray)
                .toArray(char[][]::new);
        watcher.parsed();

        var hill = new Hill(input);
        watcher.part1(hill.part1());
        watcher.part2(hill.part2());

        watcher.finish();
    }

}
