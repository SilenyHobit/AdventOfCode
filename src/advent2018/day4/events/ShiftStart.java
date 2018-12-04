package advent2018.day4.events;

import advent2018.day4.GuardProcessor;

public class ShiftStart extends Event {

    private final String guardId;

    public ShiftStart(String dateTime, String guardId) {
        super(dateTime);
        this.guardId = guardId;
    }

    @Override
    public int process(GuardProcessor processor) {
        return processor.guardChange(guardId);
    }
}
