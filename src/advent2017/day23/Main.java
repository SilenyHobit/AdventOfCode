package advent2017.day23;

import advent2017.day18.*;
import util.InputLoader;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {

    private static final Pattern SUB_PATTERN = Pattern.compile("sub ([a-z0-9]+) (-?[a-z0-9]+)");
    private static final Pattern JUMP_PATTERN = Pattern.compile("jnz ([a-z0-9]+) (-?[a-z0-9]+)");
    private static final Pattern MULTIPLY_PATTERN = Pattern.compile("mul ([a-z0-9]+) (-?[a-z0-9]+)");
    private static final Pattern SET_PATTERN = Pattern.compile("set ([a-z0-9]+) (-?[a-z0-9]+)");

    public static void main(String[] args) throws Exception {
        List<String> input = InputLoader.loadInput();
        List<VMFunction> program = parseProgram(input);

        VM2 vm = new VM2(program, null);
        vm.run();

        System.out.println(vm.mutiplyCount());

        // optimized algorithm. Counts non-primes in interval that are increments of 17
        int counter = 0;
        for (int i = 106700; i <= 123700; i+=17)
            if(!isPrime(i))
                counter++;

        System.out.println(counter);
    }

    private static boolean isPrime(int n) {
        //check if n is a multiple of 2
        if (n%2==0) return false;
        //if not, then just check the odds
        for(int i=3;i*i<=n;i+=2) {
            if(n%i==0)
                return false;
        }
        return true;
    }

    private static List<VMFunction> parseProgram(List<String> program) {
        return program.stream()
                .map(line -> {
                    Optional<VMFunction> result = parseFunction(SUB_PATTERN, line, m -> new SubFunction(m.group(1), m.group(2)));
                    if (!result.isPresent()) result = parseFunction(JUMP_PATTERN, line, m -> new JumpNZFunction(m.group(1), m.group(2)));
                    if (!result.isPresent()) result = parseFunction(MULTIPLY_PATTERN, line, m -> new Multiply2Function(m.group(1), m.group(2)));
                    if (!result.isPresent()) result = parseFunction(SET_PATTERN, line, m -> new SetFunction(m.group(1), m.group(2)));
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
