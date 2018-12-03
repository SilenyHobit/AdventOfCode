package advent2015.day17;

import util.InputLoader;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws Exception {
        List<Integer> containers = InputLoader.loadInput().stream()
                .map(Integer::parseInt)
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());

        Map<Integer, AtomicInteger> accumulator = new HashMap<>();
        calculate(150, containers, accumulator, 0);

        int res1 = accumulator.values().stream()
                .mapToInt(AtomicInteger::get)
                .sum();

        System.out.println(res1);

        int res2 = accumulator.entrySet().stream()
                .sorted(Comparator.comparingInt(Map.Entry::getKey))
                .limit(1)
                .findAny()
                .map(Map.Entry::getValue)
                .map(AtomicInteger::get)
                .get();

        System.out.println(res2);
    }

    static void calculate(int eggnog, List<Integer> containers, Map<Integer, AtomicInteger> countainerNumberToPermutations, int currentNumberOfContainers) {
        if (eggnog == 0) {
            AtomicInteger counter = countainerNumberToPermutations.computeIfAbsent(currentNumberOfContainers, i -> new AtomicInteger(0));
            counter.incrementAndGet();
            return;
        } else if (eggnog < 0)
            return;
        else if (containers.isEmpty())
            return;

        List<Integer> copy = new ArrayList<>(containers);
        Iterator<Integer> iter = copy.iterator();

        while (iter.hasNext()) {
            int capacity = iter.next();
            iter.remove();
            calculate(eggnog-capacity, copy, countainerNumberToPermutations, currentNumberOfContainers+1);
        }
    }

}
