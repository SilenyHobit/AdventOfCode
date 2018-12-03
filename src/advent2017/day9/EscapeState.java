package advent2017.day9;

public class EscapeState implements State {
    private final State previous;

    public EscapeState(State previous) {
        this.previous = previous;
    }

    @Override
    public void process(char c, Parser parser) {
        parser.setState(previous);
    }
}
