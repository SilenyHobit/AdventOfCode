package advent2018.day5;

import util.InputLoader;
import util.Printer;

import java.util.Comparator;
import java.util.Optional;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) throws Exception {
        Optional.of(InputLoader.loadInput().get(0))
                .map(line -> part1(new StringBuilder(line)))
                .map(line -> Printer.print(line.length(), line))
                .map(line -> Printer.print(part2(line), line))
                .orElseThrow(RuntimeException::new);
    }

    private static int part2(String input) {
        return IntStream.range('a', 'z'+1)
                .map(i -> part1(new StringBuilder(replace(input, i))).length())
                .min()
                .orElseThrow(IllegalArgumentException::new);
    }

    private static String replace(String s, int c) {
        return s.replace(Character.toString((char)c), "").replace(Character.toString((char)(c - 32)), "");
    }

    private static String part1(StringBuilder input) {
        return IntStream.range(-(input.length()-1), 0)
                .map(i -> -i)
                .filter(i -> i < input.length())
                .filter(i -> Math.abs(input.charAt(i-1) - input.charAt(i)) == 32)
                .mapToObj(i -> input.delete(i-1, i+1))
                .sorted(Comparator.comparingInt(builder -> builder.length()))
                .findFirst()
                .map(StringBuilder::toString)
                .orElse(input.toString());
    }
}
