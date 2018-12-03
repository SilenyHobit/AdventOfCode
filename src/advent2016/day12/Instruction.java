package advent2016.day12;

import java.util.function.Function;

public interface Instruction {

    void perform(VM vm);

    Instruction toggle();

    default Function<VM, Integer> getFunction(String str) {
        try {
            int val = Integer.parseInt(str);
            return vm -> val;
        } catch (Exception e) {
            return vm -> vm.get(str);
        }
    }

}
