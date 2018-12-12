package advent2018.day11;

import util.Pair;

import java.util.stream.LongStream;

public class Main {

    private static final long gridSN = 4151L;

    public static void main(String[] args) {
        long[][] array = new long[300][300];
        LongStream.range(0L, 300L).boxed()
                .flatMap(i -> LongStream.range(0L, 300L).mapToObj(j -> new Pair<>(i, j)))
                .forEach(pair -> {
                    long value = (((((pair.getFirst() + 11L) * (pair.getSecond() + 1L)) + gridSN) * (pair.getFirst() + 11L) / 100) % 10) - 5;
                    array[pair.getFirst().intValue()][pair.getSecond().intValue()] = value;
                });

        long max = 0;
        long x = 0;
        long y = 0;

        for (int i = 0; i < 297; i++)
            for (int j = 0; j < 297; j++) {
                long val = array[i][j];
                val += array[i][j + 1];
                val += array[i][j + 2];
                val += array[i + 1][j];
                val += array[i + 1][j + 1];
                val += array[i + 1][j + 2];
                val += array[i + 2][j];
                val += array[i + 2][j + 1];
                val += array[i + 2][j + 2];
                if (val > max) {
                    max = val;
                    x = i;
                    y = j;
                }
            }

        System.out.println((x + 1) + "," + (y + 1));
    }

}
