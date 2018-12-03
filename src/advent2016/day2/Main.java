package advent2016.day2;

import util.InputLoader;

import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        List<String> input = InputLoader.loadInput();

        StringBuilder firstCode = new StringBuilder();
        StringBuilder secondCode = new StringBuilder();

        input.forEach(line -> {
            Keypad standardKeypad = Keypad.standard();
            Keypad diamondKeypad = Keypad.diamond();

            line.chars()
                    .mapToObj(i -> (char) i)
                    .forEach(c -> {
                        standardKeypad.move(c);
                        diamondKeypad.move(c);
                    });

            firstCode.append(standardKeypad.digit());
            secondCode.append(diamondKeypad.digit());
        });

        System.out.println(firstCode.toString());
        System.out.println(secondCode.toString());
    }

    private static class Keypad {
        private final char[][] pad;

        private int x;
        private int y;

        private Keypad(int x, int y, char[][] pad) {
            this.pad = pad;
            this.x = x;
            this.y = y;
        }

        static Keypad standard() {
            char[][] pad = {
                    {'N','N','N','N','N'},
                    {'N','1','2','3','N'},
                    {'N','4','5','6','N'},
                    {'N','7','8','9','N'},
                    {'N','N','N','N','N'}
            };
            return new Keypad(2, 2, pad);
        }

        static Keypad diamond() {
            char[][] pad = {
                    {'N','N','N','N','N','N','N'},
                    {'N','N','N','1','N','N','N'},
                    {'N','N','2','3','4','N','N'},
                    {'N','5','6','7','8','9','N'},
                    {'N','N','A','B','C','N','N'},
                    {'N','N','N','D','N','N','N'},
                    {'N','N','N','N','N','N','N'}
            };
            return new Keypad(1, 3, pad);
        }

        void move(char c) {
            switch (c) {
                case 'L':
                    left();
                    break;
                case 'R':
                    right();
                    break;
                case 'U':
                    up();
                    break;
                case 'D':
                    down();
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        }

        void left() {
            int tmp = x-1;
            if (pad[y][tmp] != 'N')
                x = tmp;
        }

        void right() {
            int tmp = x+1;
            if (pad[y][tmp] != 'N')
                x = tmp;
        }

        void up() {
            int tmp = y-1;
            if (pad[tmp][x] != 'N')
                y = tmp;
        }

        void down() {
            int tmp = y+1;
            if (pad[tmp][x] != 'N')
                y = tmp;
        }

        char digit() {
            return pad[y][x];
        }
    }

}
