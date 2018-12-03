package advent2016.day6;

import util.InputLoader;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) throws Exception {
        List<String> input = InputLoader.loadInput();

        List<List<Character>> columns = new ArrayList<>();

        IntStream.range(0, input.get(0).length()).forEach(i -> columns.add(new ArrayList<>()));

        input.forEach(line ->
                IntStream.range(0, line.length()).forEach(i -> columns.get(i).add(line.charAt(i)))
        );

        List<Map<Character, Long>> counted = columns.stream()
                .map(list -> list.stream()
                        .collect(Collectors.groupingBy(
                                Function.identity(),
                                Collectors.counting()
                                )
                        )
                )
                .collect(Collectors.toList());

        String result1 = counted.stream()
                .map(map -> map.entrySet().stream()
                        .sorted(Comparator.<Map.Entry<Character, Long>>comparingLong(Map.Entry::getValue).reversed())
                        .limit(1)
                        .map(Map.Entry::getKey)
                        .findFirst()
                        .get()
                )
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();

        String result2 = counted.stream()
                .map(map -> map.entrySet().stream()
                        .sorted(Comparator.comparingLong(Map.Entry::getValue))
                        .limit(1)
                        .map(Map.Entry::getKey)
                        .findFirst()
                        .get()
                )
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();

        System.out.println(result1);
        System.out.println(result2);
    }

}
