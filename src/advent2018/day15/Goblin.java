package advent2018.day15;

import java.util.List;
import java.util.stream.Collectors;

public class Goblin extends Fighter {

    public Goblin(Node current) {
        super(current);
    }

    @Override
    public List<Fighter> filterEnemies(List<Fighter> fighters) {
        return fighters.stream().filter(Elf.class::isInstance).collect(Collectors.toList());
    }
}
