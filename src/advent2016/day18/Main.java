package advent2016.day18;

import util.InputLoader;

public class Main {

    private static final int ROWS_1 = 40;
    private static final int ROWS_2 = 400000;

    public static void main(String[] args) throws Exception {
        String input = InputLoader.loadInput().get(0);

        System.out.println(safeCount(input, ROWS_1));
        System.out.println(safeCount(input, ROWS_2));
    }

    private static long safeCount(String input, int localRows) {
        long safeCount = 0L;
        Row row = new Row(input);

        while (localRows-- != 0) {
            safeCount += row.safeCount();
            row = row.next();
        }

        return safeCount;
    }

    private static class Row {

        private final String row;

        private Row(String row) {
            this.row = "." + row + ".";
        }

        Row next() {
            StringBuilder builder = new StringBuilder();
            for (int i = 1; i < row.length()-1; i++) {
                if (row.charAt(i-1) != row.charAt(i+1)) {
                    builder.append("^");
                } else {
                    builder.append(".");
                }
            }

            return new Row(builder.toString());
        }

        int safeCount() {
            return (int) row.chars().filter(c -> c == '.').count()-2;
        }
    }

}
