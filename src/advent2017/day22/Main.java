package advent2017.day22;

import util.InputLoader;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws Exception {
        int[][] input = InputLoader.loadInput().stream()
                .map(line -> line.chars().toArray())
                .toArray(int[][]::new);

        calculate(10000, true, input);
        calculate(10000000, false, input);
    }

    private static void calculate(int count, boolean simple, int[][] input) {
        int[][] gridArray = new int[1001][1001];
        Arrays.stream(gridArray).forEach(array -> Arrays.fill(array, '.'));

        for (int i = 488; i < 513; i++)
            for (int j = 488; j < 513; j++)
                gridArray[i][j] = input[i - 488][j - 488];

        Grid grid = new Grid(gridArray, simple);
        Facing facing = new North(500, 500);

        while (count-- != 0)
            facing = facing.move(grid);

        System.out.println(grid.infections);
    }

    private static class Grid {
        final int[][] array;
        int infections = 0;
        final boolean simple;

        private Grid(int[][] array, boolean simple) {
            this.array = array;
            this.simple = simple;
        }

        private void toggle(int x, int y) {
            if (simple)
                toggleSimple(x, y);
            else
                toggleAdvanced(x, y);
        }

        private void toggleSimple(int x, int y) {
            switch (array[y][x]) {
                case '.':
                    array[y][x] = '#';
                    infections++;
                    break;
                case '#':
                    array[y][x] = '.';
            }
        }

        private void toggleAdvanced(int x, int y) {
            switch (array[y][x]) {
                case '.':
                    array[y][x] = 'W';
                    break;
                case 'W':
                    array[y][x] = '#';
                    infections++;
                    break;
                case '#':
                    array[y][x] = 'F';
                    break;
                case 'F':
                    array[y][x] = '.';
            }
        }
    }

    private static abstract class Facing {
        final int x;
        final int y;

        protected Facing(int x, int y) {
            this.x = x;
            this.y = y;
        }

        abstract Facing move(Grid grid);
    }

    private static class North extends Facing {

        protected North(int x, int y) {
            super(x, y);
        }

        @Override
        Facing move(Grid grid) {
            Facing next = null;
            switch (grid.array[y][x]) {
                case '#':
                    next = new East(x + 1, y);
                    break;
                case '.':
                    next = new West(x - 1, y);
                    break;
                case 'W':
                    next = new North(x, y-1);
                    break;
                case 'F':
                    next = new South(x, y+1);
                    break;
            }
            grid.toggle(x, y);
            return next;
        }
    }

    private static class East extends Facing {

        protected East(int x, int y) {
            super(x, y);
        }

        @Override
        Facing move(Grid grid) {
            Facing next = null;
            switch (grid.array[y][x]) {
                case '#':
                    next = new South(x, y + 1);
                    break;
                case '.':
                    next = new North(x, y - 1);
                    break;
                case 'W':
                    next = new East(x+1, y);
                    break;
                case 'F':
                    next = new West(x-1, y);
                    break;
            }
            grid.toggle(x, y);
            return next;
        }
    }

    private static class West extends Facing {

        protected West(int x, int y) {
            super(x, y);
        }

        @Override
        Facing move(Grid grid) {
            Facing next = null;
            switch (grid.array[y][x]) {
                case '#':
                    next = new North(x, y - 1);
                    break;
                case '.':
                    next = new South(x, y + 1);
                    break;
                case 'W':
                    next = new West(x-1, y);
                    break;
                case 'F':
                    next = new East(x+1, y);
                    break;
            }
            grid.toggle(x, y);
            return next;
        }
    }

    private static class South extends Facing {

        protected South(int x, int y) {
            super(x, y);
        }

        @Override
        Facing move(Grid grid) {
            Facing next = null;
            switch (grid.array[y][x]) {
                case '#':
                    next = new West(x - 1, y);
                    break;
                case '.':
                    next = new East(x + 1, y);
                    break;
                case 'W':
                    next = new South(x, y+1);
                    break;
                case 'F':
                    next = new North(x, y-1);
                    break;
            }
            grid.toggle(x, y);
            return next;
        }
    }

}
