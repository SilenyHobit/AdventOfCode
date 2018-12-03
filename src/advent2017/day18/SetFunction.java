package advent2017.day18;

import java.math.BigInteger;
import java.util.function.Function;

public class SetFunction implements VMFunction {

    private final String register;
    private final Function<VM, BigInteger> valueFunction;

    public SetFunction(String register, String value) {
        this.register = register;
        valueFunction = getValueFunction(value);
    }

    @Override
    public void perform(VM vm) {
        BigInteger value = valueFunction.apply(vm);
        vm.setRegister(register, value);
    }
}
