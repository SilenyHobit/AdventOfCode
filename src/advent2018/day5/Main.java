package advent2018.day5;

import util.InputLoader;

import java.util.Optional;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) throws Exception {
        String input = InputLoader.loadInput().get(0);

        System.out.println(part1(input));
        System.out.println(part2(input));
    }

    private static int part2(String input) {
        return IntStream.range('a', 'z'+1)
                .map(i -> part1(replace(input, i)))
                .min()
                .orElseThrow(IllegalArgumentException::new);
    }

    private static String replace(String s, int c) {
        return s.replace(Character.toString((char)c), "").replace(Character.toString((char)(c - 32)), "");
    }

    private static int part1(String input) {
        return Optional.of(new Reducer(input))
                .map(Reducer::reduce)
                .map(Reducer::result)
                .orElseThrow(IllegalArgumentException::new);
    }

    private static class Reducer {
        private StringBuilder newString;

        public Reducer(String s) {
            newString = new StringBuilder().append(s);
        }

        Reducer reduce() {
            int length = newString.length()-1;
            IntStream.range(1, length)
                    .map(i -> length-i)
                    .filter(i -> i <newString.length())
                    .filter(i -> Math.abs(newString.substring(i-1,i+1).charAt(0) - newString.substring(i-1,i+1).charAt(1)) == 32)
                    .mapToObj(i -> newString.delete(i-1, i+1))
                    .count();
            return this;
        }

        int result() {
            return newString.length();
        }
    }
}
