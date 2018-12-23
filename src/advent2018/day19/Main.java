package advent2018.day19;

import advent2018.day16.functions.*;
import util.Conversion;
import util.InputConverter;
import util.InputLoader;
import vm.GeneralVM;
import vm.VMFunction;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {

    private static final Pattern ipPattern = Pattern.compile("#ip (\\d+)");
    private static final Conversion<VMFunction> addiConversion = new Conversion<>(Pattern.compile("addi (\\d+) (\\d+) (\\d+)"), m -> new AddI(m.group(1), Integer.parseInt(m.group(2)), m.group(3)));
    private static final Conversion<VMFunction> setiConversion = new Conversion<>(Pattern.compile("seti (\\d+) (\\d+) (\\d+)"), m -> new SetI(Integer.parseInt(m.group(1)), m.group(3)));
    private static final Conversion<VMFunction> mulrConversion = new Conversion<>(Pattern.compile("mulr (\\d+) (\\d+) (\\d+)"), m -> new MulR(m.group(1), m.group(2), m.group(3)));
    private static final Conversion<VMFunction> eqrrConversion = new Conversion<>(Pattern.compile("eqrr (\\d+) (\\d+) (\\d+)"), m -> new EqRR(m.group(1), m.group(2), m.group(3)));
    private static final Conversion<VMFunction> addrConversion = new Conversion<>(Pattern.compile("addr (\\d+) (\\d+) (\\d+)"), m -> new AddR(m.group(1), m.group(2), m.group(3)));
    private static final Conversion<VMFunction> gtrrConversion = new Conversion<>(Pattern.compile("gtrr (\\d+) (\\d+) (\\d+)"), m -> new GtRR(m.group(1), m.group(2), m.group(3)));
    private static final Conversion<VMFunction> muliConversion = new Conversion<>(Pattern.compile("muli (\\d+) (\\d+) (\\d+)"), m -> new MulI(m.group(1), Integer.parseInt(m.group(2)), m.group(3)));
    private static final Conversion<VMFunction> setrConversion = new Conversion<>(Pattern.compile("setr (\\d+) (\\d+) (\\d+)"), m -> new SetR(m.group(1), m.group(3)));

    public static void main(String[] args) throws Exception {
        InputConverter<VMFunction> converter = new InputConverter<>(Arrays.asList(addiConversion, addrConversion, setiConversion, setrConversion, muliConversion, mulrConversion, eqrrConversion, gtrrConversion));
        List<String> input = InputLoader.loadInput();
        Matcher m = ipPattern.matcher(input.get(0));
        m.find();
        String pointer = m.group(1);

        List<VMFunction> program = input.stream()
                .skip(1L)
                .map(converter::convert)
                .collect(Collectors.toList());

        GeneralVM vm = new NoPointerVM(program, pointer);
        vm.run();
        System.out.println(vm.get("0"));

        //program is counting sum of divisors of given number. Number for the first part is 887.
        int target = 10551287;
        int sum = 0;

        for (int i = 1; i < target+1; i++)
            if (target % i == 0)
                sum += i;

        System.out.println(sum);
    }

}
