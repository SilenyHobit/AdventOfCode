package advent2020.day12;

public class Forward implements Rule {

    private final int modifier;

    public Forward(int modifier) {
        this.modifier = modifier;
    }

    @Override
    public void apply(IFerry ferry) {
        ferry.forward(modifier);
    }

}
