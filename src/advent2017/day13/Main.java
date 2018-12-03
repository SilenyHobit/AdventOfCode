package advent2017.day13;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {

    private static final Pattern PATTERN = Pattern.compile("([0-9]+): ([0-9]+)");

    public static void main(String[] args) throws IOException {
        List<Layer> layers = Files.readAllLines(Paths.get("inputs/2017/day13")).stream()
                .map(line -> {
                    Matcher m = PATTERN.matcher(line);
                    m.find();
                    return new Layer(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)));
                })
                .collect(Collectors.toList());

        long severity = layers.stream()
                .filter(layer -> !layer.isSafe(0))
                .mapToLong(Layer::severity)
                .sum();

        System.out.println(severity);

        boolean unsafe = true;
        AtomicInteger counter = new AtomicInteger(0);

        while (unsafe) {
            counter.incrementAndGet();
            Optional<Layer> l = layers.stream()
                    .filter(layer -> !layer.isSafe(counter.get()))
                    .findAny();

            unsafe = l.isPresent();
        }

        System.out.println(counter.get());
    }

    private static class Layer {
        private final int depth;
        private final int length;
        private final int pathLength;

        private Layer(int depth, int length) {
            this.depth = depth;
            this.length = length;
            this.pathLength = (length-1)*2;
        }

        boolean isSafe(int startTime) {
            return (startTime + depth) % pathLength != 0;
        }

        int severity() {
            return depth*length;
        }
    }

}
