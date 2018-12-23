package advent2018.day16.factories;

import advent2018.day16.Operation;
import advent2018.day16.functions.SetI;
import vm.VMFunction;

public class SetIFactory extends FunctionFactory {

    @Override
    public VMFunction create(String line) {
        String[] split = line.split(" ");
        return new SetI(Integer.parseInt(split[1]), split[3]);
    }

    @Override
    public boolean matches(Operation op) {
        return Integer.parseInt(op.getFirstOperand()) == op.getAfter().regiter(op.getThirdOperand());
    }
}
