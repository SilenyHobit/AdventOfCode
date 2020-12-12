package advent2020.day12;

public class East implements Rule {

    private final int modifier;

    public East(int modifier) {
        this.modifier = modifier;
    }

    @Override
    public void apply(IFerry ferry) {
        ferry.east(modifier);
    }

}
