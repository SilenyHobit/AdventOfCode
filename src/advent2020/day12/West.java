package advent2020.day12;

public class West implements Rule {

    private final int modifier;

    public West(int modifier) {
        this.modifier = modifier;
    }

    @Override
    public void apply(IFerry ferry) {
        ferry.west(modifier);
    }

}
