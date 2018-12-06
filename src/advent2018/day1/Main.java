package advent2018.day1;

import util.InputLoader;
import util.Printer;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) throws Exception {
        Optional.of(InputLoader.loadInput().stream().map(Long::parseLong).collect(Collectors.toList()))
                .map(input -> Printer.print(part1(input), input))
                .map(input -> Printer.print(part2(input, new Device()), input))
                .orElseThrow(RuntimeException::new);
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
