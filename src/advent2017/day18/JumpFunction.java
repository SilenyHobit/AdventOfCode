package advent2017.day18;

import java.math.BigInteger;
import java.util.function.Function;

public class JumpFunction implements VMFunction {

    private final Function<VM, BigInteger> registerFunction;
    private final Function<VM, BigInteger> valueFunction;

    public JumpFunction(String register, String value) {
        registerFunction = getValueFunction(register);
        valueFunction = getValueFunction(value);
    }

    @Override
    public void perform(VM vm) {
        BigInteger value = registerFunction.apply(vm);
        if (value.compareTo(BigInteger.ZERO) > 0)
            vm.jump(valueFunction.apply(vm).longValue());
    }

}
