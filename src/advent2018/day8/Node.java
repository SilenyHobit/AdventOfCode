package advent2018.day8;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class Node {

    final Node parent;
    final List<Integer> metadata;
    final List<Node> nodes;
    int nodesRemaining;
    int metadataRemaining = -1;

    Node(Node parent) {
        this.parent = parent;
        this.metadata = new ArrayList<>();
        this.nodes = new ArrayList<>();
    }

    int value() {
        return metadata.stream()
                .mapToInt(i -> nodes.isEmpty() ? i : childValue(i-1))
                .sum();
    }

    private int childValue(int index) {
        return Optional.of(index)
                .filter(i -> i < nodes.size())
                .map(nodes::get)
                .map(Node::value)
                .orElse(0);
    }
}
