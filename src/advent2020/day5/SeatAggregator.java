package advent2020.day5;

public class SeatAggregator {

    private int previous = -1;
    private boolean found;

    int missingId;

    public SeatAggregator next(int seatId) {
        if (found)
            return this;
        if (seatId != previous + 2 || previous == -1)
            previous = seatId;
        else {
            int row = (seatId - 1) / 8;
            int seat = (seatId - 1) % 8;
            missingId = row * 8 + seat;
            found = true;
        }

        return this;
    }

    int missingId() {
        return missingId;
    }

}
