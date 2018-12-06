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
    private final java.util.List<Point> points;
    private final int lowerBound;
    private final int upperBound;

    private int areaCount;

    Field(java.util.List<Point> points, int size) {
        this.points = points;
        this.pointsClaim = points.stream().collect(Collectors.toMap(Function.identity(), point -> new AtomicLong(0L)));
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
