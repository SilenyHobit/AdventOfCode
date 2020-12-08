package advent2020.vm;

import java.util.concurrent.atomic.AtomicBoolean;

public class Jmp implements VM2020Op {

    private final int offset;

    private final AtomicBoolean visited = new AtomicBoolean(false);

    public Jmp(int offset) {
        this.offset = offset;
    }

    @Override
    public void execute(VM2020 vm) {
        if (visited.getAndSet(true))
            vm.stop();
        else
            vm.jump(offset);
    }

    public VM2020Op toNop() {
        return new Nop(offset);
    }

    @Override
    public void reset() {
        visited.set(false);
    }

}
