package advent2017.day23;

import advent2017.day18.VM;
import advent2017.day18.VMFunction;

import java.util.List;

public class VM2 extends VM {

    private int multiplicationCounter = 0;

    public VM2(List<VMFunction> program, VM other) {
        super(program, other);
    }

    public int mutiplyCount() {
        return multiplicationCounter;
    }

    public void multiplied() {
        multiplicationCounter++;
    }

}
