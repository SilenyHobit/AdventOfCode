package advent2017.day10;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StringHash {

    private static final int[] SUFFIX = {17, 31, 73, 47, 23};

    private int[] array = IntStream.range(0, 256).toArray();
    private int skip = 0;
    private int position = 0;

    private final int[] distances;

    public StringHash(int[] distances) {
        this.distances = distances;
    }

    public StringHash(String key) {
        this.distances = Stream.concat(key.chars().boxed(), Arrays.stream(SUFFIX).boxed()).mapToInt(i -> i).toArray();
    }

    public void performHash() {
        performHash(64);
    }

    void performHash(int rounds) {
        while (rounds-- != 0)
            Arrays.stream(distances).forEach(this::perform);
    }

    private void perform(int distance) {
        int fixedDistance = fixIndex(distance);
        int limit = fixedDistance/2;
        int lastIndex = position + fixedDistance - 1;

        for (int i = 0; i < limit; i++) {
            int tmp = array[fixIndex(position+i)];
            array[fixIndex(position+i)] = array[fixIndex(lastIndex-i)];
            array[fixIndex(lastIndex-i)] = tmp;
        }

        position = fixIndex(position + fixedDistance + skip);
        skip += 1;
    }

    private int fixIndex(int i) {
        return i % array.length;
    }

    public int task1() {
        return array[0]*array[1];
    }

    public String hash() {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < 16; i++) {
            int num = array[i*16];
            for (int j = 1; j < 16; j++) {
                num = num ^ array[(i*16) + j];
            }
            builder.append(String.format("%02X", num));
        }

        return builder.toString();
    }
}
