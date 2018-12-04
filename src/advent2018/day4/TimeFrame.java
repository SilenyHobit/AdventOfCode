package advent2018.day4;

import java.util.Arrays;

public class TimeFrame {
    private final int[] minutes = new int[60];
    private int position = 0;

    int position() {
        return position;
    }

    int length() {
        return minutes.length;
    }

    int current() {
        return minutes[position];
    }

    TimeFrame map(int index, int lastIndex, int trueValue, int falseValue) {
        if (index > minutes.length)
            return this;
        minutes[index] = index == lastIndex ? trueValue : falseValue;
        position = index;
        return this;
    }

    int sum() {
        return Arrays.stream(minutes).sum();
    }

    int[] minutes() {
        return minutes;
    }

}
