package advent2020.day12;

public class Right implements Rule {

    private final int degrees;

    public Right(int degrees) {
        this.degrees = degrees;
    }

    @Override
    public void apply(IFerry ferry) {
        ferry.turnLeft(360-degrees);
    }

}
