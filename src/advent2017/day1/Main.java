package advent2017.day1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("inputs/2017/day1"));
        char[] chars = lines.get(0).toCharArray();

        count(chars, 1, "First puzzle: ");
        count(chars, chars.length / 2, "Second puzzle: ");
    }

    private static void count(char[] chars, int increment, String prefix) {
        int counter = 0;

        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == chars[(i + increment) % chars.length]) {
                counter += chars[i] - '0';
            }
        }

        System.out.println(prefix + counter);
    }
}
