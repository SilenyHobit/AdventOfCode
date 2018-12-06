package advent2018.day3;

import util.Conversion;
import util.InputConverter;
import util.InputLoader;
import util.Printer;

import java.awt.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {

    private static final Conversion<LocalRectangle> conversion = new Conversion<>(Pattern.compile("#(\\d+) @ (\\d+),(\\d+): (\\d+)x(\\d+)"),
            m -> new LocalRectangle(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)), Integer.parseInt(m.group(3)), Integer.parseInt(m.group(4)), Integer.parseInt(m.group(5))));

    public static void main(String[] args) throws Exception {
        Optional.of(InputLoader.loadInput(new InputConverter<>(Collections.singletonList(conversion))))
                .map(rectangles -> Printer.print(part1(rectangles), rectangles))
                .map(rectangles -> Printer.print(part2(rectangles), rectangles))
                .orElseThrow(RuntimeException::new);
    }

    private static int part1(List<LocalRectangle> rectangles) {
        return rectangles.stream()
                .flatMap(rectangle -> listCoordinates(rectangle.x, rectangle.y, rectangle.width, rectangle.height))
                .reduce(new Fabric(), Fabric::addPoint, /*never used ->*/ (a, b) -> a)
                .sum();
    }

    private static Stream<Point> listCoordinates(int x, int y, int width, int height) {
        return IntStream.range(x, width + x).boxed()
                .flatMap(i -> IntStream.range(y, height + y).mapToObj(j -> new Point(i, j)));
    }

    private static int part2(List<LocalRectangle> rectangles) {
        return rectangles.stream()
                .filter(rectangle -> overlaps(rectangle, rectangles) == 1L)
                .findFirst()
                .map(LocalRectangle::getId)
                .orElseThrow(IllegalArgumentException::new);
    }

    private static long overlaps(LocalRectangle rectangle, List<LocalRectangle> rectangles) {
        return rectangles.stream()
                .filter(rectangle::intersects)
                .count();
    }

    private static class LocalRectangle extends Rectangle {

        private final int id;

        public LocalRectangle(int id, int x, int y, int width, int height) {
            super(x, y, width, height);
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }

    private static class Fabric {
        private final int[][] fabric = new int[1000][1000];

        Fabric addPoint(Point point) {
            fabric[point.x][point.y]++;
            return this;
        }

        int sum() {
            return Arrays.stream(fabric)
                    .mapToInt(array -> Arrays.stream(array).map(i -> i > 1 ? 1 : 0).sum())
                    .sum();
        }
    }

}
