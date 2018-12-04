package advent2018.day4;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DutyCombiner {

    private int[] minutes = new int[60];
    private String id;

    DutyCombiner addDuty(GuardDuty duty) {
        id = duty.guardId();
        minutes = IntStream.range(0, 60)
                .boxed()
                .reduce(minutes, (array, i) -> change(array, i, duty.timeFrame().minutes()[i]), (a,b) -> a);
        return this;
    }

    private int[] change(int[] array, int minute, int change) {
        array[minute] += change;
        return array;
    }

    int result() {
        return Integer.parseInt(id) * maxIndex();
    }

    private int maxIndex() {
        return Optional.of(Arrays.stream(minutes).max())
                .filter(OptionalInt::isPresent)
                .map(OptionalInt::getAsInt)
                .map(value -> Arrays.stream(minutes).boxed().collect(Collectors.toList()).indexOf(value))
                .orElseThrow(IllegalArgumentException::new);
    }

    int maxValue() {
        return Arrays.stream(minutes)
                .max()
                .orElseThrow(IllegalArgumentException::new);
    }
}
