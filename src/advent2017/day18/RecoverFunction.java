package advent2017.day18;

import java.math.BigInteger;
import java.util.function.Function;

public class RecoverFunction implements VMFunction {

    private final Function<VM, BigInteger> valueFunction;

    public RecoverFunction(String value) {
        valueFunction = getValueFunction(value);
    }

    @Override
    public void perform(VM vm) {
        if (valueFunction.apply(vm).longValue() != 0)
            vm.recover();
    }

}
