package advent2020.day1;

import util.ExecutionWatcher;
import util.InputLoader;

public class Main {

    public static void main(String[] args) throws Exception {
        var watcher = new ExecutionWatcher();
        long[] numbers = InputLoader.loadInput().stream()
                .mapToLong(Long::parseLong)
                .sorted()
                .toArray();

        boolean first = false;
        boolean second = false;
        boolean finished = false;

        for (int i = 0; i < numbers.length && !finished; i++) {
            for (int j = i + 1; j < numbers.length && !finished; j++) {
                long sum = numbers[i] + numbers[j];
                if (sum > 2020)
                    break;
                if (sum == 2020) {
                    long result = numbers[i] * numbers[j];
                    watcher.part1(builder -> builder.append(result));
                    first = true;
                    finished = second;
                }
                for (int k = j + 1; k < numbers.length && !finished; k++) {
                    long sum2 = numbers[i] + numbers[j] + numbers[k];
                    if (sum2 > 2020)
                        break;
                    if (sum2 == 2020) {
                        long result = numbers[i] * numbers[j] * numbers[k];
                        watcher.part2(builder -> builder.append(result));
                        second = true;
                        finished = first;
                    }
                }
            }
        }

        watcher.finish();
    }

}
