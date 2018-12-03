package advent2016.day23;

import advent2016.day12.*;
import util.InputLoader;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {

    private static final Pattern copy = Pattern.compile("cpy (-?[0-9a-z]+) ([a-z]+)");
    private static final Pattern increment = Pattern.compile("inc ([a-z]+)");
    private static final Pattern decrement = Pattern.compile("dec ([a-z]+)");
    private static final Pattern jump = Pattern.compile("jnz (-?[0-9a-z]+) (-?[0-9a-zA-Z]+)");
    private static final Pattern tgl = Pattern.compile("tgl (-?[a-zA-Z0-9]+)");

    public static void main(String[] args) throws Exception {
        List<Instruction> input = InputLoader.loadInput().stream()
                .map(line -> {
                    Optional<Instruction> result = tryMatch(line, copy, m -> new Copy(m.group(1), m.group(2)));
                    if (!result.isPresent())
                        result = tryMatch(line, increment, m -> new Increment(m.group(1)));
                    if (!result.isPresent())
                        result = tryMatch(line, decrement, m -> new Decrement(m.group(1)));
                    if (!result.isPresent())
                        result = tryMatch(line, jump, m -> new Jump(m.group(1), m.group(2)));
                    if (!result.isPresent())
                        result = tryMatch(line, tgl, m -> new Toggle(m.group(1)));
                    return result.orElseThrow(IllegalArgumentException::new);
                })
                .collect(Collectors.toList());

        VM2 vm = new VM2(input);
        vm.set("a", 12);
        vm.run();
        System.out.println(vm.get("a"));
    }

    private static Optional<Instruction> tryMatch(String line, Pattern pattern, Function<Matcher, Instruction> creator) {
        Matcher m = pattern.matcher(line);
        if (m.find())
            return Optional.of(creator.apply(m));

        return Optional.empty();
    }

}
