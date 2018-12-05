package advent2018.day5;

import util.InputLoader;

import java.util.Comparator;
import java.util.Optional;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) throws Exception {
        String input = InputLoader.loadInput().get(0);

        System.out.println(part1(new StringBuilder(input)));
        System.out.println(part2(input));
    }

    private static int part2(String input) {
        return IntStream.range('a', 'z'+1)
                .map(i -> part1(new StringBuilder(replace(input, i))))
                .min()
                .orElseThrow(IllegalArgumentException::new);
    }

    private static String replace(String s, int c) {
        return s.replace(Character.toString((char)c), "").replace(Character.toString((char)(c - 32)), "");
    }

    private static int part1(StringBuilder input) {
        return IntStream.range(-(input.length()-1), 0)
                .map(i -> -i)
                .filter(i -> i < input.length())
                .filter(i -> Math.abs(input.substring(i-1,i+1).charAt(0) - input.substring(i-1,i+1).charAt(1)) == 32)
                .mapToObj(i -> input.delete(i-1, i+1))
                .sorted(Comparator.comparingInt(builder -> builder.length()))
                .map(builder -> builder.length())
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
