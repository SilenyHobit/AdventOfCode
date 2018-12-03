package advent2016.day24;

import util.InputLoader;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) throws Exception {
        Node[] nodes = new Node[8];

        int[][] room = InputLoader.loadInput().stream()
                .map(line -> line.chars().toArray())
                .toArray(int[][]::new);

        for (int i = 0; i < room.length; i++) {
            for (int j = 0; j < room[i].length; j++) {
                if (room[i][j] >= '0' && room[i][j] <='9') {
                    nodes[room[i][j] - '0'] = new Node(j, i, 0);
                    room[i][j] = '.';
                }
            }
        }

        int[][] distances = new int[nodes.length][nodes.length];
        for (int i = 0; i < nodes.length; i++) {
            for (int j = i+1; j < nodes.length; j++) {
                distances[i][j] = bfs(nodes[i], nodes[j], room);
                distances[j][i] = distances[i][j];
            }
        }

        AtomicInteger result = new AtomicInteger(Integer.MAX_VALUE);
        List<Integer> nodesList = IntStream.range(1, nodes.length).boxed().collect(Collectors.toList());
        calculate(nodesList, 0, 0, result, distances);

        System.out.println(result);

        result = new AtomicInteger(Integer.MAX_VALUE);
        calculate2(nodesList, 0, 0, result, distances);

        System.out.println(result);
    }

    private static void calculate2(List<Integer> nodes, int last, int length, AtomicInteger minimum, int[][] distances) {
        if (nodes.isEmpty()) {
            if (minimum.get() > length + distances[last][0])
                minimum.set(length + distances[last][0]);
            return;
        }

        nodes.forEach(node -> {
            List<Integer> copy = new ArrayList<>(nodes);
            copy.remove(node);
            calculate2(copy, node, length + distances[node][last], minimum, distances);
        });
    }

    private static void calculate(List<Integer> nodes, int last, int length, AtomicInteger minimum, int[][] distances) {
        if (nodes.isEmpty()) {
            if (minimum.get() > length)
                minimum.set(length);
            return;
        }

        nodes.forEach(node -> {
            List<Integer> copy = new ArrayList<>(nodes);
            copy.remove(node);
            calculate(copy, node, length + distances[node][last], minimum, distances);
        });
    }

    private static int bfs(Node start, Node target, int[][] room) {
        Queue<Node> queue = new LinkedList<>();
        Set<Node> visited = new HashSet<>();
        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            if (target.equals(current))
                return current.depth;

            addNode(new Node(current.x-1, current.y, current.depth+1), room, queue, visited);
            addNode(new Node(current.x+1, current.y, current.depth+1), room, queue, visited);
            addNode(new Node(current.x, current.y-1, current.depth+1), room, queue, visited);
            addNode(new Node(current.x, current.y+1, current.depth+1), room, queue, visited);
        }

        throw new IllegalStateException();
    }

    private static void addNode(Node next, int[][] room, Queue<Node> queue, Set<Node> visited) {
        if (room[next.y][next.x] == '.' && !visited.contains(next)) {
            visited.add(next);
            queue.add(next);
        }
    }

    private static class Node {
        private final int x;
        private final int y;
        private final int depth;

        private Node(int x, int y, int depth) {
            this.x = x;
            this.y = y;
            this.depth = depth;
        }

        @Override
        public int hashCode() {
            return x+y;
        }

        @Override
        public boolean equals(Object o) {
            Node other = (Node) o;
            return x == other.x && y == other.y;
        }
    }

}
