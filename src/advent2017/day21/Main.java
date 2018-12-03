package advent2017.day21;

import util.InputLoader;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {

    private static final Pattern rulePattern = Pattern.compile("([^=]+)=>(.+)");

    public static void main(String[] args) throws Exception {
        List<Rule> rules = InputLoader.loadInput().stream()
                .map(rulePattern::matcher)
                .filter(Matcher::matches)
                .map(matcher -> new Rule(matcher.group(1), matcher.group(2)))
                .collect(Collectors.toList());

        int[][] array = {{'.', '#', '.'}, {'.', '.', '#'}, {'#', '#', '#'}};

        int counter = 18;
        while (counter-- != 0) {
            List<int[][]> parts = split(array);
            int columns = array.length % 2 == 0 ? array.length / 2 : array.length / 3;

            parts = parts.stream()
                    .map(part -> rules.stream()
                            .filter(rule -> rule.matches(part))
                            .findFirst()
                            .get().target)
                    .collect(Collectors.toList());

            array = merge(parts, columns);
        }

        int result = Arrays.stream(array)
                .mapToInt(row -> Arrays.stream(row).map(i -> i == '#' ? 1 : 0).sum())
                .sum();
        System.out.println(result);
    }

    private static List<int[][]> split(int[][] array) {
        int size = array.length % 2 == 0 ? 2 : 3;
        int columns = array.length % 2 == 0 ? array.length / 2 : array.length / 3;
        int parts = columns * columns;
        List<int[][]> result = new ArrayList<>();

        for (int i = 0; i < parts; i++) {
            int[][] part = new int[size][size];

            int startRow = (i / columns) * size;
            int startColumn = (i % columns) * size;

            for (int j = 0; j < part.length; j++)
                for (int k = 0; k < part.length; k++)
                    part[j][k] = array[j + startRow][k + startColumn];
            result.add(part);
        }

        return result;
    }

    private static int[][] merge(List<int[][]> parts, int columns) {
        int size = parts.get(0).length * columns;
        int[][] result = new int[size][size];

        for (int i = 0; i < parts.size(); i++) {
            int[][] part = parts.get(i);

            int startRow = (i / columns) * part.length;
            int startColumn = (i % columns) * part.length;

            for (int j = 0; j < part.length; j++)
                for (int k = 0; k < part.length; k++)
                    result[j + startRow][k + startColumn] = part[j][k];
        }

        return result;
    }

    private static class Rule {

        private final List<int[][]> origin;
        private final int[][] target;

        private Rule(String origin, String target) {
            this.target = buildArray(target);
            this.origin = buildRules(buildArray(origin));
        }

        boolean matches(int[][] array) {
            for (int[][] rule : origin)
                if (Arrays.deepEquals(rule, array))
                    return true;

            return false;
        }

        private int[][] buildArray(String inputString) {
            String[] input = inputString.trim().split("/");
            int[][] inputArray = new int[input.length][input.length];

            switch (input.length) {
                case 4:
                    inputArray[3] = input[3].chars().toArray();
                case 3:
                    inputArray[2] = input[2].chars().toArray();
                case 2:
                    inputArray[0] = input[0].chars().toArray();
                    inputArray[1] = input[1].chars().toArray();
            }

            return inputArray;
        }

        private List<int[][]> buildRules(int[][] origin) {
            List<int[][]> result = new ArrayList<>();

            for (int i = 0; i < 4; i++) {
                origin = transpose(origin);
                result.add(origin);
                origin = flip(origin);
                result.add(origin);
            }


            Set<int[][]> toRemove = new HashSet<>();
            for (int i = 0; i < result.size(); i++)
                for (int j = i + 1; j < result.size(); j++)
                    if (Arrays.deepEquals(result.get(j), result.get(i)))
                        toRemove.add(result.get(j));

            result.removeAll(toRemove);
            return result;
        }

        private int[][] transpose(int[][] array) {
            int[][] result = new int[array.length][array.length];
            for (int i = 0; i < array.length; i++) {
                for (int j = i; j < array.length; j++) {
                    result[i][j] = array[j][i];
                    result[j][i] = array[i][j];
                }
            }

            return result;
        }

        private int[][] flip(int[][] array) {
            int[][] result = new int[array.length][array.length];
            for (int i = 0; i < array.length / 2; i++) {
                for (int j = 0; j < array.length; j++) {
                    result[i][j] = array[(array.length - 1) - i][j];
                    result[(array.length - 1) - i][j] = array[i][j];
                }
            }

            if (array.length == 3) {
                result[1][0] = array[1][0];
                result[1][1] = array[1][1];
                result[1][2] = array[1][2];
            }

            return result;
        }
    }

}
