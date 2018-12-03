package advent2016.day3;

import util.InputLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private static final Pattern linePattern = Pattern.compile("([0-9]+)\\s*([0-9]+)\\s*([0-9]+)");

    public static void main(String[] args) throws Exception {
        List<String> input = InputLoader.loadInput();
        TriangleBuffer buffer = new TriangleBuffer();

        long validTriangles1 = input.stream()
                .map(line -> {
                    Matcher m = linePattern.matcher(line);
                    if (m.find()) {
                        buffer.addFirstColumn(Integer.parseInt(m.group(1)));
                        buffer.addSecondColumn(Integer.parseInt(m.group(2)));
                        buffer.addThirdColumn(Integer.parseInt(m.group(3)));
                        return new Triangle(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)), Integer.parseInt(m.group(3)));
                    } else
                        throw new IllegalArgumentException();
                })
                .filter(Triangle::isValid)
                .count();

        long validTriangles2 = buffer.getTriangles().stream().filter(Triangle::isValid).count();

        System.out.println(validTriangles1);
        System.out.println(validTriangles2);
    }

    private static class TriangleBuffer {

        private List<Triangle> triangles = new ArrayList<>();

        private TriangleBuilder first = new TriangleBuilder();
        private TriangleBuilder second = new TriangleBuilder();
        private TriangleBuilder third = new TriangleBuilder();


        void addFirstColumn(int val) {
            first.addSide(val);
            if (first.isComplete()) {
                triangles.add(first.build());
                first = new TriangleBuilder();
            }
        }

        void addSecondColumn(int val) {
            second.addSide(val);
            if (second.isComplete()) {
                triangles.add(second.build());
                second = new TriangleBuilder();
            }
        }

        void addThirdColumn(int val) {
            third.addSide(val);
            if (third.isComplete()) {
                triangles.add(third.build());
                third = new TriangleBuilder();
            }
        }

        List<Triangle> getTriangles() {
            return triangles;
        }
    }

    private static class TriangleBuilder {

        private List<Integer> sides = new ArrayList<>(3);

        void addSide(int val) {
            sides.add(val);
        }

        boolean isComplete() {
            return sides.size() == 3;
        }

        Triangle build() {
            return new Triangle(sides.get(0), sides.get(1), sides.get(2));
        }

    }

    private static class Triangle {
        private final int a;
        private final int b;
        private final int c;

        private Triangle(int a, int b, int c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }

        boolean isValid() {
            return a+b > c && a+c > b && b+c > a;
        }
    }

}
