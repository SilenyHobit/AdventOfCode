package advent2016.day12;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VM {

    protected final Map<String, Integer> registers = new HashMap<>();
    protected final List<Instruction> program;

    protected int pointer = 0;

    public VM(List<Instruction> program) {
        this.program = program;
    }

    public void run() {
        while (0 <= pointer && pointer < program.size()) {
            program.get(pointer).perform(this);
        }
    }

    public int get(String register) {
        return registers.computeIfAbsent(register, r -> 0);
    }

    public void jump(int diff) {
        pointer += diff;
    }

    public void set(String register, int value) {
        registers.put(register, value);
    }
}
