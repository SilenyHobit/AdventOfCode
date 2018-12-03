package advent2018.day3;

import util.Conversion;
import util.InputConverter;
import util.InputLoader;

import java.awt.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {

    private static final Conversion<LocalRectangle> conversion = new Conversion<>(Pattern.compile("#(\\d+) @ (\\d+),(\\d+): (\\d+)x(\\d+)"),
            m -> new LocalRectangle(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)), Integer.parseInt(m.group(3)), Integer.parseInt(m.group(4)), Integer.parseInt(m.group(5))));

    public static void main(String[] args) throws Exception {
        InputConverter<LocalRectangle> converter = new InputConverter<>(Collections.singletonList(conversion));
        List<LocalRectangle> rectangles = InputLoader.loadInput().stream().map(converter::convert).collect(Collectors.toList());

        System.out.println(part1(rectangles));
        System.out.println(part2(rectangles));
    }

    private static long part1(List<LocalRectangle> rectangles) {
        int[][] fabric = rectangles.stream()
                .flatMap(rectangle -> listCoordinates(rectangle.x, rectangle.y, rectangle.width, rectangle.height))
                .reduce(new int[1000][1000], (array, point) -> {
                    array[point.x][point.y]++;
                    return array;
                }, /*never used ->*/ (a,b) -> a);

        return Arrays.stream(fabric)
                .mapToInt(array -> Arrays.stream(array).map(i -> i > 1 ? 1 : 0).sum())
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

}
