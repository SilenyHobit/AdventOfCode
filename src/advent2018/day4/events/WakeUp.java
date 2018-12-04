package advent2018.day4.events;

import advent2018.day4.GuardProcessor;

public class WakeUp extends Event {

    public WakeUp(String dateTime) {
        super(dateTime);
    }

    @Override
    public int process(GuardProcessor processor) {
        return processor.wakeUp();
    }
}
