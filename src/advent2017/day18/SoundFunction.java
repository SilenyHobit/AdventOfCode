package advent2017.day18;

import java.math.BigInteger;
import java.util.function.Function;

public class SoundFunction implements VMFunction {

    private final Function<VM, BigInteger> valueFunction;

    public SoundFunction(String value) {
        valueFunction = getValueFunction(value);
    }

    @Override
    public void perform(VM vm) {
        vm.play(valueFunction.apply(vm).longValue());
    }
}
