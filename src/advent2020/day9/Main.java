package advent2020.day9;

import util.ExecutionWatcher;
import util.InputLoader;

import java.util.Arrays;

public class Main {

    private static final int PREAMBLE = 25;

    public static void main(String[] args) throws Exception {
        var watcher = new ExecutionWatcher();
        long[] numbers = InputLoader.loadInput().stream()
                .mapToLong(Long::parseLong)
                .toArray();

        watcher.parsed();

        int invalid = PREAMBLE;
        for (; invalid < numbers.length; invalid++) {
            if (!check(numbers, invalid)) {
                break;
            }
        }
        watcher.part1(numbers[invalid]);

        int start = 0;
        int end = 1;
        long sum;

        outer: for (start = invalid - 1; start >= 0; start--) {
            sum = numbers[start];
            for (end = start-1; end >= 1; end--) {
                sum += numbers[end];
                if (sum > numbers[invalid])
                    break;
                if (sum == numbers[invalid])
                    break outer;
            }
        }

        Arrays.sort(numbers, end, start+1);

        watcher.part2(numbers[start] + numbers[end]);

        watcher.finish();
    }

    private static boolean check(long[] numbers, int testedIndex) {
        for (int i = testedIndex-PREAMBLE; i < testedIndex; i++) {
            for (int j = i; j < testedIndex; j++) {
                if (numbers[i] + numbers[j] == numbers[testedIndex])
                    return true;
            }
        }

        return false;
    }

}
