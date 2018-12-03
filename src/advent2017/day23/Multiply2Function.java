package advent2017.day23;

import advent2017.day18.MultiplyFunction;
import advent2017.day18.VM;

public class Multiply2Function extends MultiplyFunction {

    public Multiply2Function(String register, String value) {
        super(register, value);
    }

    @Override
    public void perform(VM vm) {
        super.perform(vm);
        ((VM2) vm).multiplied();
    }
}
