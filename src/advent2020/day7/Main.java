package advent2020.day7;

import util.Conversion;
import util.ExecutionWatcher;
import util.InputConverter;
import util.InputLoader;

import java.util.*;
import java.util.concurrent.atomic.LongAdder;
import java.util.regex.Pattern;

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

        var bags = InputLoader.loadInput(new InputConverter<>(Arrays.asList(FULL_BAG, EMPTY_BAG)));
        watcher.parsed();

        watcher.part1(count(bags));

        var myBag = find(MY_COLOR, bags);
        watcher.part2(count2(myBag, bags));

        watcher.finish();
    }

    private static long count(List<Bag> bags) {
        var internalBags = new ArrayList<>(bags);
        Queue<String> toScan = new ArrayDeque<>();
        toScan.add(MY_COLOR);
        var myBag = find(MY_COLOR, bags);
        internalBags.remove(myBag);

        var bagCounter = new LongAdder();
        while (!toScan.isEmpty()) {
            var next = toScan.poll();
            var iterator = internalBags.iterator();
            while(iterator.hasNext()) {
                var bag = iterator.next();
                if (bag.getBags().containsKey(next)) {
                    iterator.remove();
                    toScan.add(bag.getColor());
                    bagCounter.increment();
                }
            }
        }

        return bagCounter.sum();
    }

    private static long count2(Bag bag, List<Bag> bags) {
        var counter = new LongAdder();
        bag.getBags().forEach((color, count) -> {
            Bag innerBag = find(color, bags);
            counter.add(count * (count2(innerBag, bags) + 1));
        });

        return counter.sum();
    }

    private static Bag find(String bagColor, List<Bag> bags) {
        return bags.stream()
                .filter(bag -> bag.getColor().equals(bagColor))
                .findFirst()
                .orElseThrow();
    }

}
