package vm;

import java.util.Optional;
import java.util.function.Function;

public class ConditionalJumpFunction implements VMFunction {

    private final Function<GeneralVM, Long> valueFunction;
    private final Function<Long, Boolean> conditionFunction;
    private final int jumpValue;

    public ConditionalJumpFunction(String value, String jumpValue, Function<Long, Boolean> conditionFunction) {
        this.conditionFunction = conditionFunction;
        this.jumpValue = Integer.parseInt(jumpValue);
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
                .map(value -> conditionFunction.apply(value) ? jumpValue : 1)
                .map(vm::jump)
                .orElseThrow(RuntimeException::new);
    }
}
