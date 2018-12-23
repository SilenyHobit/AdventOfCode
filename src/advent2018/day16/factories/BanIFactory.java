package advent2018.day16.factories;

import advent2018.day16.Operation;
import advent2018.day16.functions.BanI;
import vm.VMFunction;

public class BanIFactory extends FunctionFactory {

    @Override
    public VMFunction create(String line) {
        String[] split = line.split(" ");
        return new BanI(split[1], Integer.parseInt(split[2]), split[3]);
    }

    @Override
    public boolean matches(Operation op) {
        return (op.getBefore().regiter(op.getFirstOperand()) & Integer.parseInt(op.getSecondOperand())) == op.getAfter().regiter(op.getThirdOperand());
    }
}
