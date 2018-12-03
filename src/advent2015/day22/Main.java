package advent2015.day22;

import java.util.*;
import java.util.stream.Collectors;

public class Main {

    private static final Boss startingBoss = new Boss(55, 8);
    private static final Hero startingHero = new Hero(50, 500);

    public static void main(String[] args) {
        System.out.println(perform(false).consumedMana);
        System.out.println(perform(true).consumedMana);
    }

    private static State perform(boolean hard) {
        State initialState = new State(startingHero, startingBoss, 0, new ArrayList<>(), new ArrayList<>(), hard);
        if (hard)
            initialState.hero.hp--;
        List<Action> actions = Arrays.asList(new MagicMissile(), new Drain(), new Shield(), new Poison(), new Recharge());
        State result = null;

        PriorityQueue<State> queue = new PriorityQueue<>();
        queue.add(initialState);

        while (!queue.isEmpty()) {
            State state = queue.poll();

            if (state.bossDead) {
                result = state;
                break;
            }

            actions.stream()
                    .filter(action -> action.isAllowed(state))
                    .map(action -> {
                        State child = state.copy();
                        child.performAction(action);
                        return child.evaluate();
                    })
                    .filter(st -> !st.heroDead)
                    .forEach(queue::add);
        }
        return result;
    }


    private static class Boss {
        int hp;
        int dmg;

        private Boss(int hp, int dmg) {
            this.hp = hp;
            this.dmg = dmg;
        }
    }

    private static class Hero {
        int hp;
        int armor;
        int mana;

        private Hero(int hp, int mana) {
            this.hp = hp;
            this.mana = mana;
        }
    }

    private static class State implements Comparable<State> {
        private Hero hero;
        private Boss boss;
        private int consumedMana;

        List<Action> history;
        List<Effect> effects;

        final boolean hard;
        boolean heroDead = false;
        boolean bossDead = false;

        public State(Hero hero, Boss boss, int consumedMana, List<Effect> effects, List<Action> history, boolean hard) {
            this.hero = hero;
            this.boss = boss;
            this.consumedMana = consumedMana;
            this.effects = effects;
            this.history = history;
            this.hard = hard;
        }

        State copy() {
            return new State(new Hero(hero.hp, hero.mana), new Boss(boss.hp, boss.dmg), consumedMana, new ArrayList<>(effects), new ArrayList<>(history), hard);
        }

        void hitBoss(int dmg) {
            boss.hp -= dmg;
            if (boss.hp <= 0)
                bossDead = true;
        }

        void performAction(Action action) {
            history.add(action);
            action.perform(this);
        }

        void performEffects() {
            effects = effects.stream()
                    .map(effect -> effect.tick(this))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());
        }

        void hitHero() {
            hero.hp -= 8 - hero.armor;
            if (hard)
                hero.hp--;
            if (hero.hp <= 0)
                heroDead = true;
        }

        State evaluate() {
            if (bossDead)
                return this;
            performEffects();
            if (bossDead)
                return this;
            hitHero();
            if (heroDead)
                return this;
            performEffects();
            return this;
        }

        @Override
        public int compareTo(State state) {
            return Integer.compare(consumedMana, state.consumedMana);
        }
    }

    private abstract static class Effect {

        final int timer;

        protected Effect(int timer) {
            this.timer = timer;
        }

        abstract Optional<Effect> tick(State state);
    }

    private interface Action {
        boolean isAllowed(State state);

        void perform(State state);
    }

    private static class MagicMissile implements Action {

        @Override
        public boolean isAllowed(State state) {
            return state.hero.mana >= 53;
        }

        @Override
        public void perform(State state) {
            state.hero.mana -= 53;
            state.consumedMana += 53;
            state.hitBoss(4);
        }
    }

    private static class Drain implements Action {

        @Override
        public boolean isAllowed(State state) {
            return state.hero.mana >= 73;
        }

        @Override
        public void perform(State state) {
            state.hero.mana -= 73;
            state.consumedMana += 73;
            state.hero.hp += 2;
            state.hitBoss(2);
        }
    }

    private static class ShieldEffect extends Effect {

        private ShieldEffect(int timer) {
            super(timer);
        }

        @Override
        public Optional<Effect> tick(State state) {
            if (timer != 0) {
                state.hero.armor = 7;
                return Optional.of(new ShieldEffect(timer - 1));
            } else {
                state.hero.armor = 0;
                return Optional.empty();
            }
        }

        @Override
        public int hashCode() {
            return getClass().hashCode();
        }

        @Override
        public boolean equals(Object o) {
            return this.getClass().equals(o.getClass());
        }
    }

    private static class Shield implements Action {

        private static final ShieldEffect template = new ShieldEffect(0);

        @Override
        public boolean isAllowed(State state) {
            return state.hero.mana >= 113 && (state.effects.indexOf(template) == -1 || state.effects.get(state.effects.indexOf(template)).timer == 0);
        }

        @Override
        public void perform(State state) {
            state.hero.mana -= 113;
            state.consumedMana += 113;
            state.effects.add(new ShieldEffect(6));
        }
    }

    private static class PoisonEffect extends Effect {

        public PoisonEffect(int timer) {
            super(timer);
        }

        @Override
        public Optional<Effect> tick(State state) {
            if (timer != 0) {
                state.hitBoss(3);
                return Optional.of(new PoisonEffect(timer - 1));
            } else {
                return Optional.empty();
            }
        }

        @Override
        public int hashCode() {
            return getClass().hashCode();
        }

        @Override
        public boolean equals(Object o) {
            return this.getClass().equals(o.getClass());
        }
    }

    private static class Poison implements Action {

        private static final PoisonEffect template = new PoisonEffect(0);

        @Override
        public boolean isAllowed(State state) {
            return state.hero.mana >= 173 && (state.effects.indexOf(template) == -1 || state.effects.get(state.effects.indexOf(template)).timer == 0);
        }

        @Override
        public void perform(State state) {
            state.hero.mana -= 173;
            state.consumedMana += 173;
            state.effects.add(new PoisonEffect(6));
        }
    }

    private static class RechargeEffect extends Effect {

        public RechargeEffect(int timer) {
            super(timer);
        }

        @Override
        public Optional<Effect> tick(State state) {
            if (timer != 0) {
                state.hero.mana += 101;
                return Optional.of(new RechargeEffect(timer - 1));
            } else {
                return Optional.empty();
            }
        }

        @Override
        public int hashCode() {
            return getClass().hashCode();
        }

        @Override
        public boolean equals(Object o) {
            return this.getClass().equals(o.getClass());
        }
    }

    private static class Recharge implements Action {

        private static final RechargeEffect template = new RechargeEffect(0);

        @Override
        public boolean isAllowed(State state) {
            return state.hero.mana >= 229 && (state.effects.indexOf(template) == -1 || state.effects.get(state.effects.indexOf(template)).timer == 0);
        }

        @Override
        public void perform(State state) {
            state.hero.mana -= 229;
            state.consumedMana += 229;
            state.effects.add(new RechargeEffect(5));
        }
    }

}
