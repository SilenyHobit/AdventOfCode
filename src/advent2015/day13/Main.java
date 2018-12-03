package advent2015.day13;

import util.InputLoader;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class Main {

    private static final Pattern gainPattern = Pattern.compile("([A-Za-z]+) would gain (\\d+) happiness units by sitting next to ([A-Za-z]+)");
    private static final Pattern losePattern = Pattern.compile("([A-Za-z]+) would lose (\\d+) happiness units by sitting next to ([A-Za-z]+)");

    public static void main(String[] args) throws Exception {
        List<String> input = InputLoader.loadInput();

        Map<String, Map<String, Integer>> relations = new HashMap<>();
        input.forEach(line -> {
            Matcher m = gainPattern.matcher(line);
            if (m.find()) {
                Map<String, Integer> inner = relations.computeIfAbsent(m.group(1), name -> new HashMap<>());
                inner.put(m.group(3), Integer.parseInt(m.group(2)));
            }

            m = losePattern.matcher(line);
            if (m.find()) {
                Map<String, Integer> inner = relations.computeIfAbsent(m.group(1), name -> new HashMap<>());
                inner.put(m.group(3), -Integer.parseInt(m.group(2)));
            }
        });

        printMax(relations);

        Map<String, Integer> myRel = new HashMap<>();
        relations.keySet().forEach(str -> myRel.put(str, 0));
        relations.values().forEach(map -> map.put("me", 0));
        relations.put("me", myRel);

        printMax(relations);
    }

    private static void printMax(Map<String, Map<String, Integer>> relations) {
        List<String> names = new ArrayList<>(relations.keySet());
        String name = names.remove(0);
        List<List<String>> accumulator = new ArrayList<>();
        permutate(names, new ArrayList<>(Collections.singletonList(name)), accumulator);

        System.out.println(accumulator.size());

        int max = accumulator.stream()
                .mapToInt(permutation -> value(permutation, relations))
                .max()
                .getAsInt();

        System.out.println(max);
    }

    private static void permutate(List<String> remaining, List<String> current, List<List<String>> accumulator) {
        if (remaining.isEmpty()) {
            accumulator.add(new ArrayList<>(current));
            return;
        }

        remaining.forEach(name -> {
            List<String> next = new ArrayList<>(remaining);
            next.remove(name);
            current.add(name);
            permutate(next, current, accumulator);
            current.remove(name);
        });
    }

    private static int value(List<String> permutation, Map<String, Map<String, Integer>> relations) {
        return IntStream.range(0, permutation.size())
                .map(i -> {
                    String name = permutation.get(i);
                    return relations.get(name).get(permutation.get((i-1 + permutation.size())%permutation.size())) +
                            relations.get(name).get(permutation.get((i+1)%permutation.size()));
                })
                .sum();
    }

}
