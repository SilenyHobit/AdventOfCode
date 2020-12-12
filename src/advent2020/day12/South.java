package advent2020.day12;

public class South implements Rule {

    private final int modifier;

    public South(int modifier) {
        this.modifier = modifier;
    }

    @Override
    public void apply(IFerry ferry) {
        ferry.south(modifier);
    }

}
