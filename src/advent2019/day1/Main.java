package advent2019.day1;

import util.ExecutionWatcher;
import util.InputLoader;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws Exception {
        var watcher = new ExecutionWatcher();
        long[] input = InputLoader.loadInput().stream()
                .mapToLong(Long::parseLong)
                .toArray();

        long result = Arrays.stream(input)
                .map(i -> (i/3L)-2L)
                .sum();
        watcher.part1(result);

        result = Arrays.stream(input)
                .map(i -> {
                    long fuel = 0L;
                    long addedFuel = (i/3L)-2L;
                    while (addedFuel > 0) {
                        fuel += addedFuel;
                        addedFuel = (addedFuel/3L)-2L;
                    }
                    return fuel;
                })
                .sum();
        watcher.part2(result);

        watcher.finish();
    }

}
