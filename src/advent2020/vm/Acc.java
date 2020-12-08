package advent2020.vm;

import java.util.concurrent.atomic.AtomicBoolean;

public class Acc implements VM2020Op {

    private final long offset;

    private final AtomicBoolean visited = new AtomicBoolean(false);

    public Acc(long offset) {
        this.offset = offset;
    }

    @Override
    public void execute(VM2020 vm) {
        if (visited.getAndSet(true))
            vm.stop();
        else {
            vm.adjustAccumulator(offset);
            vm.jump(1);
        }
    }

    @Override
    public void reset() {
        visited.set(false);
    }

}
