package advent2020.day12;

public class Left implements Rule {

    private final int degrees;

    public Left(int degrees) {
        this.degrees = degrees;
    }

    @Override
    public void apply(IFerry ferry) {
        ferry.turnLeft(degrees);
    }

}
