package advent2016.day1;

import util.InputLoader;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {



    public static void main(String[] args) throws Exception {
        List<String> input = InputLoader.loadInput();

        State state = new State();

        Arrays.stream(input.get(0).split(","))
                .map(String::trim)
                .forEach(str -> {
                    char c = str.charAt(0);
                    switch (c) {
                        case 'L':
                            state.moveLeft(Integer.parseInt(str.substring(1)));
                            break;
                        case 'R':
                            state.moveRight(Integer.parseInt(str.substring(1)));
                            break;
                        default:
                            throw new IllegalArgumentException();
                    }
                });

        System.out.println(state.distance());
        System.out.println(state.firstRepeatDistance());
    }

    private static class State {
        private int x = 0;
        private int y = 0;
        private Facing facing = Facing.N;

        private Set<Location> visited = new HashSet<>();
        private Location firstRepeat;

        public State() {
            visited.add(new Location(0,0));
        }

        int distance() {
            return Math.abs(x) + Math.abs(y);
        }

        int firstRepeatDistance() {
            return Math.abs(firstRepeat.x) + Math.abs(firstRepeat.y);
        }

        void moveLeft(int steps) {
            facing = facing.left();
            move(steps);
        }

        void moveRight(int steps) {
            facing = facing.right();
            move(steps);
        }

        void move(int steps) {
            while(steps-- != 0) {
                switch (facing) {
                    case N:
                        y++;
                        checkLocation();
                        break;
                    case S:
                        y--;
                        checkLocation();
                        break;
                    case E:
                        x++;
                        checkLocation();
                        break;
                    case W:
                        x--;
                        checkLocation();
                        break;
                }
            }
        }

        void checkLocation() {
            Location newLocation = new Location(x,y);
            if (visited.contains(newLocation) && firstRepeat == null)
                firstRepeat = newLocation;

            visited.add(newLocation);
        }

    }

    private static class Location {
        private final int x;
        private final int y;

        private Location(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int hashCode() {
            return x+y;
        }

        @Override
        public boolean equals(Object o) {
            Location other = (Location) o;
            return x == other.x && y == other.y;
        }
    }

    private enum Facing {
        E, W, N, S;

        Facing left() {
            switch (this) {
                case E:
                    return N;
                case N:
                    return W;
                case W:
                    return S;
                case S:
                    return E;
            }

            throw new IllegalArgumentException();
        }

        Facing right() {
            switch (this) {
                case E:
                    return S;
                case N:
                    return E;
                case W:
                    return N;
                case S:
                    return W;
            }

            throw new IllegalArgumentException();
        }
    }

}
