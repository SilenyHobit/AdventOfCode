package advent2018.day8;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class TreeBuilder {
    private Node root;
    private final List<Node> nodes = new ArrayList<>();
    private Node current;

    public TreeBuilder process(int i) {
        if (root == null) {
            root = new Node(null);
            root.nodesRemaining = i;
            current = root;
            nodes.add(current);
            return this;
        }

        if (current.metadataRemaining == -1) {
            current.metadataRemaining = i;
            return this;
        }

        if (current.nodesRemaining > 0) {
            current.nodesRemaining--;
            Node node = new Node(current);
            node.nodesRemaining = i;
            current.nodes.add(node);
            current = node;
            nodes.add(current);
            return this;
        }

        if (current.metadataRemaining > 0) {
            current.metadataRemaining--;
            current.metadata.add(i);
        }

        if (current.metadataRemaining == 0)
            current = current.parent;
        return this;
    }

    int sumMetadata() {
        return nodes.stream()
                .mapToInt(node -> node.metadata.stream().mapToInt(i -> i).sum())
                .sum();
    }

    int value() {
        return root.value();
    }

    Optional<TreeBuilder> asOptional() {
        return Optional.of(this);
    }

}
