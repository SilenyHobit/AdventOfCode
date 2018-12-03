package advent2017.day2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("inputs/2017/day2"));
        int result = 0, result2 = 0;

        topCycle: for (String line : lines) {
            List<Integer> numbers = Arrays.stream(line.split("\\t")).map(Integer::parseInt).collect(Collectors.toList());
            Collections.sort(numbers);
            Collections.reverse(numbers);

            result += numbers.get(0) - numbers.get(numbers.size()-1);

            for (int i = 0; i < numbers.size(); i++) {
                for (int j = i+1; j < numbers.size(); j++) {
                    if (numbers.get(i) % numbers.get(j) == 0) {
                        result2 += numbers.get(i)/numbers.get(j);
                        continue topCycle;
                    }
                }
            }
        }

        System.out.println("First puzzle: " + result);
        System.out.println("Second puzzle: " + result2);
    }
}
