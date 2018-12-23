package advent2018.day15;

import java.util.List;
import java.util.stream.Collectors;

public class Elf extends Fighter {

    public Elf(Node current, int ap) {
        super(current);
        this.ap = ap;
    }

    @Override
    public List<Fighter> filterEnemies(List<Fighter> fighters) {
        return fighters.stream().filter(Goblin.class::isInstance).collect(Collectors.toList());
    }
}
