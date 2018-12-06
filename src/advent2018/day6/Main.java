package advent2018.day6;

import util.Conversion;
import util.InputConverter;
import util.InputLoader;
import util.Pair;

import java.awt.*;
import java.io.PrintStream;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

    private static final int size = 1000;
    private static final AtomicInteger counter = new AtomicInteger(1);
    private final static Conversion<LocalPoint> conversion = new Conversion<>(Pattern.compile("(\\d+), (\\d+)"),
            m -> new LocalPoint(counter.incrementAndGet(), Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2))));

    public static void main(String[] args) throws Exception {
        List<LocalPoint> points = InputLoader.loadInput(new InputConverter<>(Collections.singletonList(conversion)));

        IntStream.range(-size/2, size/2)
                .boxed()
                .flatMap(i -> IntStream.range(-size/2, size/2).mapToObj(j -> new Point(i, j)))
                .reduce(new Field(points, size), Field::addPoint, (a,b) -> a)
                .asOptional()
                .map(field -> field.part1(System.out))
                .map(field -> field.part2(System.out))
                .orElseThrow(RuntimeException::new);
    }

    private static class LocalPoint extends Point {
        private final int index;

        private LocalPoint(int index, int x, int y) {
            super(x, y);
            this.index = index;
        }
    }

    private static class Field {
        private final Map<Integer, AtomicLong> pointsClaim;
        private final List<LocalPoint> points;
        private final int lowerBound;
        private final int upperBound;

        private int areaCount;

        private Field(List<LocalPoint> points, int size) {
            this.points = points;
            this.pointsClaim = points.stream().collect(Collectors.toMap(point -> point.index, point -> new AtomicLong(0L)));
            this.lowerBound = -size/2;
            this.upperBound = (size/2)-1;
        }

        Field addPoint(Point point) {
            List<Pair<Integer, Integer>> distances = points.stream()
                    .map(p2 -> new Pair<>(p2.index, Math.abs(point.x - p2.x) + Math.abs(point.y - p2.y)))
                    .sorted(Comparator.comparingInt(Pair::getSecond))
                    .collect(Collectors.toList());

            if (distances.stream().mapToInt(Pair::getSecond).sum() < 10000)
                areaCount++;

            boolean isClaimed = !distances.get(0).getSecond().equals(distances.get(1).getSecond());
            if (isOnEdge(point) && isClaimed)
                pointsClaim.remove(distances.get(0).getFirst());
            else if (isClaimed)
                pointsClaim.getOrDefault(distances.get(0).getFirst(), new AtomicLong(0L)).incrementAndGet();

            return this;
        }

        private boolean isOnEdge(Point point) {
            return point.x == lowerBound || point.x == upperBound || point.y == lowerBound || point.y == upperBound;
        }

        public Field part1(PrintStream writer) {
            return pointsClaim.values().stream()
                    .map(AtomicLong::get)
                    .sorted(Comparator.reverseOrder())
                    .findFirst()
                    .map(i -> writer.printf("%d%n", i))
                    .map(w -> this)
                    .orElseThrow(RuntimeException::new);
        }

        public Field part2(PrintStream writer) {
            return Optional.of(areaCount)
                    .map(i -> writer.printf("%d%n", i))
                    .map(w -> this)
                    .orElseThrow(RuntimeException::new);
        }

        public Optional<Field> asOptional() {
            return Optional.of(this);
        }
    }

}
