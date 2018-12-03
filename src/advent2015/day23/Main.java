package advent2015.day23;

import util.InputLoader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {

    private static final Pattern halfPattern = Pattern.compile("hlf ([ab])");
    private static final Pattern triplePattern = Pattern.compile("tpl ([ab])");
    private static final Pattern incrementPattern = Pattern.compile("inc ([ab])");
    private static final Pattern jumpPattern = Pattern.compile("jmp ([+-]\\d+)");
    private static final Pattern jiePattern = Pattern.compile("jie ([ab]), ([+-]\\d+)");
    private static final Pattern jioPattern = Pattern.compile("jio ([ab]), ([+-]\\d+)");

    public static void main(String[] args) throws Exception {
        List<String> input = InputLoader.loadInput();

        List<Step> steps = input.stream()
                .map(line -> {
                    Matcher m = halfPattern.matcher(line);
                    if (m.find())
                        return new Half(m.group(1));
                    m = triplePattern.matcher(line);
                    if (m.find())
                        return new Triple(m.group(1));
                    m = incrementPattern.matcher(line);
                    if (m.find())
                        return new Increment(m.group(1));
                    m = jumpPattern.matcher(line);
                    if (m.find())
                        return new Jump(Integer.parseInt(m.group(1)));
                    m = jiePattern.matcher(line);
                    if (m.find())
                        return new JumpIfEven(m.group(1), Integer.parseInt(m.group(2)));
                    m = jioPattern.matcher(line);
                    if (m.find())
                        return new JumpIfOdd(m.group(1), Integer.parseInt(m.group(2)));

                    throw new IllegalArgumentException();
                })
                .collect(Collectors.toList());

        VM vm = new VM(steps);
        vm.run();
        System.out.println(vm.get("b"));

        vm = new VM(steps);
        vm.set("a", 1);
        vm.run();
        System.out.println(vm.get("b"));

    }

    private static class VM {

        final Map<String, AtomicInteger> registers = new HashMap<>();
        final List<Step> program;

        int position;

        private VM(List<Step> program) {
            this.program = program;
        }

        void run() {
            while(position > -1 && position < program.size())
                program.get(position).perform(this);
        }

        int get(String register) {
            return getRegister(register).get();
        }

        void set(String register, int value) {
            getRegister(register).set(value);
        }

        void increment(String register) {
            getRegister(register).incrementAndGet();
        }

        private AtomicInteger getRegister(String register) {
            return registers.computeIfAbsent(register, s -> new AtomicInteger(0));
        }

        void jump(int value) {
            position += value;
        }

    }

    private interface Step {
        void perform(VM vm);
    }

    private static class Half implements Step {

        final String register;

        private Half(String register) {
            this.register = register;
        }

        @Override
        public void perform(VM vm) {
            int value = vm.get(register);
            vm.set(register, value/2);
            vm.jump(1);
        }
    }

    private static class Triple implements Step {

        final String register;

        private Triple(String register) {
            this.register = register;
        }

        @Override
        public void perform(VM vm) {
            int value = vm.get(register);
            vm.set(register, value*3);
            vm.jump(1);
        }
    }

    private static class Increment implements Step {

        final String register;

        private Increment(String register) {
            this.register = register;
        }

        @Override
        public void perform(VM vm) {
            vm.increment(register);
            vm.jump(1);
        }
    }

    private static class Jump implements Step {

        final int value;

        private Jump(int value) {
            this.value = value;
        }

        @Override
        public void perform(VM vm) {
            vm.jump(value);
        }
    }

    private static class JumpIfEven implements Step {

        final String register;
        final int value;

        private JumpIfEven(String register, int value) {
            this.register = register;
            this.value = value;
        }

        @Override
        public void perform(VM vm) {
            int reg = vm.get(register);
            if (reg%2 == 0)
                vm.jump(value);
            else
                vm.jump(1);
        }
    }

    private static class JumpIfOdd implements Step {

        final String register;
        final int value;

        private JumpIfOdd(String register, int value) {
            this.register = register;
            this.value = value;
        }

        @Override
        public void perform(VM vm) {
            if (vm.get(register) == 1)
                vm.jump(value);
            else
                vm.jump(1);
        }
    }

}
