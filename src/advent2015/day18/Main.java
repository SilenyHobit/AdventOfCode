package advent2015.day18;

import util.InputLoader;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) throws Exception {
        Stream<int[]> input = InputLoader.loadInput().stream()
                .map(line -> {
                            IntStream stream = IntStream.concat(IntStream.of('.'), line.chars());
                            stream = IntStream.concat(stream, IntStream.of('.'));
                            return stream
                                    .map(i -> {
                                        if (i == '.')
                                            return 0;
                                        if (i == '#')
                                            return 1;

                                        throw new IllegalArgumentException();
                                    })
                                    .toArray();
                        }
                );

        input = Stream.concat(Stream.of(new int[102]), input);
        input = Stream.concat(input, Stream.of(new int[102]));

        int[][] inputArray = input.toArray(int[][]::new);

        Canvas canvas = new Canvas(inputArray);

        int counter = 100;
        while (counter-- > 0) {
            canvas.recalculate();
        }

        System.out.println(canvas.score());

        canvas = new Canvas(inputArray);

        counter = 100;
        while (counter-- > 0) {
            canvas.recalculate2();
        }

        System.out.println(canvas.score());
    }

    private static class Canvas {
        private int[][] array;

        private Canvas(int[][] array) {
            this.array = array;
        }

        void recalculate() {
            int[][] intermediate = new int[array.length][array[0].length];

            for (int i = 1; i < array.length-1; i++) {
                for (int j = 1; j < array[i].length-1; j++) {
                    int score = array[i-1][j] + array[i-1][j-1] + array[i-1][j+1] + array[i][j-1] + array[i][j+1] + array[i+1][j-1] + array[i+1][j] + array[i+1][j+1];

                    if ((array[i][j] == 0 && score == 3) || (array[i][j] == 1 && (score == 2 || score == 3)))
                        intermediate[i][j] = 1;
                }
            }

            array = intermediate;
        }

        void recalculate2() {
            recalculate();
            array[1][1] = 1;
            array[1][array[1].length-2] = 1;
            array[array.length-2][1] = 1;
            array[array.length-2][array[1].length-2] = 1;
        }

        int score() {
            return Arrays.stream(array)
                    .mapToInt(row -> Arrays.stream(row).sum())
                    .sum();
        }
    }

}
