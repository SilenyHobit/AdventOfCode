package advent2018.day6;

import util.*;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

    private static final int size = 1000;
    private final static Conversion<Point> conversion = new Conversion<>(Pattern.compile("(\\d+), (\\d+)"),
            m -> new Point(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2))));

    public static void main(String[] args) throws Exception {
        IntStream.range(-size/2, size/2)
                .boxed()
                .flatMap(i -> IntStream.range(-size/2, size/2).mapToObj(j -> new Point(i, j)))
                .reduce(createField(), Field::addPoint, (a,b) -> a)
                .asOptional()
                .map(Field::printPart1)
                .map(Field::printPart2)
                .orElseThrow(RuntimeException::new);
    }

    private static Field createField() throws Exception {
        return new Field(InputLoader.loadInput(new InputConverter<>(Collections.singletonList(conversion))), size);
    }

    private static class Field {
        private final Map<Point, AtomicLong> pointsClaim;
        private final List<Point> points;
        private final int lowerBound;
        private final int upperBound;

        private int areaCount;

        private Field(List<Point> points, int size) {
            this.points = points;
            this.pointsClaim = points.stream().collect(Collectors.toMap(Function.identity(),point -> new AtomicLong(0L)));
            this.lowerBound = -size/2;
            this.upperBound = (size/2)-1;
        }

        Field addPoint(Point point) {
            List<Pair<Point, Integer>> distances = points.stream()
                    .map(p2 -> new Pair<>(p2, Math.abs(point.x - p2.x) + Math.abs(point.y - p2.y)))
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

        public Field printPart1() {
            return pointsClaim.values().stream()
                    .map(AtomicLong::get)
                    .sorted(Comparator.reverseOrder())
                    .findFirst()
                    .map(i -> Printer.print(i, this))
                    .orElseThrow(RuntimeException::new);
        }

        public Field printPart2() {
            return Optional.of(areaCount)
                    .map(i -> Printer.print(i, this))
                    .orElseThrow(RuntimeException::new);
        }

        public Optional<Field> asOptional() {
            return Optional.of(this);
        }
    }

}
