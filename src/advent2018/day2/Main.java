package advent2018.day2;

import util.InputLoader;
import util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) throws Exception {
        List<String> input = InputLoader.loadInput();

        System.out.println(part1(input));
        System.out.println(part2(input));
    }

    private static long part1(List<String> input) {
        List<Map<Integer, Long>> rows = input.stream()
                .map(String::chars)
                .map(stream -> stream.boxed().collect(Collectors.groupingBy(Function.identity(), Collectors.counting())))
                .collect(Collectors.toList());

        long doubles = rows.stream()
                .filter(map -> map.values().contains(2L))
                .count();
        long triplets = rows.stream()
                .filter(map -> map.values().contains(3L))
                .count();

        return doubles * triplets;
    }

    private static String part2(List<String> input) {
        List<String> copy = new ArrayList<>(input);
        return input.stream()
                .filter(copy::remove)
                .flatMap(line -> copy.stream().map(line2 -> new Pair<>(line, line2)))
                .filter(pair -> differences(pair.getFirst(), pair.getSecond()) == 1)
                .map(pair -> formatDifference(pair.getFirst(), pair.getSecond()))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    private static int differences(String s1, String s2) {
        return IntStream.range(0, s1.length())
                .map(i -> s1.charAt(i) == s2.charAt(i) ? 0 : 1)
                .sum();
    }

    private static String formatDifference(String s1, String s2) {
        return IntStream.range(0, s1.length())
                .filter(i -> s1.charAt(i) == s2.charAt(i))
                .mapToObj(i -> Character.toString(s1.charAt(i)))
                .collect(Collectors.joining());
    }

}
