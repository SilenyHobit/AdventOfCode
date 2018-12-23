package advent2018.day16.factories;

import advent2018.day16.Operation;
import advent2018.day16.factories.FunctionFactory;
import advent2018.day16.functions.MulR;
import vm.VMFunction;

public class MulRFactory extends FunctionFactory {

    @Override
    public VMFunction create(String line) {
        String[] split = line.split(" ");
        return new MulR(split[1], split[2], split[3]);
    }

    @Override
    public boolean matches(Operation op) {
        return op.getBefore().regiter(op.getFirstOperand()) * op.getBefore().regiter(op.getSecondOperand()) == op.getAfter().regiter(op.getThirdOperand());
    }

}
