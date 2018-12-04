package advent2018.day4;

import java.util.stream.IntStream;

public class GuardDuty {

    private TimeFrame timeFrame = new TimeFrame();
    private final String guardId;

    public GuardDuty(String guardId) {
        this.guardId = guardId;
    }

    public int sleep(int startMinute) {
        return IntStream.range(timeFrame.position(), startMinute+1)
                .boxed()
                .reduce(timeFrame, (tf, index) -> tf.map(index, startMinute, 1, 0), (a,b) -> a)
                .position();
    }

    public int wakeUp(int startMinute) {
        return IntStream.range(timeFrame.position(), startMinute+1)
                .boxed()
                .reduce(timeFrame, (tf, index) -> tf.map(index, startMinute, 0, 1), (a,b) -> a)
                .position();
    }

    public int finish() {
        return IntStream.range(timeFrame.position(), timeFrame.length())
                .boxed()
                .reduce(timeFrame, (tf, index) -> tf.map(index, timeFrame.length(), 0, timeFrame.current()), (a,b) -> a)
                .position();
    }

    public String guardId() {
        return guardId;
    }

    public int sum() {
        return timeFrame.sum();
    }

    TimeFrame timeFrame() {
        return timeFrame;
    }
}
