package advent2015.day21;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Main {

    private static final Character boss = new Character(109, 8, 2, 0);

    public static void main(String[] args) {
        List<Weapon> weapons = Arrays.asList(
                new Weapon(4,0,8),
                new Weapon(5,0,10),
                new Weapon(6,0,25),
                new Weapon(7,0,40),
                new Weapon(8,0,74)
        );
        List<Armor> armors = Arrays.asList(
                new Armor(0,0,0),
                new Armor(0,1,13),
                new Armor(0,2,31),
                new Armor(0,3,53),
                new Armor(0,4,75),
                new Armor(0,5,102)
        );
        List<Ring> rings = Arrays.asList(
                new Ring(0,0,0),
                new Ring(0,0,0),
                new Ring(1,0,25),
                new Ring(2,0,50),
                new Ring(3,0,100),
                new Ring(0,1,20),
                new Ring(0,2,40),
                new Ring(0,3,80)
        );

        List<Character> candidates = new ArrayList<>();
        for (Weapon weapon: weapons) {
            for (Armor armor: armors) {
                for (int i = 0; i < rings.size()-1; i++) {
                    for (int j = i+1; j < rings.size(); j++) {
                        int dmg = weapon.dmg + armor.dmg + rings.get(i).dmg + rings.get(j).dmg;
                        int ar = weapon.armor + armor.armor + rings.get(i).armor + rings.get(j).armor;
                        int cost = weapon.cost + armor.cost + rings.get(i).cost + rings.get(j).cost;
                        candidates.add(new Character(100, dmg, ar, cost));
                    }
                }
            }
        }

        Character cheapest = candidates.stream()
                .filter(hero -> test(hero, boss, true))
                .sorted(Comparator.comparingInt(hero -> hero.cost))
                .findFirst()
                .get();

        System.out.println(cheapest.cost);

        Character expensive = candidates.stream()
                .filter(hero -> test(hero, boss, false))
                .sorted(Comparator.<Character>comparingInt(hero -> hero.cost).reversed())
                .findFirst()
                .get();

        System.out.println(expensive.cost);
    }

    private static boolean test(Character hero, Character boss, boolean preferVictory) {
        int diff = Math.max(hero.dmg-boss.armor, 1);
        int heroTurns = turnsToKill(diff, boss.hp);
        diff = Math.max(boss.dmg-hero.armor, 1);
        int bossTurns = turnsToKill(diff, hero.hp);
        return bossTurns >= heroTurns == preferVictory;
    }

    private static int turnsToKill(int diff, int hp) {
        int res = hp/diff;
        if (hp%diff != 0)
            res++;
        return res;
    }

    private static class Character {
        private int hp;
        private int dmg;
        private int armor;
        private int cost;

        public Character(int hp, int dmg, int armor, int cost) {
            this.hp = hp;
            this.dmg = dmg;
            this.armor = armor;
            this.cost = cost;
        }
    }

    private abstract static class Item {
        final int dmg;
        final int armor;
        final int cost;

        protected Item(int dmg, int armor, int cost) {
            this.dmg = dmg;
            this.armor = armor;
            this.cost = cost;
        }
    }

    private static class Weapon extends Item {
        protected Weapon(int dmg, int armor, int cost) {
            super(dmg, armor, cost);
        }
    }

    private static class Armor extends Item {
        protected Armor(int dmg, int armor, int cost) {
            super(dmg, armor, cost);
        }
    }

    private static class Ring extends Item {
        protected Ring(int dmg, int armor, int cost) {
            super(dmg, armor, cost);
        }
    }
}
