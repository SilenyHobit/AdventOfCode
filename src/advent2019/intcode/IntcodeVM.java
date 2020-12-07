package advent2019.intcode;

public class IntcodeVM {

    private final int[] code;

    private int index = 0;
    private boolean running = true;

    public IntcodeVM(int[] code) {
        this.code = code;
    }

    public void run() {
        while (running) {
            step();
        }
    }

    private void step() {
        OperationParser.parse(index, code).execute(this);
        if (running)
            index += 4;
    }


    void set(int index, int value) {
        code[index] = value;
    }

    public int get(int index) {
        return code[index];
    }

    void stop() {
        running = false;
    }

}
