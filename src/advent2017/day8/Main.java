package advent2017.day8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private static final Pattern PATTERN = Pattern.compile("([a-zA-Z]+) (inc|dec) (-?[0-9]+) if ([a-zA-Z]+) ([<>=!]+) (-?[0-9]+)");

    private static long maxValue = 0L;

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("inputs/2017/day8"));
        Map<String, AtomicLong> registers = new HashMap<>();

        lines.forEach(line -> {
            Matcher m = PATTERN.matcher(line);
            m.find();
            String register = m.group(1);
            String operation = m.group(2);
            String operationNumber = m.group(3);
            String conditionRegister = m.group(4);
            String condition = m.group(5);
            String conditionNumber = m.group(6);

            AtomicLong val = registerValue(register, registers);

            if (checkCondition(conditionRegister, condition, conditionNumber, registers))
                perform(val, operation, operationNumber);
        });

        long max = registers.values().stream()
                .mapToLong(AtomicLong::get)
                .max()
                .getAsLong();

        System.out.println(max);
        System.out.println(maxValue);
    }

    private static boolean checkCondition(String conditionRegister, String condition, String conditionNumber, Map<String, AtomicLong> registers) {
        AtomicLong value = registerValue(conditionRegister, registers);
        long conditionValue = Long.parseLong(conditionNumber);
        switch (condition) {
            case "<":
                return value.get() < conditionValue;
            case ">":
                return value.get() > conditionValue;
            case "==":
                return value.get() == conditionValue;
            case "!=":
                return value.get() != conditionValue;
            case ">=":
                return value.get() >= conditionValue;
            case "<=":
                return value.get() <= conditionValue;
        }

        throw new IllegalArgumentException();
    }

    private static void perform(AtomicLong value, String operation, String number) {
        long val;
        switch (operation) {
            case "inc":
                val = value.addAndGet(Long.parseLong(number));
                if (val > maxValue)
                    maxValue = val;
                break;
            case "dec":
                val = value.addAndGet(-1L * Long.parseLong(number));
                if (val > maxValue)
                    maxValue = val;
                break;
        }
    }

    private static AtomicLong registerValue(String register, Map<String, AtomicLong> registers) {
        return registers.computeIfAbsent(register, s -> new AtomicLong(0L));
    }

}
