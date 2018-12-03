package advent2016.day23;

import advent2016.day12.Instruction;
import advent2016.day12.VM;

import java.util.List;

public class VM2 extends VM {

    public VM2(List<Instruction> program) {
        super(program);
    }

    void toggle(int value) {
        int index = pointer+value;
        if (index > 0 && index < program.size()) {
            program.add(index, program.remove(index).toggle());
        }
    }

}
