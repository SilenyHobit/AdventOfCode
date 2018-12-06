package advent2018.day6;

import util.Pair;
import util.Printer;

import java.awt.*;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.stream.Collectors;

class Field {
    private final Map<Point, AtomicLong> pointsClaim;
    private final List<Point> points;
    private final int size;

    private int areaCount;

    Field(List<Point> points, int size) {
        this.points = points;
        this.pointsClaim = points.stream().collect(Collectors.toMap(Function.identity(), point -> new AtomicLong(0L)));
        this.size = size - 1;
    }

    Field addPoint(Point point) {
        return Optional.of(distances(point))
                .map(this::checkForLimit)
                .map(list -> resolveClaim(list, point))
                .map(list -> this)
                .orElseThrow(RuntimeException::new);
    }

    private List<Pair<Point, Integer>> distances(Point point) {
        return points.stream()
                .map(p2 -> new Pair<>(p2, Math.abs(point.x - p2.x) + Math.abs(point.y - p2.y)))
                .sorted(Comparator.comparingInt(Pair::getSecond))
                .collect(Collectors.toList());
    }

    private List<Pair<Point, Integer>> checkForLimit(List<Pair<Point, Integer>> distances) {
        return Optional.of(distances)
                .map(dist -> dist.stream().mapToInt(Pair::getSecond).sum())
                .map(sum -> sum < 10000 ? ++areaCount : areaCount)
                .map(i -> distances)
                .orElseThrow(RuntimeException::new);
    }

    private long resolveClaim(List<Pair<Point, Integer>> distances, Point current) {
        return Optional.of(new Pair<>(distances.get(0), distances.get(1)))
                .filter(pair -> !pair.getFirst().getSecond().equals(pair.getSecond().getSecond()))
                .map(pair -> isOnEdge(current) ? remove(pair.getFirst().getFirst()) : increment(pair.getFirst().getFirst()))
                .orElse(0L);
    }

    private long increment(Point point) {
        return pointsClaim.getOrDefault(point, new AtomicLong(0L)).incrementAndGet();
    }

    private long remove(Point point) {
        return Optional.ofNullable(pointsClaim.remove(point)).map(AtomicLong::get).orElse(0L);
    }

    private boolean isOnEdge(Point point) {
        return point.x == 0 || point.x == size || point.y == 0 || point.y == size;
    }

    Field printPart1() {
        return pointsClaim.values().stream()
                .map(AtomicLong::get)
                .sorted(Comparator.reverseOrder())
                .findFirst()
                .map(i -> Printer.print(i, this))
                .orElseThrow(RuntimeException::new);
    }

    Field printPart2() {
        return Optional.of(areaCount)
                .map(i -> Printer.print(i, this))
                .orElseThrow(RuntimeException::new);
    }

    Optional<Field> asOptional() {
        return Optional.of(this);
    }
}
