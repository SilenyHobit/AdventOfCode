package advent2017.day7;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private static final Pattern node = Pattern.compile("(\\w+) \\((\\d+)\\)");
    private static final Pattern children = Pattern.compile("->(.*)");

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("inputs/2017/day7"));
        Map<String, Node> tree = new HashMap<>();

        for (String line : lines) {
            Matcher nodeMatcher = node.matcher(line);
            Node node;

            if (nodeMatcher.find()) {
                String name = nodeMatcher.group(1);
                node = tree.get(name);
                if (node == null) {
                    node = new Node(name, tree);
                    tree.put(name, node);
                }
                node.value = Integer.parseInt(nodeMatcher.group(2));
            } else {
                System.out.println("Error parsing line " + line);
                break;
            }

            Matcher childrenMatcher = children.matcher(line);

            if (childrenMatcher.find()) {
                String[] children = childrenMatcher.group(1).split(",");
                List<String> childrenList = new ArrayList<>();

                for (String child : children) {
                    child = child.trim();
                    Node childNode = tree.get(child);
                    if (!tree.containsKey(child)) {
                        childNode = new Node(child, tree);
                        tree.put(child, childNode);
                    }
                    childNode.parent = node.name;
                    childrenList.add(child);
                }

                node.children = childrenList;
            }
        }

        Node root = (Node) tree.values().toArray()[0];

        while (root.getParent() != null) {
            root = root.getParent();
        }

        System.out.println("First: " + root.name);

        root.getSum();
        String result = root.findBalanceProblem();
        Node parent = tree.get(tree.get(result).parent);
        parent.children.forEach(child -> System.out.println(tree.get(child).name + " " + tree.get(child).sum));
        System.out.println(tree.get(result).name + " " + tree.get(result).value);

    }

    private static class Node {

        private final String name;
        private final Map<String, Node> map;

        private int value;
        private String parent;
        private List<String> children;

        private Integer sum;

        private Node(String name, Map<String, Node> map) {
            this.name = name;
            this.map = map;
        }

        Node getParent() {
            if (parent == null) {
                return null;
            }

            return map.get(parent);
        }

        int getSum() {
            if (children == null) {
                sum = value;
                return value;
            }

            if (sum == null) {
                sum = value;
                for (String child : children) {
                    Node node = map.get(child);
                    sum += node.getSum();
                }
            }

            return sum;
        }

        String findBalanceProblem() {
            if (children == null)
                return null;

            for (String child : children) {
                Node node = map.get(child);
                boolean exception = true;
                for (String child2 : children) {
                    Node node2 = map.get(child2);
                    if (node == node2)
                        continue;
                    if (node.sum.equals(node2.sum)) {
                        exception = false;
                        break;
                    }
                }
                if (exception && children.size() == 2) {
                    String res1 = map.get(child).findBalanceProblem();
                    if (child.equals(res1)) {
                        for (String child2 : children) {
                            if (!child.equals(child2)) {
                                String res2 = map.get(child2).findBalanceProblem();
                                if (res2.equals(child2))
                                    return name;
                                else
                                    return res2;
                            }
                        }
                    }
                } else if (exception) {
                    return map.get(child).findBalanceProblem();
                }

            }

            return name;
        }
    }
}
