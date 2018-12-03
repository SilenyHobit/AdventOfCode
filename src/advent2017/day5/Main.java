package advent2017.day5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("inputs/2017/day5"));
        List<Integer> parsedLines = lines.stream().map(Integer::parseInt).collect(Collectors.toList());
        int[] memory = new int[parsedLines.size()];

        for (int i = 0; i < parsedLines.size(); i++)
            memory[i] = parsedLines.get(i);

        int pointer = 0, counter = 0;

        while (pointer < memory.length && pointer > -1) {
            pointer += memory[pointer]++;
            counter++;
        }

        System.out.println("First: " + counter);

        for (int i = 0; i < parsedLines.size(); i++)
            memory[i] = parsedLines.get(i);

        pointer = 0;
        counter = 0;

        while (pointer < memory.length && pointer > -1) {
            if (memory[pointer] < 3)
                pointer += memory[pointer]++;
            else
                pointer += memory[pointer]--;
            counter++;
        }

        System.out.println("Second: " + counter);
    }
}
