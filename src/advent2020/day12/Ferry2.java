package advent2020.day12;

public class Ferry2 implements IFerry {

    private long x;
    private long y;

    private long waypointX = 10;
    private long waypointY = 1;

    public void north(int modifier) {
        waypointY += modifier;
    }

    public void west(int modifier) {
        waypointX -= modifier;
    }

    public void south(int modifier) {
        waypointY -= modifier;
    }

    public void east(int modifier) {
        waypointX += modifier;
    }

    public void forward(int modifier) {
        x += waypointX * modifier;
        y += waypointY * modifier;
    }

    public void turnLeft(int degrees) {
        long newX;
        long newY;
        switch (degrees) {
            case 90:
                newX = -waypointY;
                newY = waypointX;
                break;
            case 180:
                newX = -waypointX;
                newY = -waypointY;
                break;
            case 270:
                newX = waypointY;
                newY = -waypointX;
                break;
            default:
                throw new IllegalArgumentException();
        }

        waypointX = newX;
        waypointY = newY;
    }

    public long manhattanDistance() {
        return Math.abs(x) + Math.abs(y);
    }

}
