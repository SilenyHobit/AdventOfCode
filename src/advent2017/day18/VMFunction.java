package advent2017.day18;

import java.math.BigInteger;
import java.util.function.Function;

public interface VMFunction {

    void perform(VM vm);

    default Function<VM, BigInteger> getValueFunction(String value) {
        Function<VM, BigInteger> function;
        try {
            BigInteger val = BigInteger.valueOf(Long.parseLong(value));
            function = vm -> val;
        } catch (NumberFormatException e) {
            function = vm -> vm.getRegister(value);
        }

        return function;
    }

}
