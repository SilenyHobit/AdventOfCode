package advent2018.day16.functions;

import vm.GeneralVM;
import vm.VMFunction;

import java.util.Optional;

public class AddI implements VMFunction {

    private final long value;
    private final String origin;
    private final String target;

    public AddI(String origin, long value, String target) {
        this.origin = origin;
        this.value = value;
        this.target = target;
    }

    @Override
    public int perform(GeneralVM vm) {
        return Optional.of(value)
                .map(l -> l + vm.get(origin))
                .map(l -> vm.set(target, l))
                .map(l -> vm.jump(1))
                .orElseThrow(RuntimeException::new);
    }
}
