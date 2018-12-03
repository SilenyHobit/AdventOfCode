package advent2017.day14;

import advent2017.day10.StringHash;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {

    private static final String INPUT = "amgozmfv";

    public static void main(String[] args) {

        boolean[][] grid = IntStream.range(0, 128)
                .mapToObj(i -> INPUT + "-" + i)
                .map(str -> {
                    StringHash stringHash = new StringHash(str);
                    stringHash.performHash();
                    return stringHash.hash();
                })
                .map(Main::decode)
                .toArray(boolean[][]::new);

        System.out.println(Arrays.stream(grid).mapToLong(array -> stream(array).filter(b -> b).count()).sum());

        boolean[][] visited = new boolean[128][128];
        int counter = 0;

        for (int i = 0; i < 128; i++) {
            for (int j = 0; j < 128; j++) {
                if (grid[i][j] && !visited[i][j]) {
                    counter++;
                    checkRegion(i, j, grid, visited);
                }
            }
        }

        System.out.println(counter);
    }

    private static boolean[] decode(String s) {
        boolean[] result = new boolean[128];

        IntStream.range(0, s.length())
                .forEach(i -> {
                    int character = Integer.parseInt(String.valueOf(s.charAt(i)), 16);
                    for (int j = 3; j > -1; j--) {
                        result[i*4+j] = character%2 == 1;
                        character /= 2;
                    }
                });

        return result;
    }

    private static void checkRegion(int i, int j, boolean[][] grid, boolean[][] visited) {
        if (!grid[i][j] || visited[i][j])
            return;

        visited[i][j] = true;

        if (i > 0 && grid[i-1][j] && !visited[i-1][j])
            checkRegion(i-1, j, grid, visited);
        if (j > 0 && grid[i][j-1] && !visited[i][j-1])
            checkRegion(i, j-1, grid, visited);
        if (i < 127 && grid[i+1][j] && !visited[i+1][j])
            checkRegion(i+1, j, grid, visited);
        if (j < 127 && grid[i][j+1] && !visited[i][j+1])
            checkRegion(i, j+1, grid, visited);
    }

    private static Stream<Boolean> stream(boolean[] array) {
        return IntStream.range(0, array.length)
                .mapToObj(i -> array[i]);
    }

}
