package advent2018.day16.functions;

import vm.GeneralVM;
import vm.VMFunction;

import java.util.Optional;

public class GtRI implements VMFunction {

    private final String op1;
    private final long op2;
    private final String target;

    public GtRI(String op1, long op2, String target) {
        this.op1 = op1;
        this.op2 = op2;
        this.target = target;
    }

    @Override
    public int perform(GeneralVM vm) {
        return Optional.of(vm.get(op1))
                .map(l -> l > op2)
                .map(b -> b ? 1 : 0)
                .map(l -> vm.set(target, l))
                .map(l -> vm.jump(1))
                .orElseThrow(RuntimeException::new);
    }
}
