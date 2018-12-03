package advent2016.day20;

import util.InputLoader;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {

    private static final Pattern rangePattern = Pattern.compile("(\\d+)-(\\d+)");

    public static void main(String[] args) throws Exception {
        List<String> input = InputLoader.loadInput();

        List<Range> ranges = input.stream()
                .map(line -> {
                    Matcher m = rangePattern.matcher(line);
                    m.find();
                    return new Range(Long.parseLong(m.group(1)), Long.parseLong(m.group(2)));
                })
                .sorted(new RangeComparator())
                .collect(Collectors.toList());

        List<Range> merged = new ArrayList<>();
        Range latest = ranges.get(0);

        for (int i = 1; i < ranges.size(); i++) {
            Range next = ranges.get(i);
            if (next.lower <= latest.upper+1 && next.upper > latest.upper)
                latest = new Range(latest.lower, next.upper);
            else if (next.lower > latest.upper+1) {
                merged.add(latest);
                latest = next;
            }
        }

        merged.add(latest);


        System.out.println(merged.get(0).upper+1);
        System.out.println(4294967296L - merged.stream().mapToLong(range -> range.upper-range.lower+1).sum());
    }

    private static class Range {
        private final long lower;
        private final long upper;

        private Range(long lower, long upper) {
            this.lower = lower;
            this.upper = upper;
        }
    }

    private static class RangeComparator implements Comparator<Range> {

        @Override
        public int compare(Range range, Range t1) {
            int res = Long.compare(range.lower, t1.lower);
            if (res == 0)
                res = Long.compare(range.upper, t1.upper);
            return res;
        }
    }

}
