package advent2015.day16;

import util.InputLoader;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {

    private static final Sue target = new Sue();

    static {
        target.children = 3;
        target.cats = 7;
        target.samoyeds = 2;
        target.pomeranians = 3;
        target.akitas = 0;
        target.vizslas = 0;
        target.goldfish = 5;
        target.trees = 3;
        target.cars = 2;
        target.perfumes = 1;
    }

    private static final Pattern suePattern = Pattern.compile("Sue (\\d+)");
    private static final Pattern childrenPattern = Pattern.compile("children: (\\d+)");
    private static final Pattern catsPattern = Pattern.compile("cats: (\\d+)");
    private static final Pattern samoyedsPattern = Pattern.compile("samoyeds: (\\d+)");
    private static final Pattern pomeraniansPattern = Pattern.compile("pomeranians: (\\d+)");
    private static final Pattern akitasPattern = Pattern.compile("akitas: (\\d+)");
    private static final Pattern vizslasPattern = Pattern.compile("vizslas: (\\d+)");
    private static final Pattern goldfishPattern = Pattern.compile("goldfish: (\\d+)");
    private static final Pattern treesPattern = Pattern.compile("trees: (\\d+)");
    private static final Pattern carsPattern = Pattern.compile("cars: (\\d+)");
    private static final Pattern perfumesPattern = Pattern.compile("perfumes: (\\d+)");

    public static void main(String[] args) throws Exception {
        List<Sue> candidates = InputLoader.loadInput().stream()
                .map(line -> {
                    Sue sue = new Sue();
                    sue.number = getNumber(suePattern, line);
                    sue.children = getNumber(childrenPattern, line);
                    sue.cats = getNumber(catsPattern, line);
                    sue.samoyeds = getNumber(samoyedsPattern, line);
                    sue.pomeranians = getNumber(pomeraniansPattern, line);
                    sue.akitas = getNumber(akitasPattern, line);
                    sue.vizslas = getNumber(vizslasPattern, line);
                    sue.goldfish = getNumber(goldfishPattern, line);
                    sue.trees = getNumber(treesPattern, line);
                    sue.cars = getNumber(carsPattern, line);
                    sue.perfumes = getNumber(perfumesPattern, line);
                    return sue;
                })
                .collect(Collectors.toList());

        Sue task1 = candidates.stream()
                .filter(target::match)
                .findAny()
                .get();

        System.out.println(task1.number);

        Sue task2 = candidates.stream()
                .filter(target::match2)
                .findAny()
                .get();

        System.out.println(task2.number);
    }

    private static int getNumber(Pattern p, String line) {
        Matcher m = p.matcher(line);
        if (m.find())
            return Integer.parseInt(m.group(1));
        else
            return -1;
    }

    private static class Sue {
        int number = -1;
        int children = -1;
        int cats = -1;
        int samoyeds = -1;
        int pomeranians = -1;
        int akitas = -1;
        int vizslas = -1;
        int goldfish = -1;
        int trees = -1;
        int cars = -1;
        int perfumes = -1;

        private static boolean matchNumber(int first, int second) {
            return second == -1 || first == second;
        }

        private static boolean matchLower(int first, int second) {
            return second == -1 || first > second;
        }

        private static boolean matchGreater(int first, int second) {
            return second == -1 || first < second;
        }

        boolean match(Sue sue) {
            boolean result = matchNumber(children, sue.children);
            result = result && matchNumber(cats, sue.cats);
            result = result && matchNumber(samoyeds, sue.samoyeds);
            result = result && matchNumber(pomeranians, sue.pomeranians);
            result = result && matchNumber(akitas, sue.akitas);
            result = result && matchNumber(vizslas, sue.vizslas);
            result = result && matchNumber(goldfish, sue.goldfish);
            result = result && matchNumber(trees, sue.trees);
            result = result && matchNumber(cars, sue.cars);
            result = result && matchNumber(perfumes, sue.perfumes);
            return result;
        }

        boolean match2(Sue sue) {
            boolean result = matchNumber(children, sue.children);
            result = result && matchGreater(cats, sue.cats);
            result = result && matchNumber(samoyeds, sue.samoyeds);
            result = result && matchLower(pomeranians, sue.pomeranians);
            result = result && matchNumber(akitas, sue.akitas);
            result = result && matchNumber(vizslas, sue.vizslas);
            result = result && matchLower(goldfish, sue.goldfish);
            result = result && matchGreater(trees, sue.trees);
            result = result && matchNumber(cars, sue.cars);
            result = result && matchNumber(perfumes, sue.perfumes);
            return result;
        }
    }

}
