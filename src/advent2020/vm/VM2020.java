package advent2020.vm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class VM2020 {

    private final List<VM2020Op> operations;

    private int index = 0;
    private long accumulator = 0L;
    private boolean running = true;
    private boolean finished = false;

    public VM2020(Collection<VM2020Op> operations) {
        this.operations = new ArrayList<>(operations);
    }

    public void run() {
        while(running) {
            if (index == operations.size()) {
                finished = true;
                break;
            }

            operations.get(index).execute(this);
        }
    }

    void adjustAccumulator(long offset) {
        accumulator += offset;
    }

    void jump(int offset) {
        index += offset;
    }

    void stop() {
        running = false;
    }

    public long getAccumulator() {
        return accumulator;
    }

    public boolean isFinished() {
        return finished;
    }
}
