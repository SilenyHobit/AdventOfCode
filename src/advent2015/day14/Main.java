package advent2015.day14;

import util.InputLoader;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {

    private static final Pattern pattern = Pattern.compile("([A-Za-z]+) can fly (\\d+) km/s for (\\d+) seconds, but then must rest for (\\d+) seconds");

    public static void main(String[] args) throws Exception {
        List<String> input = InputLoader.loadInput();

        List<Reindeer> reindeerList = input.stream()
                .map(line -> {
                    Matcher m = pattern.matcher(line);
                    m.find();
                    return new Reindeer(m.group(1), Integer.parseInt(m.group(2)), Integer.parseInt(m.group(3)), Integer.parseInt(m.group(4)));
                })
                .collect(Collectors.toList());

        int time = 2503;

        int result = reindeerList.stream()
                .mapToInt(reindeer -> reindeer.distance(time))
                .max()
                .getAsInt();

        System.out.println(result);

        AtomicInteger timer = new AtomicInteger(0);
        while (timer.getAndIncrement() < time) {
            List<Reindeer> results = reindeerList.stream()
                    .sorted(Comparator.<Reindeer>comparingInt(reindeer -> reindeer.distance(timer.get())).reversed())
                    .collect(Collectors.toList());

            //Soooo inefficient. But I just can't be bothered...
            results.stream().filter(reindeer -> results.get(0).distance(timer.get()) == reindeer.distance(timer.get())).forEach(Reindeer::addPoint);
        }

        Reindeer winner = reindeerList.stream()
                .sorted(Comparator.<Reindeer>comparingInt(reindeer -> reindeer.score).reversed())
                .limit(1)
                .findAny()
                .get();

        System.out.println(winner.score);
    }

    private static class Reindeer {
        private final String name;
        private final int speed;
        private final int runtime;
        private final int rest;

        private int score = 0;

        private Reindeer(String name, int speed, int runtime, int rest) {
            this.name = name;
            this.speed = speed;
            this.runtime = runtime;
            this.rest = rest;
        }

        int distance(int time) {
            int cycle = runtime + rest;
            int cycles = time/cycle;
            int remainder = time%cycle;

            return cycles*speed*runtime + Math.min(remainder, runtime)*speed;
        }

        void addPoint() {
            score++;
        }
    }

}
