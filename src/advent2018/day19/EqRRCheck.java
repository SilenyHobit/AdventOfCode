package advent2018.day19;

import vm.GeneralVM;
import vm.VMFunction;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

public class EqRRCheck implements VMFunction {

    private final String op1;
    private final String op2;
    private final String target;

    Set<Long> encountered = new LinkedHashSet<>();

    public EqRRCheck(String op1, String op2, String target) {
        this.op1 = op1;
        this.op2 = op2;
        this.target = target;
    }

    @Override
    public int perform(GeneralVM vm) {
        return Optional.of(check(vm.get(op1)))
                .map(l -> l == vm.get(op2))
                .map(b -> b ? 1 : 0)
                .map(l -> vm.set(target, l))
                .map(l -> vm.jump(1))
                .orElseThrow(RuntimeException::new);
    }

    long check(long r) {
        if (encountered.isEmpty())
            System.out.printf("r: %d%n", r);
        if (encountered.contains(r)) {
            long value = encountered.stream().skip(encountered.size()-1).findFirst().get();
            throw new RuntimeException("Found repeat! Previous value: " + value);
        }
        encountered.add(r);
        return r;
    }

}
