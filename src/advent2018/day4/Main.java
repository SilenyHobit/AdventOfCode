package advent2018.day4;

import advent2018.day4.events.Event;
import advent2018.day4.events.FallAsleep;
import advent2018.day4.events.ShiftStart;
import advent2018.day4.events.WakeUp;
import util.Conversion;
import util.InputConverter;
import util.InputLoader;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {

    private static final Conversion<Event> fallAsleep = new Conversion<>(Pattern.compile("\\[(.*)] falls asleep"), m -> new FallAsleep(m.group(1)));
    private static final Conversion<Event> wakeUp = new Conversion<>(Pattern.compile("\\[(.*)] wakes up"), m -> new WakeUp(m.group(1)));
    private static final Conversion<Event> startShift = new Conversion<>(Pattern.compile("\\[(.*)] Guard #(\\d+) begins shift"), m -> new ShiftStart(m.group(1), m.group(2)));

    public static void main(String[] args) throws Exception {
        InputConverter<Event> converter = new InputConverter<>(Arrays.asList(fallAsleep, wakeUp, startShift));
        List<Event> input = InputLoader.loadInputSorted(converter);

        System.out.println(part1(input));
        System.out.println(part2(input));
    }

    private static int part1(List<Event> input) {
        return compute(input, duties -> duties.stream().mapToInt(GuardDuty::sum).sum());
    }

    private static int part2(List<Event> input) {
        return compute(input, duties -> duties.stream().reduce(new DutyCombiner(), DutyCombiner::addDuty, (a, b) -> a).maxValue());
    }

    private static int compute(List<Event> input, Function<List<GuardDuty>, Integer> dutiesToValue) {
        return input.stream()
                .reduce(new GuardProcessor(), GuardProcessor::process, (a, b) -> a)
                .duties()
                .stream()
                .collect(Collectors.groupingBy(GuardDuty::guardId, Collectors.toList()))
                .entrySet()
                .stream()
                .sorted(Comparator.<Map.Entry<String, List<GuardDuty>>>comparingInt(entry -> dutiesToValue.apply(entry.getValue())).reversed())
                .findFirst()
                .map(Map.Entry::getValue)
                .map(List::stream)
                .orElseThrow(IllegalArgumentException::new)
                .reduce(new DutyCombiner(), DutyCombiner::addDuty, (a, b) -> a)
                .result();
    }

}
