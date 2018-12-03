package advent2016.day22;

import util.InputLoader;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {

    private static final Pattern pattern = Pattern.compile("/dev/grid/node-x(\\d+)-y(\\d+)\\s*(\\d+)T\\s*(\\d+)T\\s*(\\d+)");

    public static void main(String[] args) throws Exception {
        List<Node> nodes = InputLoader.loadInput()
                .stream()
                .skip(2L)
                .map(line -> {
                    Matcher m = pattern.matcher(line);
                    m.find();
                    return new Node(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)), Integer.parseInt(m.group(4)), Integer.parseInt(m.group(5)));
                })
                .collect(Collectors.toList());

        Set<Node> viableNodes = new HashSet<>();
        Node empty = null;
        for (int i = 0; i < nodes.size(); i++) {
            for (int j = i + 1; j < nodes.size(); j++) {
                if (nodes.get(i).used != 0 && nodes.get(i).used <= nodes.get(j).free) {
                    viableNodes.add(nodes.get(i));
                }
                if (nodes.get(j).used != 0 && nodes.get(j).used <= nodes.get(i).free) {
                    viableNodes.add(nodes.get(j));
                }
                if (nodes.get(j).used == 0) {
                    empty = nodes.get(j);
                }
            }
        }

        System.out.println(viableNodes.size());

        char[][] nodeArray = new char[30][33];
        nodes.forEach(node -> {
            if (viableNodes.contains(node))
                nodeArray[node.y][node.x] = '.';
            else
                nodeArray[node.y][node.x] = '#';
        });

        nodeArray[0][0] = 'T';
        nodeArray[empty.y][empty.x] = '_';
        nodeArray[0][32] = 'G';

        Arrays.stream(nodeArray)
                .forEach(array -> System.out.println(new String(array)));
        // 69 + 156 = 225
    }

    private static class Node {
        private final int x;
        private final int y;
        private final int used;
        private final int free;

        private Node(int x, int y, int used, int free) {
            this.x = x;
            this.y = y;
            this.used = used;
            this.free = free;
        }
    }

}
