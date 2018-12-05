package advent2018.day5;

import util.InputLoader;

import java.util.Optional;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) throws Exception {
        String input = InputLoader.loadInput().get(0);

        System.out.println(part1(new Reducer(input)));
        System.out.println(part2(input));
    }

    private static int part2(String input) {
        return IntStream.range('a', 'z'+1)
                .map(i -> part1(new Reducer(replace(input, i))))
                .min()
                .orElseThrow(IllegalArgumentException::new);
    }

    private static String replace(String s, int c) {
        return s.replace(Character.toString((char)c), "").replace(Character.toString((char)(c - 32)), "");
    }

    private static int part1(Reducer reducer) {
        return IntStream.iterate(0, i -> i+1)
                .mapToObj(reducer::process)
                .filter(Reducer::isFinished)
                .findFirst()
                .map(Reducer::result)
                .orElseThrow(IllegalArgumentException::new);
    }

    private static class Reducer {
        private char[] currentString;
        private StringBuilder newString;
        boolean replaced;
        boolean skipNext;
        int sum;
        int position;

        public Reducer(String s) {
            newString = new StringBuilder();
            currentString = s.toCharArray();
        }

        Reducer process(int index) {
            if (index-sum == currentString.length) {
                sum += currentString.length;
                currentString = newString.toString().toCharArray();
                newString = new StringBuilder();
                replaced = false;
            }

            position = index-sum;
            if (skipNext) {
                skipNext = false;
            } else {
                skipNext = position != currentString.length-1 && Math.abs(currentString[position] - currentString[position+1]) == 32;
                replaced = skipNext || replaced;
                newString.append(skipNext ? "" : Character.toString(currentString[position]));
            }
            return this;
        }

        boolean isFinished() {
            return position == currentString.length - 1 && !replaced;
        }

        int result() {
            return currentString.length;
        }

    }

}
