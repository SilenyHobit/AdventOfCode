package advent2017.day23;

import advent2017.day18.VM;
import advent2017.day18.VMFunction;

import java.math.BigInteger;
import java.util.function.Function;

public class SubFunction implements VMFunction {

    private final String register;
    private final Function<VM, BigInteger> valueFunction;

    public SubFunction(String register, String value) {
        this.register = register;
        valueFunction = getValueFunction(value);
    }

    @Override
    public void perform(VM vm) {
        BigInteger value = vm.getRegister(register);
        vm.setRegister(register, value.subtract(valueFunction.apply(vm)));
    }

}
