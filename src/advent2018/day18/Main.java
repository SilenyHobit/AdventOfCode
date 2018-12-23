package advent2018.day18;

import util.InputLoader;
import util.Pair;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) throws Exception {
        List<String> input = InputLoader.loadInput().stream().map(line -> ";" + line + ";").collect(Collectors.toList());
        String padding = new String(new char[input.get(0).length()]).replace("\0", ";");
        input.add(padding);
        input.add(0, padding);

        int[][] matrix = input.stream()
                .map(String::chars)
                .map(IntStream::toArray)
                .toArray(int[][]::new);

        Area area = new Area(matrix);

        int counter = 10;
        while (counter-- != 0)
            area.step();

        System.out.println(area.result());

          // a cycle of 56 steps starts on 472 and 10 steps were done already
        int required = (1000000000 - 472) - 10;
        int minimized = required % 56;
        counter = 472 + minimized;

        while (counter-- != 0) {
            area.step();
        }

        System.out.println(area.result());
    }

    private static class Area {

        private int[][] matrix;

        private List<Pair<int[][], int[][]>> transitions = new ArrayList<>();

        Area(int[][] matrix) {
            this.matrix = matrix;
        }

        void step() {
            Optional<int[][]> result = transitions.stream()
                    .filter(pair -> Arrays.deepEquals(pair.getFirst(), matrix))
                    .map(Pair::getSecond)
                    .findFirst();

            if (result.isPresent()) {
                matrix = result.get();
                return;
            }

            int[][] newMatrix = new int[matrix.length][matrix[0].length];
            for (int i = 1; i < matrix.length - 1; i++) {
                for (int j = 1; j < matrix.length - 1; j++) {
                    char c = (char) matrix[i][j];
                    switch (c) {
                        case '.':
                            newMatrix[i][j] = countSurround(i, j, '|') > 2 ? '|' : '.';
                            break;
                        case '|':
                            newMatrix[i][j] = countSurround(i, j, '#') > 2 ? '#' : '|';
                            break;
                        case '#':
                            newMatrix[i][j] = countSurround(i, j, '#') > 0 && countSurround(i, j, '|') > 0 ? '#' : '.';
                            break;
                    }
                }
            }

            transitions.add(new Pair<>(matrix, newMatrix));
            matrix = newMatrix;
        }

        int countSurround(int x, int y, char c) {
            int result = matrix[x + 1][y - 1] == c ? 1 : 0;
            result += matrix[x + 1][y] == c ? 1 : 0;
            result += matrix[x + 1][y + 1] == c ? 1 : 0;
            result += matrix[x][y + 1] == c ? 1 : 0;
            result += matrix[x - 1][y + 1] == c ? 1 : 0;
            result += matrix[x - 1][y] == c ? 1 : 0;
            result += matrix[x - 1][y - 1] == c ? 1 : 0;
            result += matrix[x][y - 1] == c ? 1 : 0;
            return result;
        }

        long result() {
            long forest = countChar('|');
            long lumberyard = countChar('#');
            return forest * lumberyard;
        }

        long countChar(char c) {
            return Arrays.stream(matrix)
                    .flatMapToInt(Arrays::stream)
                    .filter(i -> i == c)
                    .count();
        }
    }

}
