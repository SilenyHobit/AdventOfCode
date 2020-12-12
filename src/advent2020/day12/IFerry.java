package advent2020.day12;

public interface IFerry {

    void north(int modifier);

    void west(int modifier);

    void south(int modifier);

    void east(int modifier);

    void forward(int modifier);

    void turnLeft(int degrees);

    long manhattanDistance();

}
