package advent2017.day19;

import util.InputLoader;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) throws Exception {
        List<String> input = InputLoader.loadInput();

        int[] empty = new int[input.get(1).length()+1];
        Arrays.fill(empty, ' ');

        Stream<int[]> rows = input.stream()
                .map(line -> {
                    int[] row = new int[empty.length-line.length()];
                    Arrays.fill(row, ' ');
                    return IntStream.concat(line.chars(), IntStream.of(row)).toArray();
                });

        int[][] array = Stream.concat(Stream.concat(Stream.of(empty), rows), Stream.of(empty))
                .toArray(int[][]::new);

        int index = input.get(0).indexOf('|');
        array[0] = empty;

        Canvas canvas = new Canvas(array, new South(index, 1));

        int counter = 0;
        while (canvas.step()) {
            counter++;
        }

        System.out.println(canvas.visited.toString());
        System.out.println(counter+1);
    }

    private static class Canvas {

        private final int[][] array;
        private Facing facing;
        final StringBuilder visited = new StringBuilder();

        private Canvas(int[][] array, Facing facing) {
            this.array = array;
            this.facing = facing;
        }

        boolean step() {
            facing = facing.next(this);
            return facing != null;
        }
    }

    private static abstract class Facing {

        final int x;
        final int y;

        protected Facing(int x, int y) {
            this.x = x;
            this.y = y;
        }

        abstract Facing next(Canvas canvas);
    }

    private static class North extends Facing {

        protected North(int x, int y) {
            super(x, y);
        }

        @Override
        public Facing next(Canvas canvas) {
            switch (canvas.array[y][x]) {
                case '+':
                    if (canvas.array[y][x - 1] != ' ') return new East(x - 1, y);
                    if (canvas.array[y][x + 1] != ' ') return new West(x + 1, y);
                    return null;
                case '|':
                case '-':
                    if (canvas.array[y - 1][x] != ' ') return new North(x, y - 1);
                    return null;
                default:
                    canvas.visited.append(Character.toString((char) canvas.array[y][x]));
                    if (canvas.array[y - 1][x] != ' ') return new North(x, y - 1);
                    return null;
            }
        }
    }

    private static class South extends Facing {

        protected South(int x, int y) {
            super(x, y);
        }

        @Override
        Facing next(Canvas canvas) {
            switch (canvas.array[y][x]) {
                case '+':
                    if (canvas.array[y][x - 1] != ' ') return new East(x - 1, y);
                    if (canvas.array[y][x + 1] != ' ') return new West(x + 1, y);
                    return null;
                case '|':
                case '-':
                    if (canvas.array[y + 1][x] != ' ') return new South(x, y + 1);
                    return null;
                default:
                    canvas.visited.append(Character.toString((char) canvas.array[y][x]));
                    if (canvas.array[y + 1][x] != ' ') return new South(x, y + 1);
                    return null;
            }
        }
    }

    private static class East extends Facing {

        protected East(int x, int y) {
            super(x, y);
        }

        @Override
        Facing next(Canvas canvas) {
            switch (canvas.array[y][x]) {
                case '+':
                    if (canvas.array[y - 1][x] != ' ') return new North(x, y - 1);
                    if (canvas.array[y + 1][x] != ' ') return new South(x, y + 1);
                    return null;
                case '-':
                case '|':
                    if (canvas.array[y][x - 1] != ' ') return new East(x - 1, y);
                    return null;
                default:
                    canvas.visited.append(Character.toString((char) canvas.array[y][x]));
                    if (canvas.array[y][x - 1] != ' ') return new East(x - 1, y);
                    return null;
            }
        }
    }

    private static class West extends Facing {

        protected West(int x, int y) {
            super(x, y);
        }

        @Override
        Facing next(Canvas canvas) {
            switch (canvas.array[y][x]) {
                case '+':
                    if (canvas.array[y - 1][x] != ' ') return new North(x, y - 1);
                    if (canvas.array[y + 1][x] != ' ') return new South(x, y + 1);
                    return null;
                case '-':
                case '|':
                    if (canvas.array[y][x + 1] != ' ') return new West(x + 1, y);
                    return null;
                default:
                    canvas.visited.append(Character.toString((char) canvas.array[y][x]));
                    if (canvas.array[y][x + 1] != ' ') return new West(x + 1, y);
                    return null;
            }
        }
    }

}
