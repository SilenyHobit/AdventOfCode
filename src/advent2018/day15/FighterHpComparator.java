package advent2018.day15;

import java.util.Comparator;

public class FighterHpComparator implements Comparator<Fighter> {

    @Override
    public int compare(Fighter fighter, Fighter t1) {
        int result = Integer.compare(fighter.getHp(), t1.getHp());
        return result != 0 ? result : fighter.compareTo(t1);
    }

}
