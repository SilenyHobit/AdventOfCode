package advent2018.day16.factories;

import advent2018.day16.Operation;
import advent2018.day16.functions.SetR;
import vm.VMFunction;

public class SetRFactory extends FunctionFactory {

    @Override
    public VMFunction create(String line) {
        String[] split = line.split(" ");
        return new SetR(split[1], split[3]);
    }

    @Override
    public boolean matches(Operation op) {
        return op.getBefore().regiter(op.getFirstOperand()) == op.getAfter().regiter(op.getThirdOperand());
    }
}
