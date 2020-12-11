package advent2020.day11;

import java.util.Arrays;

public class GameOfLife {

    private int[][] field;
    private final int adjacencyLimit;
    private final boolean deepFind;

    public GameOfLife(int[][] field, int adjacencyLimit, boolean deepFind) {
        this.adjacencyLimit = adjacencyLimit;
        this.deepFind = deepFind;
        this.field = new int[field.length+2][field[0].length+2];
        for (int i = 1; i < this.field.length-1; i++) {
            this.field[i][0] = '.';
            this.field[i][this.field[i].length-1] = '.';
            System.arraycopy(field[i-1], 0, this.field[i], 1, field[i-1].length);
        }
        Arrays.fill(this.field[0], '.');
        Arrays.fill(this.field[this.field.length-1], '.');
    }

    public boolean cycle() {
        int[][] newField = copyField();

        for (int i = 1; i < field.length-1; i++) {
            for (int j = 1; j < field[i].length-1; j++) {
                if (field[i][j] == 'L' && countOccupied(i,j) == 0)
                    newField[i][j] = '#';
                else if (field[i][j] == '#' && countOccupied(i, j) >= adjacencyLimit)
                    newField[i][j] = 'L';
            }
        }

        boolean result = !Arrays.deepEquals(field, newField);

        this.field = newField;

        return result;
    }

    public long countOccupied() {
        return Arrays.stream(field)
                .flatMap(row -> Arrays.stream(row).boxed())
                .filter(character -> character == '#')
                .count();
    }

    private int[][] copyField() {
        return Arrays.stream(field)
                .map(row -> Arrays.copyOf(row, row.length))
                .toArray(int[][]::new);
    }

    private int countOccupied(int i, int j) {
        int counter = 0;
        if (lookup(i, j, -1, -1) == '#') counter++;
        if (lookup(i, j, -1, 0) == '#') counter++;
        if (lookup(i, j, -1, 1) == '#') counter++;
        if (lookup(i, j, 0, -1) == '#') counter++;
        if (lookup(i, j, 0, 1) == '#') counter++;
        if (lookup(i, j, 1, -1) == '#') counter++;
        if (lookup(i, j, 1, 0) == '#') counter++;
        if (lookup(i, j, 1, 1) == '#') counter++;

        return counter;
    }

    private int lookup(int i, int j, int modI, int modJ) {
        if (deepFind) {
            i += modI;
            j += modJ;
            if (i > 0 && i < field.length-1 && j > 0 && j < field[i].length-1 && field[i][j] != '#' && field[i][j] != 'L')
                return lookup(i, j, modI, modJ);
            else
                return field[i][j];
        } else {
            return field[i+modI][j+modJ];
        }
    }

}
