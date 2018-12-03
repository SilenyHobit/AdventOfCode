package advent2017.day23;

import advent2017.day18.VM;
import advent2017.day18.VMFunction;

import java.math.BigInteger;
import java.util.function.Function;

public class JumpNZFunction implements VMFunction {

    private final Function<VM, BigInteger> registerFunction;
    private final Function<VM, BigInteger> valueFunction;

    public JumpNZFunction(String register, String value) {
        registerFunction = getValueFunction(register);
        valueFunction = getValueFunction(value);
    }

    @Override
    public void perform(VM vm) {
        BigInteger value = registerFunction.apply(vm);
        if (value.compareTo(BigInteger.ZERO) != 0)
            vm.jump(valueFunction.apply(vm).longValue());
    }

}
