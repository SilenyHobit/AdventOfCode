package advent2017.day10;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws IOException {
        String line = Files.readAllLines(Paths.get("inputs/2017/day10")).get(0).trim();
        int[] distances = Arrays.stream(line.split(","))
                .mapToInt(Integer::parseInt)
                .toArray();
        StringHash stringHash = new StringHash(distances);

        stringHash.performHash(1);

        System.out.println(stringHash.task1());

        stringHash = new StringHash(line);
        stringHash.performHash();

        System.out.println(stringHash.hash());
    }

}
