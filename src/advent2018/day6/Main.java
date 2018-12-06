package advent2018.day6;

import util.*;

import java.awt.*;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class Main {

    private static final int size = 400;
    private final static Conversion<Point> conversion = new Conversion<>(Pattern.compile("(\\d+), (\\d+)"),
            m -> new Point(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2))));

    public static void main(String[] args) throws Exception {
        IntStream.range(0, size)
                .boxed()
                .flatMap(i -> IntStream.range(0, size).mapToObj(j -> new Point(i, j)))
                .reduce(createField(), Field::addPoint, (a,b) -> a)
                .asOptional()
                .map(Field::printPart1)
                .map(Field::printPart2)
                .orElseThrow(RuntimeException::new);
    }

    private static Field createField() throws Exception {
        return new Field(InputLoader.loadInput(new InputConverter<>(Collections.singletonList(conversion))), size);
    }

}
