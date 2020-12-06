package advent2020.day6;

import java.util.Arrays;

public class Group {

    private final int[] answers = new int[26];
    private int members;

    void addLine(String line) {
        line.chars()
                .forEach(c -> answers[c - 'a'] += 1);
        members++;
    }

    long sum() {
        return Arrays.stream(answers).filter(i -> i != 0).count();
    }

    long sum2() {
        return Arrays.stream(answers).filter(i -> i == members).count();
    }

}
