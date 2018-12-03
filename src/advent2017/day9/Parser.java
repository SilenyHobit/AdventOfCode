package advent2017.day9;

public class Parser {

    private long value = 0L;

    private State state = new GroupState();

    private long currentGroupValue = 0L;

    private long garbage = 0L;

    public void process(char c) {
        state.process(c, this);
    }

    public void openGroup() {
        currentGroupValue += 1L;
    }

    public void closeGroup() {
        value += currentGroupValue;
        currentGroupValue -= 1L;
    }

    public void setState(State state) {
        this.state = state;
    }

    public long getValue() {
        return value;
    }

    public void incrementGarbage() {
        garbage += 1L;
    }

    public long getGarbage() {
        return garbage;
    }
}
