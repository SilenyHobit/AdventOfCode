package advent2018.day4;

import advent2018.day4.events.Event;
import util.CheckedWrapper;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.MonthDay;
import java.time.OffsetTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class GuardProcessor {

    private Instant currentTime = CheckedWrapper.wrap(() -> new SimpleDateFormat("YYYY-MM-dd HH:mm").parse("1518-01-01 00:00").toInstant());

    private GuardDuty currentDuty;

    private final List<GuardDuty> history = new ArrayList<>();

    GuardProcessor process(Event event) {
        Instant eventTime = event.dateTime();
        if ((dayOfMonth(currentTime) != dayOfMonth(eventTime) || hourOfDay(eventTime) != 0) && currentDuty != null) {
            currentDuty.finish();
            history.add(currentDuty);
            currentDuty = new GuardDuty(currentDuty.guardId());
        }
        currentTime = eventTime;
        event.process(this);
        return this;
    }

    public int sleep() {
        return currentDuty.sleep(minuteOfHour(currentTime));
    }

    public int wakeUp() {
        return currentDuty.wakeUp(minuteOfHour(currentTime));
    }

    public int guardChange(String id) {
        currentDuty = new GuardDuty(id);
        return 1;
    }

    public List<GuardDuty> duties() {
        return new ArrayList<>(history);
    }

    private int dayOfMonth(Instant instant) {
        return MonthDay.from(instant.atZone(ZoneId.of("UTC"))).getDayOfMonth();
    }

    private int hourOfDay(Instant instant) {
        return OffsetTime.from(instant.atZone(ZoneId.of("UTC"))).getHour();
    }

    private int minuteOfHour(Instant instant) {
        return OffsetTime.from(instant.atZone(ZoneId.of("UTC"))).getMinute();
    }

}
