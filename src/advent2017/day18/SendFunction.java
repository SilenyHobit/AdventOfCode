package advent2017.day18;

import java.math.BigInteger;
import java.util.function.Function;

public class SendFunction implements VMFunction {

    private final Function<VM, BigInteger> valueFunction;

    public SendFunction(String value) {
        valueFunction = getValueFunction(value);
    }

    @Override
    public void perform(VM vm) {
        vm.send(valueFunction.apply(vm));
    }
}
