package advent2020.day7;

import util.Conversion;
import util.ExecutionWatcher;
import util.InputConverter;
import util.InputLoader;

import java.util.*;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Function;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.*;

public class Main {

    private static final String MY_COLOR = "shiny gold";

    private static final Pattern BAGS_PATTERN = Pattern.compile(" (\\d+) (\\w+ \\w+) bags?");

    private static final Conversion<Bag> FULL_BAG = new Conversion<>(Pattern.compile("(\\w+ \\w+) bags contain(( \\d+ (\\w+ \\w+) bags?,?)+)\\."),
            matcher -> {
                var bags = new HashMap<String, Integer>();
                var groupMatcher = BAGS_PATTERN.matcher(matcher.group(0));
                while (groupMatcher.find()) {
                    bags.put(groupMatcher.group(2), Integer.parseInt(groupMatcher.group(1)));
                }

                return new Bag(matcher.group(1), bags);
            });
    private static final Conversion<Bag> EMPTY_BAG = new Conversion<>(Pattern.compile("(\\w+ \\w+) bags contain no other bags\\."),
            matcher -> new Bag(matcher.group(1), Collections.emptyMap()));

    public static void main(String[] args) throws Exception {
        var watcher = new ExecutionWatcher();

        var bags = InputLoader.loadInput(new InputConverter<>(Arrays.asList(FULL_BAG, EMPTY_BAG))).stream()
                .collect(toMap(Bag::getColor, Function.identity()));

        bags.forEach((color, bag) -> bag.getBags().keySet()
                .forEach(innerBag -> bags.get(innerBag).addPotentialParent(bag))
        );

        watcher.parsed();

        watcher.part1(count(bags));

        var myBag = bags.get(MY_COLOR);
        watcher.part2(count2(myBag, bags));

        watcher.finish();
    }

    private static long count(Map<String, Bag> bags) {
        var myBag = bags.get(MY_COLOR);
        var visited = new HashSet<String>();
        visited.add(MY_COLOR);

        return countRec(visited, myBag) - 1;
    }

    private static long countRec(Set<String> visited, Bag current) {
        var bagCounter = new LongAdder();
        current.getPotentialParents().stream()
                .filter(bag -> !visited.contains(bag.getColor()))
                .forEach(bag -> {
                    visited.add(bag.getColor());
                    bagCounter.add(countRec(visited, bag));
                });

        bagCounter.increment();
        return bagCounter.sum();
    }

    private static long count2(Bag bag, Map<String, Bag> bags) {
        var counter = new LongAdder();
        bag.getBags().forEach((color, count) -> {
            Bag innerBag = bags.get(color);
            counter.add(count * (count2(innerBag, bags) + 1));
        });

        return counter.sum();
    }

}
