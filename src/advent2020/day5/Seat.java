package advent2020.day5;

import java.util.StringJoiner;

public class Seat {

    private final int row;
    private final int column;
    private final int id;

    public Seat(String code) {
        int minRow = 0;
        int maxRow = 127;
        int minColumn = 0;
        int maxColumn = 7;

        for (char c : code.toCharArray()) {
            switch (c) {
                case 'B':
                    minRow = ((maxRow - minRow + 1) / 2) + minRow;
                    break;
                case 'F':
                    maxRow = ((maxRow - minRow) / 2) + minRow;
                    break;
                case 'R':
                    minColumn = ((maxColumn - minColumn + 1) / 2) + minColumn;
                    break;
                case 'L':
                    maxColumn = ((maxColumn - minColumn) / 2) + minColumn;
                    break;
            }
        }

        if (minColumn != maxColumn || minRow != maxRow)
            throw new IllegalArgumentException("Failed to find row or column!");

        this.row = minRow;
        this.column = minColumn;
        this.id = row * 8 + column;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Seat.class.getSimpleName() + "[", "]")
                .add("row=" + row)
                .add("column=" + column)
                .add("id=" + id)
                .toString();
    }
}
