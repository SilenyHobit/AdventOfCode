package advent2017.day18;

import java.math.BigInteger;
import java.util.function.Function;

public class AddFunction implements VMFunction {

    private final String register;
    private final Function<VM, BigInteger> valueFunction;

    public AddFunction(String register, String value) {
        this.register = register;
        valueFunction = getValueFunction(value);
    }

    @Override
    public void perform(VM vm) {
        BigInteger value = vm.getRegister(register);
        vm.setRegister(register, value.add(valueFunction.apply(vm)));
    }
}
