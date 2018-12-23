package advent2018.day15;

import java.util.stream.Stream;

public class Node implements Comparable<Node> {

    private final int x;
    private final int y;
    private final Node root;
    private final int distance;

    public Node(int x, int y, Node root, int distance) {
        this.x = x;
        this.y = y;
        this.root = root;
        this.distance = distance;
    }

    public Stream<Node> neighbors() {
        return neighbors(root == null ? this : root);
    }

    public Stream<Node> neighbors(Node nextRoot) {
        return Stream.of(
                new Node(x + 1, y, nextRoot, distance + 1),
                new Node(x - 1, y, nextRoot, distance + 1),
                new Node(x, y + 1, nextRoot, distance + 1),
                new Node(x, y - 1, nextRoot, distance + 1)
        );
    }

    public Node getRoot() {
        return root == null ? this : root;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDistance() {
        return distance;
    }

    public boolean positionEquals(Node other) {
        if (x != other.x) return false;
        return y == other.y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        if (x != node.x) return false;
        return y == node.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    @Override
    public int compareTo(Node node) {
        int result = Integer.compare(distance, node.distance);
        return result != 0 ? result : Integer.compare(toComparisonInt(), node.toComparisonInt());
    }

    public int toComparisonInt() {
        int result = y*1000000;
        result += x*10000;
        result += getRoot().y*100;
        result += getRoot().getX();
        return result;
    }
}
