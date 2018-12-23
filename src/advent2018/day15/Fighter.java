package advent2018.day15;

import java.util.List;

public abstract class Fighter implements Comparable<Fighter> {

    private Node current;
    private int hp = 200;
    protected int ap = 3;

    public Fighter(Node current) {
        this.current = current;
    }

    public int getHp() {
        return hp;
    }

    public void fight(Fighter enemy) {
        enemy.hp = Math.max(enemy.hp - ap, 0);
    }

    public void move(Node target) {
        this.current = target;
    }

    public Node getCurrent() {
        return current;
    }

    public boolean isNeighbor(Node node) {
        int diffX = Math.abs(current.getX() - node.getX());
        int diffY = Math.abs(current.getY() - node.getY());
        return diffX + diffY == 1;
    }

    public abstract List<Fighter> filterEnemies(List<Fighter> fighters);

    @Override
    public int compareTo(Fighter fighter) {
        int result = Integer.compare(current.getY(), fighter.current.getY());
        return result != 0 ? result : Integer.compare(current.getX(), fighter.current.getX());
    }
}
