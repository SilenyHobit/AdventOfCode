package advent2019.day4;

import util.ExecutionWatcher;

import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    private static final int START = 146810;
    private static final int END = 612564;

    public static void main(String[] args) {
        var watcher = new ExecutionWatcher();

        AtomicInteger counter = new AtomicInteger(0);
        calculate(1, new int[6], 0, counter, false);
        watcher.part1(counter);

        counter = new AtomicInteger(0);
        calculate(1, new int[6], 0, counter, true);
        watcher.part2(counter);
        watcher.finish();
    }

    public static void calculate(int startDigit, int[] currentValue, int index, AtomicInteger counter, boolean enforceDouble) {
        if (index == 6) {
            boolean doubleValue = false;
            for (int i = 0; i < currentValue.length-1; i++) {
                if (currentValue[i] == currentValue[i+1]) {
                    if (enforceDouble && i < currentValue.length-2 && i > 0)
                        doubleValue = currentValue[i+2] != currentValue[i] && currentValue[i-1] != currentValue[i];
                    else if (enforceDouble && i < currentValue.length-2)
                        doubleValue = currentValue[i+2] != currentValue[i];
                    else if (enforceDouble && i > 0)
                        doubleValue = currentValue[i-1] != currentValue[i];
                    else
                        doubleValue = true;

                    if (doubleValue)
                        break;
                }
            }

            if (doubleValue) {
                int value = currentValue[0] * 100000
                        + currentValue[1] * 10000
                        + currentValue[2] * 1000
                        + currentValue[3] * 100
                        + currentValue[4] * 10
                        + currentValue[5];
                if(START <= value && value <= END)
                    counter.incrementAndGet();
            }

            return;
        }

        for (int i = startDigit; i < 10; i++) {
            currentValue[index] = i;
            calculate(i, currentValue, index+1, counter, enforceDouble);
        }
    }

}
