package advent2018.day1;

import util.InputLoader;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) throws Exception {
        List<Long> input = InputLoader.loadInput().stream().map(Long::parseLong).collect(Collectors.toList());

        System.out.println(part1(input));
        System.out.println(part2(input, new Device()));
    }

    private static long part1(List<Long> input) {
        return input.stream().reduce(0L, (a, b) -> a + b);
    }

    private static long part2(List<Long> input, Device device) {
        return Stream.generate(input::stream).flatMap(Function.identity())
                .map(device::changeFrequency)
                .filter(Device::isRepeat)
                .map(Device::frequency)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    private static class Device {

        private final Set<Long> history;
        private final AtomicLong frequency;

        public Device() {
            this.history = new HashSet<>();
            this.frequency = new AtomicLong(0L);
        }

        Device changeFrequency(long change) {
            history.add(frequency.get());
            frequency.addAndGet(change);
            return this;
        }

        boolean isRepeat() {
            return history.contains(frequency.get());
        }

        long frequency() {
            return frequency.get();
        }
    }

}
