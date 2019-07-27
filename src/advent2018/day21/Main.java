package advent2018.day21;

import advent2018.day16.functions.*;
import advent2018.day19.EqRRCheck;
import advent2018.day19.NoPointerVM;
import util.Conversion;
import util.InputConverter;
import util.InputLoader;
import vm.VMFunction;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {

    private static final Pattern ipPattern = Pattern.compile("#ip (\\d+)");
    private static final Conversion<VMFunction> setiConversion = new Conversion<>(Pattern.compile("seti (\\d+) (\\d+) (\\d+)"), m -> new SetI(Integer.parseInt(m.group(1)), m.group(3)));
    private static final Conversion<VMFunction> setrConversion = new Conversion<>(Pattern.compile("setr (\\d+) (\\d+) (\\d+)"), m -> new SetR(m.group(1), m.group(3)));
    private static final Conversion<VMFunction> baniConversion = new Conversion<>(Pattern.compile("bani (\\d+) (\\d+) (\\d+)"), m -> new BanI(m.group(1), Integer.parseInt(m.group(2)), m.group(3)));
    private static final Conversion<VMFunction> eqriConversion = new Conversion<>(Pattern.compile("eqri (\\d+) (\\d+) (\\d+)"), m -> new EqRI(m.group(1), Integer.parseInt(m.group(2)), m.group(3)));
    private static final Conversion<VMFunction> addrConversion = new Conversion<>(Pattern.compile("addr (\\d+) (\\d+) (\\d+)"), m -> new AddR(m.group(1), m.group(2), m.group(3)));
    private static final Conversion<VMFunction> boriConversion = new Conversion<>(Pattern.compile("bori (\\d+) (\\d+) (\\d+)"), m -> new BorI(m.group(1), Integer.parseInt(m.group(2)), m.group(3)));
    private static final Conversion<VMFunction> muliConversion = new Conversion<>(Pattern.compile("muli (\\d+) (\\d+) (\\d+)"), m -> new MulI(m.group(1), Integer.parseInt(m.group(2)), m.group(3)));
    private static final Conversion<VMFunction> gtirConversion = new Conversion<>(Pattern.compile("gtir (\\d+) (\\d+) (\\d+)"), m -> new GtIR(Integer.parseInt(m.group(1)), m.group(2), m.group(3)));
    private static final Conversion<VMFunction> addiConversion = new Conversion<>(Pattern.compile("addi (\\d+) (\\d+) (\\d+)"), m -> new AddI(m.group(1), Integer.parseInt(m.group(2)), m.group(3)));
    private static final Conversion<VMFunction> gtrrConversion = new Conversion<>(Pattern.compile("gtrr (\\d+) (\\d+) (\\d+)"), m -> new GtRR(m.group(1), m.group(2), m.group(3)));
    private static final Conversion<VMFunction> eqrrConversion = new Conversion<>(Pattern.compile("eqrr (\\d+) (\\d+) (\\d+)"), m -> new EqRRCheck(m.group(1), m.group(2), m.group(3)));

    public static void main(String[] args) throws Exception {
        // first value of EqRR solves part1, last value before repeat solves part2 (comes as RuntimeException)
        InputConverter<VMFunction> converter = new InputConverter<>(Arrays.asList(addiConversion, addrConversion, setiConversion, setrConversion, baniConversion, muliConversion, eqriConversion, eqrrConversion, gtrrConversion, boriConversion, gtirConversion));
        List<String> input = InputLoader.loadInput();
        Matcher m = ipPattern.matcher(input.get(0));
        m.find();
        String pointer = m.group(1);

        List<VMFunction> program = input.stream()
                .skip(1L)
                .map(converter::convert)
                .collect(Collectors.toList());

        NoPointerVM vm = new NoPointerVM(program, pointer);
        vm.run();

    }

}
