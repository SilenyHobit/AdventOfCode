package advent2017.day6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {
        String line = Files.readAllLines(Paths.get("inputs/2017/day6")).get(0);
        List<Integer> parsedLines = Arrays.stream(line.split("\\t")).map(Integer::parseInt).collect(Collectors.toList());
        int[] memory = new int[parsedLines.size()];

        for (int i = 0; i < parsedLines.size(); i++)
            memory[i] = parsedLines.get(i);

        int counter = 0;
        List<int[]> previous = new ArrayList<>();

        while (!contains(previous, memory)) {
            previous.add(Arrays.copyOf(memory, memory.length));
            modify(memory);
            counter++;
        }

        System.out.println("First: " + counter);

        counter = 1;
        int[] start = Arrays.copyOf(memory, memory.length);
        modify(memory);

        while (!Arrays.equals(start, memory)) {
            counter++;
            modify(memory);
        }

        System.out.println("Second: " + counter);
    }

    private static boolean contains(List<int[]> list, int[] memory) {
        for (int[] array : list) {
            if (Arrays.equals(array, memory))
                return true;
        }

        return false;
    }

    private static void modify(int[] memory) {
        int index = maxIndex(memory);
        int value = memory[index];
        memory[index] = 0;

        while (value-- != 0) {
            index = (index + 1) % memory.length;
            memory[index]++;
        }
    }

    private static int maxIndex(int[] memory) {
        int index = 0;
        int max = 0;

        for (int i = 0; i < memory.length; i++)
            if (memory[i] > max) {
                index = i;
                max = memory[i];
            }

        return index;
    }

}
