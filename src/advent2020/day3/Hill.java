package advent2020.day3;

import java.util.Arrays;

public class Hill {

    private final char[][] terrain;

    private int[] rightAdjusts = new int[]{1, 3, 5, 7, 1};
    private int[] right = new int[5];
    private int[] downAdjusts = new int[]{1, 1, 1, 1, 2};
    private long[] trees = new long[5];

    Hill(char[][] terrain) {
        this.terrain = terrain;
        analyze();
    }

    private void analyze() {
        for (int row = 0; row < terrain.length; row++) {
            for (int i = 0; i < downAdjusts.length; i++) {
                if (row % downAdjusts[i] == 0 && terrain[row][right[i]] == '#')
                    trees[i]++;
                right[i] = (right[i] + rightAdjusts[i]) % terrain[row].length;
            }
        }
    }

    long part1() {
        return trees[1];
    }

    long part2() {
        return Arrays.stream(trees).reduce(1L, (i1, i2) -> i1 * i2);
    }

}
