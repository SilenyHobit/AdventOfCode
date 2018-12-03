package advent2016.day23;

import advent2016.day12.Increment;
import advent2016.day12.Instruction;
import advent2016.day12.VM;

import java.util.function.Function;

public class Toggle implements Instruction {

    private final String original;
    private final Function<VM, Integer> value;

    public Toggle(String value) {
        this.value = getFunction(value);
        this.original = value;
    }

    @Override
    public void perform(VM vm) {
        ((VM2) vm).toggle(value.apply(vm));
        vm.jump(1);
    }

    @Override
    public Instruction toggle() {
        return new Increment(original);
    }
}
