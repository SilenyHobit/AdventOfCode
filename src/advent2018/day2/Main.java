package advent2018.day2;

import util.InputLoader;
import util.Pair;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) throws Exception {
        List<String> input = InputLoader.loadInput();

        System.out.println(part1(input));
        System.out.println(part2(input));
    }

    private static int part1(List<String> input) {
        return input.stream()
                .map(String::chars)
                .map(stream -> stream.boxed().collect(Collectors.groupingBy(Function.identity(), Collectors.counting())))
                .reduce(new ChecksumGenerator(), ChecksumGenerator::addRow, /*never used ->*/ (a, b) -> a)
                .checksum();
    }

    private static String part2(List<String> input) {
        /*potentially inefficient (if duplicates appear), but it's a single statement method :D*/
        return input.stream()
                .flatMap(line -> pairLines(line, input))
                .filter(pair -> differences(pair.getFirst(), pair.getSecond()) == 1)
                .map(pair -> formatDifference(pair.getFirst(), pair.getSecond()))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    private static Stream<Pair<String, String>> pairLines(String line, List<String> input) {
        return input.subList(input.indexOf(line) + 1, input.size())
                .stream()
                .map(line2 -> new Pair<>(line, line2));
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

    private static class ChecksumGenerator {

        private int doubles = 0;
        private int triplets = 0;

        ChecksumGenerator addRow(Map<Integer, Long> row) {
            doubles += row.containsValue(2L) ? 1 : 0;
            triplets += row.containsValue(3L) ? 1 : 0;
            return this;
        }

        int checksum() {
            return doubles * triplets;
        }
    }

}
