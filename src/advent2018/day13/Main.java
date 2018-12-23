package advent2018.day13;

import util.InputLoader;
import util.Pair;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class Main {

    private static final Set<Character> carSymbols = new HashSet<>(Arrays.asList('v', '>', '<', '^'));

    public static void main(String[] args) throws Exception {
        List<String> input = InputLoader.loadInput();

        int[][] rails = new int[150][150];
        List<Car> cars1 = new ArrayList<>();
        List<Car> cars2 = new ArrayList<>();

        for (int i = 0; i < input.size(); i++) {
            String line = input.get(i);
            for (int j = 0; j < line.length(); j++) {
                rails[i][j] = line.charAt(j);
                if (carSymbols.contains(line.charAt(j))) {
                    cars1.add(new Car(j, i, line.charAt(j)));
                    cars2.add(new Car(j, i, line.charAt(j)));
                }
            }
        }

        part1(rails, cars1);
        part2(rails, cars2);
    }

    private static void part1(int[][] rails, List<Car> cars) {
        Pair<Integer, Integer> crash = null;
        while (crash == null) {
            Collections.sort(cars);
            for (Car car : cars) {
                car.move((char) rails[car.y][car.x]);
                long samePositions = cars.stream().filter(car::equals).count();
                if (samePositions > 1L) {
                    crash = new Pair<>(car.x, car.y);
                    break;
                }
            }
        }

        System.out.println(crash);
    }

    private static void part2(int[][] rails, List<Car> cars) {
        AtomicBoolean lastStanding = new AtomicBoolean(false);
        while (!lastStanding.get()) {
            Collections.sort(cars);
            for (Car car : cars) {
                if (car.x != -1) {
                    car.move((char) rails[car.y][car.x]);
                    List<Car> crashed = cars.stream().filter(car::equals).collect(Collectors.toList());
                    if (crashed.size() > 1) {
                        crashed.forEach(Car::crash);
                    }
                }
            }
            cars = cars.stream().filter(car -> car.x != -1).collect(Collectors.toList());
            lastStanding.set(cars.size() == 1);
        }

        Pair<Integer, Integer> result = cars.stream().map(car -> new Pair<>(car.x, car.y)).findFirst().orElseThrow(IllegalArgumentException::new);
        System.out.println(result);
    }

    private static class Car implements Comparable<Car> {
        int x;
        int y;
        char direction;
        Turn nextTurn = Turn.LEFT;

        enum Turn {
            LEFT, STRAIGHT, RIGHT;

            static Turn nextTurn(Turn current) {
                switch (current) {
                    case LEFT:
                        return STRAIGHT;
                    case STRAIGHT:
                        return RIGHT;
                    case RIGHT:
                        return LEFT;
                    default:
                        throw new IllegalArgumentException();
                }
            }
        }

        public Car(int x, int y, char direction) {
            this.x = x;
            this.y = y;
            this.direction = direction;
        }

        void crash() {
            x = -1;
            y = -1;
        }

        void move(char current) {
            changeDirection(current);
            switch (direction) {
                case '^':
                    y--;
                    break;
                case 'v':
                    y++;
                    break;
                case '>':
                    x++;
                    break;
                case '<':
                    x--;
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        }

        void changeDirection(char current) {
            switch (current) {
                case '/':
                    switch (direction) {
                        case '>':
                            direction = '^';
                            break;
                        case '<':
                            direction = 'v';
                            break;
                        case 'v':
                            direction = '<';
                            break;
                        case '^':
                            direction = '>';
                    }
                    break;
                case '\\':
                    switch (direction) {
                        case '>':
                            direction = 'v';
                            break;
                        case '<':
                            direction = '^';
                            break;
                        case 'v':
                            direction = '>';
                            break;
                        case '^':
                            direction = '<';
                    }
                    break;
                case '+':
                    if (nextTurn == Turn.LEFT)
                        direction = turnLeft();
                    if (nextTurn == Turn.RIGHT)
                        direction = turnRight();
                    nextTurn = Turn.nextTurn(nextTurn);
            }
        }

        char turnLeft() {
            switch (direction) {
                case '^':
                    return '<';
                case 'v':
                    return '>';
                case '<':
                    return 'v';
                case '>':
                    return '^';
                default:
                    throw new IllegalArgumentException();
            }
        }

        char turnRight() {
            switch (direction) {
                case '^':
                    return '>';
                case 'v':
                    return '<';
                case '<':
                    return '^';
                case '>':
                    return 'v';
                default:
                    throw new IllegalArgumentException();
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Car car = (Car) o;

            if (x != car.x) return false;
            return y == car.y;
        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            return result;
        }

        @Override
        public int compareTo(Car car) {
            int result = Integer.compare(y, car.y);

            return result != 0 ? result : Integer.compare(x, car.x);
        }
    }

}
