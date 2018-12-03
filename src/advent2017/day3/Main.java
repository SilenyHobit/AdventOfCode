package advent2017.day3;

public class Main {

    private static final int input = 289326;

    public static void main(String[] args) {
        System.out.println("First: " + findLength(input));
        System.out.println("First: " + findValue(input));
    }

    private static int findLength(int address) {
        int square = (int) Math.sqrt(address);
        square = square % 2 == 0 ? square-1 : square;
        int closest = (int) Math.pow(square, 2);

        int shortAddress = address - closest;

        if (shortAddress == 0) {
            return square/2 + square/2;
        }

        while (shortAddress > square + 1) {
            shortAddress = shortAddress - (square + 1);
        }

        if (shortAddress < square/2) {
            return square/2 - shortAddress + square/2;
        } else {
            return shortAddress - square/2 + square/2;
        }
    }

    private static int findValue(int limit) {
        int[][] array = new int[1000][1000];
        int x = 501;
        int y = 500;
        array[500][500] = 1;
        int value = 1;

        while (value <= limit) {
            array[x][y] = array[x-1][y-1] + array[x][y-1] + array[x+1][y-1] + array[x+1][y] + array[x+1][y+1] + array[x][y+1] + array[x-1][y+1] + array[x-1][y];
            value = array[x][y];

            if (array[x-1][y] != 0 && array[x][y+1] == 0)
                y++;
            else if (array[x][y-1] != 0 && array[x-1][y] == 0)
                x--;
            else if (array[x][y-1] == 0 && array[x+1][y] != 0)
                y--;
            else
                x++;
        }

        return value;
    }

}
