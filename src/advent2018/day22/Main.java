package advent2018.day22;

import util.Conversion;
import util.InputConverter;
import util.InputLoader;
import util.Pair;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Main {

    private static final Conversion<Integer> DEPTH = new Conversion<>(Pattern.compile("depth: (\\d+)"), m -> Integer.parseInt(m.group(1)));
    private static final InputConverter<Integer> DEPTH_CONVERTER = new InputConverter<>(Collections.singletonList(DEPTH));
    private static final Conversion<Pair<Integer, Integer>> TARGET = new Conversion<>(Pattern.compile("target: (\\d+),(\\d+)"), m -> new Pair<>(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2))));
    private static final InputConverter<Pair<Integer, Integer>> TARGET_CONVERTER = new InputConverter<>(Collections.singletonList(TARGET));

    public static void main(String[] args) throws Exception {
        List<String> input = InputLoader.loadInput();
        int depth = DEPTH_CONVERTER.convert(input.get(0));
        Pair<Integer, Integer> target = TARGET_CONVERTER.convert(input.get(1));

        int[][] array = new int[100][1000];

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                array[i][j] = (geologicIndex(i, j, array, target) + depth) % 20183;
            }
        }

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                array[i][j] = array[i][j] % 3;
            }
        }

        System.out.println(Arrays.stream(array)
                .limit(target.getFirst()+1L)
                .mapToInt(a -> Arrays.stream(a).limit(target.getSecond()+1L).sum())
                .sum());

        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(Node::getTime));
        Map<Node, Node> visited = new HashMap<>();
        queue.add(new Node(0, 0, 1, 0));

        while (!queue.isEmpty()) {
            Node node = queue.poll();
            if (node.getX() == target.getFirst() && node.getY() == target.getSecond() && node.getTool() == 1) {
                System.out.println(node.getTime());
                break;
            }


            if (node.getX() < array.length - 1)
                generate(node, 1, 0, array, visited)
                        .forEach(queue::add);
            if (node.getY() < array[0].length - 1)
                generate(node, 0, 1, array, visited)
                        .forEach(queue::add);
            if (node.getX() > 0)
                generate(node, -1, 0, array, visited)
                        .forEach(queue::add);
            if (node.getY() > 0)
                generate(node, 0, -1, array, visited)
                        .forEach(queue::add);
        }
    }

    private static Stream<Node> generate(Node original, int xDiff, int yDiff, int[][] array, Map<Node, Node> visited) {
        int x = original.getX() + xDiff;
        int y = original.getY() + yDiff;
        return Stream.of(0, 1, 2)
                .filter(i -> i != array[original.getX()][original.getY()] && i != array[x][y])
                .map(i -> i == original.getTool() ? new Node(x, y, i, original.getTime() + 1) : new Node(x, y, i, original.getTime() + 8))
                .filter(node -> {
                    Node prev = visited.get(node);
                    return prev == null || prev.getTime() > node.getTime();
                })
                .peek(node -> visited.put(node, node));
    }

    private static int geologicIndex(int x, int y, int[][] array, Pair<Integer, Integer> target) {
        if (x == 0 && y == 0)
            return 0;
        if (x == target.getFirst() && y == target.getSecond())
            return 0;

        if (x == 0)
            return y * 48271;
        if (y == 0)
            return x * 16807;

        return array[x - 1][y] * array[x][y - 1];
    }

}
