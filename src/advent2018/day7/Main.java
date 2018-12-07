package advent2018.day7;

import util.*;

import java.util.*;
import java.util.regex.Pattern;

public class Main {

    private static final Conversion<Pair<String, String>> conversion = new Conversion<>(Pattern.compile("Step (.) must be finished before step (.) can begin."),
            m -> new Pair<>(m.group(1), m.group(2)));

    public static void main(String[] args) throws Exception {
        Optional.of(InputLoader.loadInput(new InputConverter<>(Collections.singletonList(conversion))))
                .map(Main::part1)
                .map(Main::part2)
                .orElseThrow(RuntimeException::new);
    }

    private static List<Pair<String, String>> part1(List<Pair<String, String>> input) {
        return input.stream()
                .reduce(new Plan(), Plan::process, (a,b) -> a)
                .asOptional()
                .map(Plan::path)
                .map(result -> Printer.print(result, input))
                .orElseThrow(RuntimeException::new);
    }

    private static List<Pair<String, String>> part2(List<Pair<String, String>> input) {
        return input.stream()
                .reduce(new Plan(), Plan::process, (a,b) -> a)
                .asOptional()
                .map(Scheduler::new)
                .map(Scheduler::perform)
                .map(result -> Printer.print(result, input))
                .orElseThrow(RuntimeException::new);
    }

}
