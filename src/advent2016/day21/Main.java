package advent2016.day21;

import util.InputLoader;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private static final Pattern swapPositionPattern = Pattern.compile("swap position (\\d+) with position (\\d+)");
    private static final Pattern swapLetterPattern = Pattern.compile("swap letter (.) with letter (.)");
    private static final Pattern roateLeftPattern = Pattern.compile("rotate left (\\d+) step");
    private static final Pattern rotateRightPattern = Pattern.compile("rotate right (\\d+) step");
    private static final Pattern rotatePattern = Pattern.compile("rotate based on position of letter (.)");
    private static final Pattern reversePattern = Pattern.compile("reverse positions (\\d+) through (\\d+)");
    private static final Pattern movePattern = Pattern.compile("move position (\\d+) to position (\\d+)");

    public static void main(String[] args) throws Exception {
        List<String> input = InputLoader.loadInput();
        scramble(new Scrambler("abcdefgh".toCharArray(), false), input);

        Collections.reverse(input);

        scramble(new Scrambler("fbgdceah".toCharArray(), true), input);
    }

    private static void scramble(Scrambler scrambler, List<String> input) {
        input.forEach(line -> {
            performIfMatches(line, swapPositionPattern, m -> scrambler.swap(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2))));
            performIfMatches(line, swapLetterPattern, m -> scrambler.swap(m.group(1).charAt(0), m.group(2).charAt(0)));
            performIfMatches(line, roateLeftPattern, m -> scrambler.rotate(Integer.parseInt(m.group(1))));
            performIfMatches(line, rotateRightPattern, m -> scrambler.rotate(-Integer.parseInt(m.group(1))));
            performIfMatches(line, rotatePattern, m -> scrambler.rotate(m.group(1).charAt(0)));
            performIfMatches(line, reversePattern, m -> scrambler.reverse(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2))));
            performIfMatches(line, movePattern, m -> scrambler.move(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2))));
        });

        scrambler.print();
    }

    private static void performIfMatches(String line, Pattern p, Consumer<Matcher> c) {
        Matcher m = p.matcher(line);
        if (m.find())
            c.accept(m);
    }

    private static class Scrambler {

        private final char[] text;
        private final int[] rotationMapping;
        private final boolean invert;
        private int position;

        private Scrambler(char[] text, boolean invert) {
            this.text = text;
            this.rotationMapping = new int[text.length];
            this.invert = invert;

            for (int i = 0; i < text.length; i++) {
                int result = i;
                if (i > 3)
                    result++;
                result++;
                if (!invert)
                    rotationMapping[i] = result;
                else
                    rotationMapping[(i + result) % text.length] = result;
            }
        }

        void print() {
            for (int i = 0; i < text.length; i++)
                System.out.print(text[index(i)]);
            System.out.println();
        }

        void swap(int x, int y) {
            swapInternal(index(x), index(y));
        }

        private void swapInternal(int x, int y) {
            char tmp = text[x];
            text[x] = text[y];
            text[y] = tmp;
        }

        void swap(char x, char y) {
            int indexX = 0;
            int indexY = 0;
            for (int i = 0; i < text.length; i++) {
                if (text[i] == x)
                    indexX = i;
                if (text[i] == y)
                    indexY = i;
            }

            swapInternal(indexX, indexY);
        }

        void rotate(int x) {
            if (invert)
                x = -x;
            position += x;
            while (position < 0)
                position += text.length;

            position %= text.length;
        }

        void rotate(char x) {
            int indexX = 0;
            for (int i = 0; i < text.length; i++) {
                if (text[i] == x)
                    indexX = i;
            }

            if (indexX < position)
                indexX += text.length;

            int result = indexX - position;

            rotate(-(rotationMapping[result]));
        }

        void reverse(int x, int y) {
            int length = y - x + 1;
            int limit = length / 2;

            for (int i = 0; i < limit; i++) {
                swapInternal(index(i + x), index(y - i));
            }
        }

        void move(int x, int y) {
            if (x == y) {
                swap(x, y);
                return;
            }

            if (invert) {
                int tmp = x;
                x = y;
                y = tmp;
            }


            char tmp = text[index(x)];
            if (x > y)
                for (int i = x; i > y; i--)
                    text[index(i)] = text[index(i - 1)];
            else
                for (int i = x; i < y; i++)
                    text[index(i)] = text[index(i + 1)];

            text[index(y)] = tmp;
        }

        private int index(int index) {
            return (position + index) % text.length;
        }
    }

}
