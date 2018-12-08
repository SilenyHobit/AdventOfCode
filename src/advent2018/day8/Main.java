package advent2018.day8;

import util.InputLoader;
import util.Printer;

import java.util.Arrays;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) throws Exception {
        Stream.of(InputLoader.loadInput().get(0))
                .flatMap(s -> Arrays.stream(s.split(" ")))
                .map(Integer::parseInt)
                .reduce(new TreeBuilder(), TreeBuilder::process, (a, b) -> a)
                .asOptional()
                .map(builder -> Printer.print(builder.sumMetadata(), builder))
                .map(builder -> Printer.print(builder.value(), builder));
    }

}
