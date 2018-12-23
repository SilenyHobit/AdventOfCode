package advent2018.day16.functions;

import vm.GeneralVM;
import vm.VMFunction;

import java.util.Optional;

public class SetR implements VMFunction {

    private final String origin;
    private final String target;

    public SetR(String origin, String target) {
        this.origin = origin;
        this.target = target;
    }

    @Override
    public int perform(GeneralVM vm) {
        return Optional.of(vm.get(origin))
                .map(l -> vm.set(target, l))
                .map(l -> vm.jump(1))
                .orElseThrow(RuntimeException::new);
    }
}
