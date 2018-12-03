package advent2018.day1;

import util.InputLoader;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) throws Exception {
        List<Long> input = InputLoader.loadInput().stream().map(Long::parseLong).collect(Collectors.toList());

        System.out.println(part1(input));
        System.out.println(part2(input));
    }

    private static long part1(List<Long> input) {
        return input.stream().reduce(0L, (a, b) -> a + b);
    }

    private static long part2(List<Long> input) {
        Set<Long> seen = new HashSet<>();
        AtomicLong frequency = new AtomicLong(0L);

        return Stream.generate(input::stream).flatMap(Function.identity())
                .map(frequencyChange -> {
                    seen.add(frequency.get());
                    return frequency.addAndGet(frequencyChange);
                })
                .filter(seen::contains)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

}
