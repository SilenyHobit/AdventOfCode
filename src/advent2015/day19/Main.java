package advent2015.day19;

import util.InputLoader;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {

    private static final Pattern pattern = Pattern.compile("([a-zA-Z]+) => ([a-zA-Z]+)");

    public static void main(String[] args) throws Exception {
        List<String> input = InputLoader.loadInput();

        String molecule = input.get(input.size() - 1);

        List<Replacement> replacements = input.stream()
                .limit(input.size() - 2L)
                .map(line -> {
                    Matcher m = pattern.matcher(line);
                    m.find();
                    return new Replacement(m.group(1), m.group(2));
                })
                .collect(Collectors.toList());

        Set<String> products = new HashSet<>();

        replacements.forEach(replacement -> {
            Matcher m = Pattern.compile(replacement.original).matcher(molecule);
            int start = 0;
            while (m.find(start)) {
                StringBuffer buffer = new StringBuffer();
                m.appendReplacement(buffer, replacement.replacement);
                m.appendTail(buffer);
                products.add(buffer.toString());
                start = m.end(0);
            }
        });

        System.out.println(products.size());

        replacements.sort(Comparator.<Replacement>comparingInt(replacement -> replacement.replacement.length()).reversed());

        System.out.println(decompose(molecule, replacements));
    }

    // some strange property of input causes this to work o.O
    private static int decompose(String molecule, List<Replacement> replacements) {
        int counter = 0;
        while (!"e".equals(molecule)) {
            for (Replacement replacement : replacements) {
                if (molecule.contains(replacement.replacement)) {
                    int idx = molecule.lastIndexOf(replacement.replacement);
                    molecule = molecule.substring(0, idx) + replacement.original + molecule.substring(idx + replacement.replacement.length());
                    counter++;
                }
            }
        }

        return counter;
    }

    private static class Replacement {
        private final String original;
        private final String replacement;

        private Replacement(String original, String replacement) {
            this.original = original;
            this.replacement = replacement;
        }
    }

}
