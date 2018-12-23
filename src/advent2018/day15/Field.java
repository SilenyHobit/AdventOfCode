package advent2018.day15;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Field {

    private List<Fighter> fighters;
    private final int[][] matrix;
    private int completedRounds;

    public Field(List<Fighter> fighters, int[][] matrix) {
        this.fighters = fighters;
        this.matrix = matrix;
    }

    int result() {
        return fighters.stream().mapToInt(Fighter::getHp).sum() * completedRounds;
    }

    long countElves() {
        return fighters.stream().filter(Elf.class::isInstance).count();
    }

    void fight() {
        while (true) {

            for (Fighter fighter : fighters) {
                if (fighter.getHp() != 0) {
                    long goblins = fighters.stream().filter(Goblin.class::isInstance).filter(goblin -> goblin.getHp() != 0).count();
                    long elves = fighters.stream().filter(Elf.class::isInstance).filter(elf -> elf.getHp() != 0).count();
                    if (goblins == 0L || elves == 0L)
                        return;

                    move(fighter);
                    performAttack(fighter);
                }
            }

            fighters = fighters.stream()
                    .filter(fighter -> fighter.getHp() != 0)
                    .sorted()
                    .collect(Collectors.toList());

            completedRounds++;
        }
    }

    private void performAttack(Fighter fighter) {
        fighter.filterEnemies(fighters).stream()
                .filter(enemy -> enemy.getHp() != 0)
                .filter(enemy -> enemy.isNeighbor(fighter.getCurrent()))
                .min(new FighterHpComparator())
                .ifPresent(fighter::fight);
    }

    private Fighter move(Fighter fighter) {
        List<Fighter> enemies = fighter.filterEnemies(fighters);

        if (enemies.stream().filter(enemy -> enemy.getHp() != 0).anyMatch(enemy -> enemy.isNeighbor(fighter.getCurrent())))
            return fighter;

        Set<Node> visited = new HashSet<>();
        visited.add(fighter.getCurrent());

        PriorityQueue<Node> queue = fighter.getCurrent()
                .neighbors(null)
                .filter(n -> n.getX() > -1 && n.getX() < matrix[0].length && n.getY() > -1 && n.getY() < matrix.length)
                .filter(n -> matrix[n.getY()][n.getX()] == '.')
                .filter(n -> fighters.stream().filter(f -> f.getHp() != 0).map(Fighter::getCurrent).noneMatch(f -> f.positionEquals(n)))
                .filter(visited::add)
                .collect(Collectors.toCollection(PriorityQueue::new));

        List<Node> results = new ArrayList<>();
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            if (!results.isEmpty() && node.getDistance() > results.get(0).getDistance())
                break;

            if (enemies.stream().filter(enemy -> enemy.getHp() != 0).anyMatch(enemy -> enemy.isNeighbor(node))) {
                results.add(node);
            }

            node.neighbors()
                    .filter(n -> n.getX() > -1 && n.getX() < matrix[0].length && n.getY() > -1 && n.getY() < matrix.length)
                    .filter(n -> matrix[n.getY()][n.getX()] == '.')
                    .filter(n -> fighters.stream().filter(f -> f.getHp() != 0).map(Fighter::getCurrent).noneMatch(f -> f.positionEquals(n)))
                    .filter(visited::add)
                    .forEach(queue::add);
        }

        results.stream()
                .min(Comparator.comparingInt(Node::toComparisonInt))
                .map(Node::getRoot)
                .ifPresent(fighter::move);


        return fighter;
    }

}
