package advent2018.day4.events;

import advent2018.day4.GuardProcessor;
import util.CheckedWrapper;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public abstract class Event {

    private static final DateTimeFormatter FMT = new DateTimeFormatterBuilder()
            .appendPattern("yyyy-MM-dd HH:mm")
            .toFormatter()
            .withZone(ZoneId.of("UTC"));

    private final Instant dateTime;

    public Event(String dateTime) {
        this.dateTime = CheckedWrapper.wrap(() -> FMT.parse(dateTime, Instant::from));
    }

    public abstract int process(GuardProcessor processor);

    public Instant dateTime() {
        return dateTime;
    }
}
