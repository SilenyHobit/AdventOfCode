package advent2016.day8;

import util.InputLoader;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private static final Pattern rect = Pattern.compile("rect (\\d+)x(\\d+)");
    private static final Pattern rotateRow = Pattern.compile("rotate row y=(\\d+) by (\\d+)");
    private static final Pattern rotateColumn = Pattern.compile("rotate column x=(\\d+) by (\\d+)");

    public static void main(String[] args) throws Exception {
        List<String> input = InputLoader.loadInput();

        Screen screen = new Screen();

        input.forEach(line -> {
            Matcher m = rect.matcher(line);
            if (m.find()) {
                screen.rect(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)));
                return;
            }

            m = rotateRow.matcher(line);
            if (m.find()) {
                screen.rotateRow(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)));
                return;
            }

            m = rotateColumn.matcher(line);
            if (m.find()) {
                screen.rotateColumn(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)));
                return;
            }

            throw new IllegalArgumentException();
        });

        System.out.println(screen.sum());
        screen.print();
    }

    private static class Screen {

        private int[][] display = new int[50][6];

        void rect(int width, int height) {
            for (int col = 0; col < width; col++)
                for (int row = 0; row < height; row++)
                    display[col][row] = 1;
        }

        void rotateRow(int row, int rotation) {
            int[] tmp = new int[50];
            for (int i = 0; i < 50; i ++)
                tmp[(i+rotation)%50] = display[i][row];

            for (int i = 0; i < 50; i ++)
                display[i][row] = tmp[i];
        }

        void rotateColumn(int column, int rotation) {
            int[] tmp = new int[6];
            for (int i = 0; i < 6; i ++)
                tmp[(i+rotation)%6] = display[column][i];

            display[column] = tmp;
        }

        int sum() {
            return Arrays.stream(display)
                    .mapToInt(array -> Arrays.stream(array).sum())
                    .sum();
        }

        void print() {
            for (int row = 0; row < 6; row++) {
                for (int col = 0; col < 50; col++)
                    System.out.print(display[col][row] == 0 ? '.' : 'X');
                System.out.println();
            }
        }

    }

}
