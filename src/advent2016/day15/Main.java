package advent2016.day15;

import util.InputLoader;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {

    private static final Pattern discPattern = Pattern.compile("Disc #(\\d+) has (\\d+) positions; at time=0, it is at position (\\d+).");

    public static void main(String[] args) throws Exception {
        List<String> input = InputLoader.loadInput();

        List<Disc> discs = input.stream()
                .map(line -> {
                    Matcher m = discPattern.matcher(line);
                    m.find();
                    return new Disc(Integer.parseInt(m.group(2)), Integer.parseInt(m.group(3)), Integer.parseInt(m.group(1)));
                })
                .collect(Collectors.toList());

        System.out.println(calculateDrop(discs));

        discs.add(new Disc(11, 0, discs.size()+1));

        System.out.println(calculateDrop(discs));
    }

    private static int calculateDrop(List<Disc> discs) {
        AtomicInteger counter = new AtomicInteger(0);
        boolean capsuleStops = true;

        while (capsuleStops) {
            capsuleStops = discs.stream()
                    .anyMatch(disc -> !disc.canPass(counter.get()));
            counter.incrementAndGet();
        }

        return counter.get()-1;
    }

    private static class Disc {

        private final int size;
        private final int initial;
        private final int number;

        private Disc(int size, int initial, int number) {
            this.size = size;
            this.initial = initial;
            this.number = number;
        }

        boolean canPass(int time) {
            return (time + number + initial) % size == 0;
        }
    }

}
