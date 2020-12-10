package advent2020.day10;

import util.ExecutionWatcher;
import util.InputLoader;

import java.util.*;

import static java.util.stream.Collectors.toCollection;

public class Main {

    public static void main(String[] args) throws Exception {
        var watcher = new ExecutionWatcher();
        int[] adapters = InputLoader.loadInput().stream()
                .mapToInt(Integer::parseInt)
                .toArray();

        watcher.parsed();

        Arrays.sort(adapters);

        int[] diffs = new int[]{0, 0, 1};
        diffs[adapters[0] - 1]++;
        for (int i = 1; i < adapters.length; i++) {
            diffs[adapters[i] - adapters[i - 1] - 1]++;
        }

        watcher.part1(diffs[0] * diffs[2]);

        var list = Arrays.stream(adapters).boxed()
                .collect(toCollection(ArrayList::new));

        list.add(0, 0);
        list.add(list.get(list.size() - 1) + 3);

        watcher.part2(reduce(list));

        watcher.finish();
    }

    // everything has difference 1 and consecutive adapters produce same number of solutions as tribonacci for same number
    private static long reduce(List<Integer> adapters) {
        int[] tribonacci = {0, 1, 1, 2, 4, 7, 13};
        long result = 1;
        int consecutiveCount = 1;
        for (int i = 1; i < adapters.size(); i++) {
            if (adapters.get(i) - adapters.get(i-1) == 1) {
                consecutiveCount++;
            } else {
                result *= tribonacci[consecutiveCount];
                consecutiveCount = 1;
            }
        }
        return result;
    }

}
