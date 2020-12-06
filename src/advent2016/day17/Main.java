package advent2016.day17;

import util.InputLoader;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.LinkedList;
import java.util.Queue;

public class Main {

    private static final Node target = new Node("", "", 3, 3);

    private static final Queue<Node> queue = new LinkedList<>();

    public static void main(String[] args) throws Exception {
        String input = InputLoader.loadInput().get(0);

        System.out.println(walkShort(input));
        System.out.println(walkLong(input));
    }

    private static String walkShort(String input) throws Exception {
        Node current = new Node(input, hash(input), 0, 0);

        while (target.x != current.x || target.y != current.y) {
            addNodes(current);
            current = queue.poll();
        }

        return current.path.substring(input.length());
    }

    private static int walkLong(String input) throws Exception {
        Node current = new Node(input, hash(input), 0, 0);
        int length = input.length();

        do {
            if (target.x == current.x && target.y == current.y) {
                if (current.path.length() > length)
                    length = current.path.length();
            } else {
                addNodes(current);
            }
            current = queue.poll();
        } while (!queue.isEmpty());

        return length - input.length();
    }

    private static String hash(String original) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("MD5");
        byte[] result = digest.digest(original.getBytes());
        BigInteger bigInteger = new BigInteger(1, result);
        String hashed = String.format(
                "%0" + (result.length << 1) + "x", bigInteger).substring(0, 4);
        return hashed.replaceAll("[0123456789A]", "0").replaceAll("[BCDEF]", "1");
    }

    private static void addNodes(Node parent) throws Exception {
        if (canWalk(parent.x, parent.y - 1, parent.hash.charAt(0)))
            addNode(parent.path + "U", parent.x, parent.y - 1);
        if (canWalk(parent.x, parent.y + 1, parent.hash.charAt(1)))
            addNode(parent.path + "D", parent.x, parent.y + 1);
        if (canWalk(parent.x - 1, parent.y, parent.hash.charAt(2)))
            addNode(parent.path + "L", parent.x - 1, parent.y);
        if (canWalk(parent.x + 1, parent.y, parent.hash.charAt(3)))
            addNode(parent.path + "R", parent.x + 1, parent.y);
    }

    private static void addNode(String path, int x, int y) throws Exception {
        Node n = new Node(path, hash(path), x, y);
        queue.add(n);
    }

    private static boolean canWalk(int x, int y, char c) {
        return x >= 0 && x < 4 && y >= 0 && y < 4 && c == '1';

    }

    private static class Node {

        private final String path;
        private final String hash;
        private final int x;
        private final int y;

        private Node(String path, String hash, int x, int y) {
            this.path = path;
            this.hash = hash;
            this.x = x;
            this.y = y;
        }

        @Override
        public int hashCode() {
            return hash.hashCode() + x + y + path.length();
        }

        @Override
        public boolean equals(Object o) {
            Node other = (Node) o;
            return x == other.x && y == other.y && hash.equals(other.hash) && path.length() == other.path.length();
        }


    }

}
