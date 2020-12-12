package advent2020.day12;

public class Ferry implements IFerry {

    private int x;
    private int y;
    private Facing facing = Facing.EAST;

    private enum Facing {
        NORTH, SOUTH, WEST, EAST;

        Facing turnLeft(int degrees) {
            switch (degrees) {
                case 90:
                    switch (this) {
                        case NORTH:
                            return WEST;
                        case WEST:
                            return SOUTH;
                        case SOUTH:
                            return EAST;
                        case EAST:
                            return NORTH;
                    }
                    break;
                case 180:
                    switch (this) {
                        case NORTH:
                            return SOUTH;
                        case WEST:
                            return EAST;
                        case SOUTH:
                            return NORTH;
                        case EAST:
                            return WEST;
                    }
                    break;
                case 270:
                    switch (this) {
                        case NORTH:
                            return EAST;
                        case WEST:
                            return NORTH;
                        case SOUTH:
                            return WEST;
                        case EAST:
                            return SOUTH;
                    }
                    break;
            }
            throw new IllegalArgumentException();
        }

    }

    public void north(int modifier) {
        y += modifier;
    }

    public void west(int modifier) {
        x -= modifier;
    }

    public void south(int modifier) {
        y -= modifier;
    }

    public void east(int modifier) {
        x += modifier;
    }

    public void forward(int modifier) {
        switch (facing) {
            case NORTH:
                north(modifier);
                break;
            case WEST:
                west(modifier);
                break;
            case SOUTH:
                south(modifier);
                break;
            case EAST:
                east(modifier);
                break;
        }
    }

    public void turnLeft(int degrees) {
        facing = facing.turnLeft(degrees);
    }

    public long manhattanDistance() {
        return Math.abs(x) + Math.abs(y);
    }

}
