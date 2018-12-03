package advent2016.day12;

import java.util.function.Function;

public class Jump implements Instruction {

    private final Function<VM, Integer> condition;
    private final Function<VM, Integer> valueFnc;
    private final String value;

    public Jump(String condition, String value) {
        this.condition = getFunction(condition);
        this.valueFnc = getFunction(value);
        this.value = value;
    }

    public Jump(Function<VM, Integer> condition, String value) {
        this.condition = condition;
        this.value = value;
        this.valueFnc = getFunction(value);
    }

    @Override
    public void perform(VM vm) {
        if (condition.apply(vm) != 0)
            vm.jump(valueFnc.apply(vm));
        else
            vm.jump(1);
    }

    @Override
    public Instruction toggle() {
        return new Copy(condition, value);
    }
}
