package advent2017.day24;

import util.InputLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {

    private static final Pattern part = Pattern.compile("(\\d+)/(\\d+)");

    public static void main(String[] args) throws Exception {
        List<Part> parts = InputLoader.loadInput().stream()
                .map(line -> {
                    Matcher m = part.matcher(line);
                    m.find();
                    return new Part(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)));
                })
                .collect(Collectors.toList());

        AtomicInteger maximum = new AtomicInteger(0);
        calculate(parts, 0, 0, maximum);
        System.out.println(maximum);

        maximum = new AtomicInteger(0);
        AtomicInteger maxLength = new AtomicInteger(0);
        calculate(parts, 0, 0, 0, maximum, maxLength);
        System.out.println(maximum);

    }

    private static void calculate(List<Part> parts, int last, int strength, AtomicInteger maximum) {
        parts.stream()
                .filter(part -> part.supports(last))
                .forEach(part -> {
                    List<Part> copy = new ArrayList<>(parts);
                    copy.remove(part);
                    calculate(copy, part.other(last), strength + part.strength(), maximum);
                });

        if (maximum.get() < strength)
            maximum.set(strength);
    }

    private static void calculate(List<Part> parts, int last, int strength, int length, AtomicInteger maximum, AtomicInteger maxLength) {
        parts.stream()
                .filter(part -> part.supports(last))
                .forEach(part -> {
                    List<Part> copy = new ArrayList<>(parts);
                    copy.remove(part);
                    calculate(copy, part.other(last), strength + part.strength(), length + 1, maximum, maxLength);
                });

        if (maxLength.get() < length) {
            maxLength.set(length);
            maximum.set(strength);
        } else if (maxLength.get() == length && maximum.get() < strength) {
            maximum.set(strength);
        }
    }

    private static class Part {

        final int first;
        final int second;

        private Part(int first, int second) {
            this.first = first;
            this.second = second;
        }

        int other(int side) {
            if (side == first) return second;
            if (side == second) return first;
            throw new IllegalArgumentException();
        }

        boolean supports(int connection) {
            return first == connection || second == connection;
        }

        int strength() {
            return first + second;
        }
    }

}
