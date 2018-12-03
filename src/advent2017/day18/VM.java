package advent2017.day18;

import java.math.BigInteger;
import java.util.*;

public class VM {

    private final Map<String, BigInteger> registers = new HashMap<>();

    private final List<VMFunction> program;

    private final Queue<BigInteger> queue = new LinkedList<>();

    private boolean waiting;
    private boolean finished;
    private final boolean secondary;

    private VM other;

    private Long lastFrequency;

    private Long lastRecovered;

    private int position = 0;

    private int sendCounter = 0;

    public VM(List<VMFunction> program, VM other) {
        this.program = program;
        this.other = other;

        secondary = other == null;

        if (!secondary)
            other.other = this;
    }

    public int getSendCount() {
        return sendCounter;
    }

    public void run() {
        while (lastRecovered == null) {
            program.get(position).perform(this);
            if (waiting && secondary) {
                return;
            } else if (waiting) {
                other.runSecondary();
                if (other.finished || queue.isEmpty())
                    return;

                continue;
            }

            position++;

            if (position < 0 || position >= program.size()) {
                finished = true;
                break;
            }
        }
    }

    void runSecondary() {
        if (waiting && queue.isEmpty()) {
            finished = true;
            return;
        }

        this.run();
    }

    public BigInteger getRegister(String register) {
        return registers.computeIfAbsent(register, s -> BigInteger.ZERO);
    }

    public void setRegister(String register, BigInteger value) {
        registers.put(register, value);
    }

    public void jump(long offset) {
        position += offset-1;
    }

    public void play(long frequency) {
        lastFrequency = frequency;
    }

    public void recover() {
        lastRecovered = lastFrequency;
    }

    public Long getLastRecovered() {
        return lastRecovered;
    }

    public void send(BigInteger message) {
        other.queue.add(message);
        sendCounter++;
    }

    public void receive(String register) {
        if (queue.isEmpty()) {
            waiting = true;
        } else {
            waiting = false;
            setRegister(register, queue.poll());
        }
    }
}
