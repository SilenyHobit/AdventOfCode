package advent2020.day12;

public class North implements Rule {

    private final int modifier;

    public North(int modifier) {
        this.modifier = modifier;
    }

    @Override
    public void apply(IFerry ferry) {
        ferry.north(modifier);
    }

}
