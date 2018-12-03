package advent2017.day18;

import java.math.BigInteger;
import java.util.function.Function;

public class ModuloFunction implements VMFunction {

    private final String register;
    private final Function<VM, BigInteger> valueFunction;

    public ModuloFunction(String register, String value) {
        this.register = register;
        valueFunction = getValueFunction(value);
    }

    @Override
    public void perform(VM vm) {
        BigInteger value = vm.getRegister(register);
        vm.setRegister(register, value.remainder(valueFunction.apply(vm)));
    }

}
