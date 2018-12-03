package advent2017.day18;

import java.math.BigInteger;
import java.util.function.Function;

public class MultiplyFunction implements VMFunction {

    private final String register;
    private final Function<VM, BigInteger> valueFunction;

    public MultiplyFunction(String register, String value) {
        this.register = register;
        valueFunction = getValueFunction(value);
    }

    @Override
    public void perform(VM vm) {
        BigInteger value = vm.getRegister(register);
        vm.setRegister(register, value.multiply(valueFunction.apply(vm)));
    }

}
