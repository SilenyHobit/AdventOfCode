package advent2018.day4.events;

import advent2018.day4.GuardProcessor;

public class FallAsleep extends Event {

    public FallAsleep(String dateTime) {
        super(dateTime);
    }

    @Override
    public int process(GuardProcessor processor) {
        return processor.sleep();
    }
}
