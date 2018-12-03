package advent2017.day9;

public class GarbageState implements State {

    @Override
    public void process(char c, Parser parser) {
        switch (c) {
            case '!':
                parser.setState(new EscapeState(this));
                break;
            case '>':
                parser.setState(new GroupState());
                break;
            default:
                parser.incrementGarbage();
        }
    }
}
