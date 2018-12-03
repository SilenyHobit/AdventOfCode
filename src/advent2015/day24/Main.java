package advent2015.day24;

import util.InputLoader;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws Exception {
        List<Integer> input = InputLoader.loadInput().stream()
                .map(Integer::parseInt)
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());

        System.out.println(calculateFor(input, 3));
        System.out.println(calculateFor(input, 4));
    }

    static long calculateFor(List<Integer> input, int groupNumber) {
        int sum = input.stream().reduce(0, (a,b) -> a+b);
        int third = sum/groupNumber;

        List<List<Integer>> accumulator = new ArrayList<>();
        calculate(input, new ArrayList<>(), third, accumulator, 0);

        return accumulator.stream()
                .filter(list -> {
                    List<Integer> packages = new ArrayList<>(input);
                    packages.removeAll(list);
                    return checkValidity(packages, 0, third);
                })
                .sorted(new PackageComparator())
                .limit(1)
                .findFirst()
                .map(list -> list.stream().map(Integer::longValue).reduce(1L, (a,b) -> a*b))
                .get();
    }

    static void calculate(List<Integer> packages, List<Integer> firstGroup, int limit, List<List<Integer>> accummulator, int weight) {
        if (weight > limit)
            return;

        if (weight == limit) {
            accummulator.add(new ArrayList<>(firstGroup));
            return;
        }


        List<Integer> copy = new ArrayList<>(packages);
        for (Integer pck : packages) {
            firstGroup.add(pck);
            copy.remove(pck);
            calculate(copy, firstGroup, limit, accummulator, weight + pck);
            firstGroup.remove(pck);
        }
    }

    static boolean checkValidity(List<Integer> packages, int weight, int limit) {
        if (weight == limit)
            return true;

        if (weight > limit)
            return false;

        List<Integer> copy = new ArrayList<>(packages);
        for (Integer i : packages) {
            copy.remove(i);
            if (checkValidity(copy, weight + i, limit))
                return true;
        }

        return false;
    }

    private static class PackageComparator implements Comparator<List<Integer>> {

        @Override
        public int compare(List<Integer> integers, List<Integer> t1) {
            int result = Integer.compare(integers.size(), t1.size());
            if (result == 0) {
                long first = integers.stream().map(Integer::longValue).reduce(1L, (a,b) -> a*b);
                long second = t1.stream().map(Integer::longValue).reduce(1L, (a,b) -> a*b);
                result = Long.compare(first, second);
            }

            return result;
        }
    }

}
