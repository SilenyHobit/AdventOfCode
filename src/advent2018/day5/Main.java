package advent2018.day5;

import util.InputLoader;

import java.util.stream.IntStream;
import java.util.stream.Stream;

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
        return Stream.generate(reducer::generate)
                .flatMap(IntStream::boxed)
                .map(reducer::process)
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
        int position;

        public Reducer(String s) {
            newString = new StringBuilder().append(s);
        }

        IntStream generate() {
            replaced = false;
            skipNext = false;
            currentString = newString.toString().toCharArray();
            newString = new StringBuilder();
            return IntStream.range(0, currentString.length);
        }

        Reducer process(int index) {
            position = index;
            if (skipNext) {
                skipNext = false;
            } else {
                if (index != currentString.length-1) {
                    skipNext = Math.abs(currentString[index] - currentString[index + 1]) == 32;
                    replaced = skipNext || replaced;
                }
                if (!skipNext)
                    newString.append(currentString[index]);
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
