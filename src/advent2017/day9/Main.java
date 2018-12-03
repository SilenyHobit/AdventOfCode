package advent2017.day9;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) throws IOException {
        String line = Files.readAllLines(Paths.get("inputs/2017/day9")).get(0);
        Parser parser = new Parser();

        for (char c : line.toCharArray())
            parser.process(c);

        System.out.println(parser.getValue());
        System.out.println(parser.getGarbage());
    }
}
