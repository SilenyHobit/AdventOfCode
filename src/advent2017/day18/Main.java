package advent2017.day18;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {

    private static final Pattern ADD_PATTERN = Pattern.compile("add ([a-z0-9]+) (-?[a-z0-9]+)");
    private static final Pattern JUMP_PATTERN = Pattern.compile("jgz ([a-z0-9]+) (-?[a-z0-9]+)");
    private static final Pattern MODULO_PATTERN = Pattern.compile("mod ([a-z0-9]+) (-?[a-z0-9]+)");
    private static final Pattern MULTIPLY_PATTERN = Pattern.compile("mul ([a-z0-9]+) (-?[a-z0-9]+)");
    private static final Pattern RECOVER_PATTERN = Pattern.compile("rcv ([a-z0-9]+)");
    private static final Pattern SET_PATTERN = Pattern.compile("set ([a-z0-9]+) (-?[a-z0-9]+)");
    private static final Pattern SOUND_PATTERN = Pattern.compile("snd ([a-z0-9]+)");

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("inputs/2017/day18"));
        List<VMFunction> program = parseProgram1(lines);

        VM vm = new VM(program, null);

        vm.run();

        System.out.println(vm.getLastRecovered());

        program = parseProgram2(lines);

        VM vm1 = new VM(program, null);
        vm1.setRegister("p", BigInteger.ONE);
        VM vm0 = new VM(program, vm1);

        vm0.run();

        System.out.println(vm1.getSendCount());
    }

    private static List<VMFunction> parseProgram1(List<String> program) {
        return program.stream()
                .map(line -> {
                    Optional<VMFunction> result = parseFunction(ADD_PATTERN, line, m -> new AddFunction(m.group(1), m.group(2)));
                    if (!result.isPresent()) result = parseFunction(JUMP_PATTERN, line, m -> new JumpFunction(m.group(1), m.group(2)));
                    if (!result.isPresent()) result = parseFunction(MODULO_PATTERN, line, m -> new ModuloFunction(m.group(1), m.group(2)));
                    if (!result.isPresent()) result = parseFunction(MULTIPLY_PATTERN, line, m -> new MultiplyFunction(m.group(1), m.group(2)));
                    if (!result.isPresent()) result = parseFunction(RECOVER_PATTERN, line, m -> new RecoverFunction(m.group(1)));
                    if (!result.isPresent()) result = parseFunction(SET_PATTERN, line, m -> new SetFunction(m.group(1), m.group(2)));
                    if (!result.isPresent()) result = parseFunction(SOUND_PATTERN, line, m -> new SoundFunction(m.group(1)));

                    return result.orElseThrow(() -> new IllegalArgumentException(line));
                })
                .collect(Collectors.toList());
    }

    private static List<VMFunction> parseProgram2(List<String> program) {
        return program.stream()
                .map(line -> {
                    Optional<VMFunction> result = parseFunction(ADD_PATTERN, line, m -> new AddFunction(m.group(1), m.group(2)));
                    if (!result.isPresent()) result = parseFunction(JUMP_PATTERN, line, m -> new JumpFunction(m.group(1), m.group(2)));
                    if (!result.isPresent()) result = parseFunction(MODULO_PATTERN, line, m -> new ModuloFunction(m.group(1), m.group(2)));
                    if (!result.isPresent()) result = parseFunction(MULTIPLY_PATTERN, line, m -> new MultiplyFunction(m.group(1), m.group(2)));
                    if (!result.isPresent()) result = parseFunction(RECOVER_PATTERN, line, m -> new ReceiveFunction(m.group(1)));
                    if (!result.isPresent()) result = parseFunction(SET_PATTERN, line, m -> new SetFunction(m.group(1), m.group(2)));
                    if (!result.isPresent()) result = parseFunction(SOUND_PATTERN, line, m -> new SendFunction(m.group(1)));

                    return result.orElseThrow(() -> new IllegalArgumentException(line));
                })
                .collect(Collectors.toList());
    }

    private static Optional<VMFunction> parseFunction(Pattern pattern, String line, Function<Matcher, VMFunction> function) {
        Matcher m = pattern.matcher(line);
        if (m.find())
            return Optional.of(function.apply(m));

        return Optional.empty();
    }
}
