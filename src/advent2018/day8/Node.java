package advent2018.day8;

import java.util.ArrayList;
import java.util.List;

class Node {

    final Node parent;
    final List<Integer> metadata;
    final List<Node> nodes;
    int nodesRemaining;
    int metadataRemaining = -1;

    public Node(Node parent) {
        this.parent = parent;
        this.metadata = new ArrayList<>();
        this.nodes = new ArrayList<>();
    }

    int value() {
        if (nodes.isEmpty())
            return metadata.stream().mapToInt(i -> i).sum();

        return metadata.stream()
                .map(i -> i-1)
                .filter(i -> i < nodes.size())
                .map(nodes::get)
                .mapToInt(Node::value)
                .sum();
    }
}
