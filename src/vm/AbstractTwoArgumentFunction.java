package vm;

import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Function;

public abstract class AbstractTwoArgumentFunction implements VMFunction {

    private final String register;
    private final Function<GeneralVM, Long> valueFunction;
    private final BinaryOperator<Long> resultFunction;

    public AbstractTwoArgumentFunction(String register, String value, BinaryOperator<Long> resultFunction) {
        this.register = register;
        this.resultFunction = resultFunction;
        Function<GeneralVM, Long> valueFunction;
        try {
            long v = Long.parseLong(value);
            valueFunction = vm -> v;
        } catch (Exception e) {
            valueFunction = vm -> vm.get(value);
        }
        this.valueFunction = valueFunction;
    }

    @Override
    public int perform(GeneralVM vm) {
        return Optional.of(vm)
                .map(valueFunction)
                .map(value -> resultFunction.apply(vm.get(register), value))
                .map(value -> vm.set(register, value))
                .map(value -> vm.jump(1))
                .orElseThrow(RuntimeException::new);
    }
}
