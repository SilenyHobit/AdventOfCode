package advent2018.day16;

import advent2018.day16.factories.*;
import util.InputLoader;
import vm.GeneralVM;
import vm.VMFunction;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {

    private static final Pattern beforePattern = Pattern.compile("Before: \\[([^]]+)]");
    private static final Pattern afterPattern = Pattern.compile("After:  \\[([^]]+)]");

    public static void main(String[] args) throws Exception {
        List<String> input = InputLoader.loadInput();
        List<Operation> operations = new ArrayList<>();

        int counter = 0;
        Matcher m = beforePattern.matcher(input.get(counter));
        while (m.find()) {
            String before = m.group(1).replace(",", "");
            String op = input.get(++counter);
            m = afterPattern.matcher(input.get(++counter));
            m.find();
            String after = m.group(1).replace(",", "");

            operations.add(new Operation(before, op, after));

            counter += 2;
            m = beforePattern.matcher(input.get(counter));
        }

        int result = 0;
        for (Operation op : operations)
            if (factories().stream().filter(fac -> fac.matches(op)).count() > 2L)
                result++;

        System.out.println(result);

        Map<String, List<FunctionFactory>> opcodes = operations.stream()
                .collect(Collectors.groupingBy(Operation::getOpcode))
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        entry -> factories().stream().filter(fac -> entry.getValue().stream().allMatch(fac::matches)).collect(Collectors.toList())));

        Map<String, FunctionFactory> resolved = new HashMap<>();

        while (resolved.size() != opcodes.size()) {
            opcodes.entrySet().stream()
                    .filter(entry -> entry.getValue().size() == 1)
                    .forEach(entry -> {
                        FunctionFactory factory = entry.getValue().get(0);
                        resolved.put(entry.getKey(), factory);
                        opcodes.entrySet().stream()
                                .filter(e -> !e.getValue().isEmpty())
                                .forEach(e -> e.getValue().remove(factory));
                    });
        }

        List<VMFunction> program = input.stream()
                .skip(counter)
                .filter(line -> !line.isEmpty())
                .map(line -> {
                    String[] splits = line.split(" ");
                    return resolved.get(splits[0]).create(line);
                })
                .collect(Collectors.toList());

        GeneralVM vm = new GeneralVM(program);
        vm.run();

        System.out.println(vm.get("0"));
    }

    private static List<FunctionFactory> factories() {
        return Arrays.asList(
                new AddIFactory(),
                new AddRFactory(),
                new BanIFactory(),
                new BanRFactory(),
                new BorIFactory(),
                new BorRFactory(),
                new EqIRFactory(),
                new EqRIFactory(),
                new EqRRFactory(),
                new GtRRFactory(),
                new GtIRFactory(),
                new GtRIFactory(),
                new MulIFactory(),
                new MulRFactory(),
                new SetIFactory(),
                new SetRFactory()
        );
    }

}
