package advent2018.day16.functions;

import vm.GeneralVM;
import vm.VMFunction;

import java.util.Optional;

public class SetI implements VMFunction {

    private final long value;
    private final String target;

    public SetI(long value, String target) {
        this.value = value;
        this.target = target;
    }

    @Override
    public int perform(GeneralVM vm) {
        return Optional.of(value)
                .map(l -> vm.set(target, l))
                .map(l -> vm.jump(1))
                .orElseThrow(RuntimeException::new);
    }
}
