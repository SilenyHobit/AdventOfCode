package advent2020.day6;

public class GroupAggregator {

    private Group group = new Group();

    private long sum;
    private long sum2;

    GroupAggregator next(String line) {
        if (line.isEmpty()) {
            sum += group.sum();
            sum2 += group.sum2();
            group = new Group();
        } else {
            group.addLine(line);
        }
        return this;
    }

    GroupAggregator finish() {
        sum += group.sum();
        sum2 += group.sum2();
        group = new Group();
        return this;
    }

    long sum() {
        return sum;
    }

    long sum2() {
        return sum2;
    }

}
