package advent2020.vm;

import java.util.concurrent.atomic.AtomicBoolean;

public class Nop implements VM2020Op {

    private final int offset;

    private final AtomicBoolean visited = new AtomicBoolean(false);

    public Nop(int offset) {
        this.offset = offset;
    }

    @Override
    public void execute(VM2020 vm) {
        if (visited.getAndSet(true))
            vm.stop();
        else
            vm.jump(1);
    }

    public VM2020Op toJmp() {
        return new Jmp(offset);
    }

    @Override
    public void reset() {
        visited.set(false);
    }

}
