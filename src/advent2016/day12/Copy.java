package advent2016.day12;

import java.util.function.Function;

public class Copy implements Instruction {

    private final Function<VM, Integer> value;
    private final String target;
    private final boolean valid;

    public Copy(String value, String target) {
        this.value = getFunction(value);
        this.target = target;
        valid = true;
    }

    public Copy(Function<VM, Integer> value, String target) {
        this.value = value;
        this.target = target;
        boolean valid;
        try {
            Integer.parseInt(target);
            valid = false;
        } catch (Exception e) {
            valid = true;
        }
        this.valid = valid;
    }

    @Override
    public void perform(VM vm) {
        if (valid)
            vm.set(target, value.apply(vm));
        vm.jump(1);
    }

    @Override
    public Instruction toggle() {
        return new Jump(value, target);
    }
}
