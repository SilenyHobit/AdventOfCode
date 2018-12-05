package vm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

public class GeneralVM {

    private final Map<String, AtomicLong> registers;
    private final List<VMFunction> program;

    private final AtomicInteger position;

    public GeneralVM(List<VMFunction> program) {
        this.program = program;
        this.registers = new HashMap<>();
        this.position = new AtomicInteger(0);
    }

    public GeneralVM withRegister(String register, long value) {
        return Optional.of(register)
                .map(r -> registers.put(register, new AtomicLong(value)))
                .map(r -> this)
                .orElseThrow(IllegalArgumentException::new);
    }

    public GeneralVM run() {
        return IntStream.generate(() -> 0)
                .mapToObj(i -> program.get(position.get()))
                .map(function -> function.perform(this))
                .filter(i -> i < 0 || i >= program.size())
                .map(i -> this)
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

    public int jump(int difference) {
        return position.addAndGet(difference);
    }

    public long get(String register) {
        return registers.computeIfAbsent(register, r -> new AtomicLong(0L)).get();
    }

    public long set(String register, long value) {
        return registers.computeIfAbsent(register, r -> new AtomicLong(0L)).updateAndGet(l -> value);
    }

}
