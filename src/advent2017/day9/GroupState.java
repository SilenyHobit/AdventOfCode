package advent2017.day9;

public class GroupState implements State {
    @Override
    public void process(char c, Parser parser) {
        switch (c) {
            case '{':
                parser.openGroup();
                break;
            case '}':
                parser.closeGroup();
                break;
            case '<':
                parser.setState(new GarbageState());
                break;
        }
    }
}
