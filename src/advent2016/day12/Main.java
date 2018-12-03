package advent2016.day12;

import util.InputLoader;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {

    private static final Pattern copy = Pattern.compile("cpy (-?[0-9a-z]*) ([a-z*])");
    private static final Pattern increment = Pattern.compile("inc ([a-z*])");
    private static final Pattern decrement = Pattern.compile("dec ([a-z*])");
    private static final Pattern jump = Pattern.compile("jnz (-?[0-9a-z]*) (-?[0-9*])");

    public static void main(String[] args) throws Exception {
        List<String> input = InputLoader.loadInput();

        List<Instruction> instructions = input.stream()
                .map(line -> {
                    Matcher m = copy.matcher(line);
                    if (m.find())
                        return new Copy(m.group(1), m.group(2));

                    m = increment.matcher(line);
                    if (m.find())
                        return new Increment(m.group(1));

                    m = decrement.matcher(line);
                    if (m.find())
                        return new Decrement(m.group(1));

                    m = jump.matcher(line);
                    if (m.find())
                        return new Jump(m.group(1), m.group(2));

                    throw new IllegalArgumentException();
                })
                .collect(Collectors.toList());

        VM vm = new VM(instructions);

        vm.run();

        System.out.println(vm.get("a"));

        vm = new VM(instructions);
        vm.set("c", 1);

        vm.run();

        System.out.println(vm.get("a"));
    }

}
