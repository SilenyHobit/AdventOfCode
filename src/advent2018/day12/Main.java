package advent2018.day12;

import util.InputLoader;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {

    private static final Pattern statePattern = Pattern.compile("initial state: ([.#]+)");
    private static final Pattern rulePattern = Pattern.compile("([.#]+) => ([.#])");

    public static void main(String[] args) throws Exception {
        List<String> input = InputLoader.loadInput();
        Matcher stateMatcher = statePattern.matcher(input.get(0));
        stateMatcher.find();
        String initial = stateMatcher.group(1);

        Map<String, String> rules = input.stream()
                .skip(2L)
                .map(rulePattern::matcher)
                .filter(Matcher::find)
                .collect(Collectors.toMap(m -> m.group(1), m -> m.group(2)));

        int[] state = new int[100000];
        Arrays.fill(state, '.');
        for (int i = 0; i < initial.length(); i++)
            state[i + 500] = initial.charAt(i);

        int counter = 20;
        while (counter-- != 0) {
            state = step(state, rules);
        }

        int sum = 0;
        for (int i = 0; i < state.length; i++)
            if (state[i] == '#') {
                sum += i - 500;
            }
        System.out.println(sum);

        //gen 158 = 11373, increments of 81 afterwards
        System.out.println(((50000000000L - 158L) * 81L) + 11373L);
    }

    private static int[] step(int[] initial, Map<String, String> rules) {

        int[] result = Arrays.copyOf(initial, initial.length);
        for (int i = 0; i < initial.length - 5; i++) {
            String base = Arrays.stream(Arrays.copyOfRange(initial, i, i + 5))
                    .collect(StringBuilder::new,
                            StringBuilder::appendCodePoint,
                            StringBuilder::append)
                    .toString();
            result[i + 2] = (rules.getOrDefault(base, ".")).charAt(0);
        }

        return result;
    }

}
