package advent2018.day23;

import util.Conversion;
import util.InputConverter;
import util.InputLoader;

import java.util.*;
import java.util.regex.Pattern;

public class Main {

    private static final Conversion<NanoBot> BOT_CONVERSION = new Conversion<>(Pattern.compile("pos=<(-?\\d+),(-?\\d+),(-?\\d+)>, r=(\\d+)"),
            m -> new NanoBot(Long.parseLong(m.group(1)), Long.parseLong(m.group(2)), Long.parseLong(m.group(3)), Long.parseLong(m.group(4))));

    public static void main(String[] args) throws Exception {
        List<NanoBot> bots = InputLoader.loadInput(new InputConverter<>(Collections.singletonList(BOT_CONVERSION)));
        bots.sort(Comparator.comparingLong(NanoBot::getRange).reversed());

        NanoBot strongest = bots.get(0);

        long inRange = bots.stream()
                .filter(strongest::isBotInRange)
                .count();

        System.out.println(inRange);

        TreeMap<Long, Integer> ranges = new TreeMap<>();
        bots.forEach(bot -> {
            ranges.put(bot.getX() + bot.getY() + bot.getZ() - bot.getRange(), 1);
            ranges.put(bot.getX() + bot.getY() + bot.getZ() + bot.getRange() + 1, -1);
        });

        int count = 0;
        List<Long> result = new ArrayList<>();
        int maxCount = 0;
        for (Map.Entry<Long, Integer> entry : ranges.entrySet()) {
            count += entry.getValue();
            if (count > maxCount) {
                result = new ArrayList<>();
                result.add(entry.getKey());
                maxCount = count;
            } else if (count == maxCount-1) {
                result.add(entry.getKey());
            }
        }

        System.out.println(maxCount);
        System.out.println(result);

    }

}
