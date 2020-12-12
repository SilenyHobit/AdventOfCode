package advent2020.day12;

import util.Conversion;
import util.ExecutionWatcher;
import util.InputConverter;
import util.InputLoader;

import java.util.Arrays;
import java.util.regex.Pattern;

public class Main {

    private static final Conversion<Rule> FORWARD = new Conversion<>(Pattern.compile("F(\\d+)"), m -> new Forward(Integer.parseInt(m.group(1))));
    private static final Conversion<Rule> NORTH = new Conversion<>(Pattern.compile("N(\\d+)"), m -> new North(Integer.parseInt(m.group(1))));
    private static final Conversion<Rule> WEST = new Conversion<>(Pattern.compile("W(\\d+)"), m -> new West(Integer.parseInt(m.group(1))));
    private static final Conversion<Rule> SOUTH = new Conversion<>(Pattern.compile("S(\\d+)"), m -> new South(Integer.parseInt(m.group(1))));
    private static final Conversion<Rule> EAST = new Conversion<>(Pattern.compile("E(\\d+)"), m -> new East(Integer.parseInt(m.group(1))));
    private static final Conversion<Rule> LEFT = new Conversion<>(Pattern.compile("L(\\d+)"), m -> new Left(Integer.parseInt(m.group(1))));
    private static final Conversion<Rule> RIGHT = new Conversion<>(Pattern.compile("R(\\d+)"), m -> new Right(Integer.parseInt(m.group(1))));

    public static void main(String[] args) throws Exception {
        var watcher = new ExecutionWatcher();
        var rules = InputLoader.loadInput(new InputConverter<>(Arrays.asList(FORWARD, NORTH, WEST, SOUTH, EAST, LEFT, RIGHT)));
        watcher.parsed();

        var ferry = new Ferry();
        rules.forEach(rule -> rule.apply(ferry));

        watcher.part1(ferry.manhattanDistance());

        var ferry2 = new Ferry2();
        rules.forEach(rule -> rule.apply(ferry2));

        watcher.part2(ferry2.manhattanDistance());

        watcher.finish();
    }

}
