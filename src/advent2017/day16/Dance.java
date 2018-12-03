package advent2017.day16;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Dance {

    private static final Pattern SWITCH = Pattern.compile("s([0-9]+)");
    private static final Pattern EXCHANGE = Pattern.compile("x([0-9]+)/([0-9]+)");
    private static final Pattern PARTNER = Pattern.compile("p([a-z]+)/([a-z]+)");

    private Map<String, Integer> places;
    private Map<Integer, String> line;

    public Dance() {
        places = new HashMap<>();
        line = new HashMap<>();

        IntStream.range(0, 16).forEach(i -> {
            String program = String.valueOf((char) ('a' + i));
            places.put(program, i);
            line.put(i, program);
        });
    }

    public void perform(String commands) {
        for (String command : commands.split(",")) {
            Matcher m = SWITCH.matcher(command);
            if (m.find()) {
                sw(Integer.parseInt(m.group(1)));
                continue;
            }

            m = EXCHANGE.matcher(command);
            if (m.find()) {
                exchange(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)));
                continue;
            }

            m = PARTNER.matcher(command);
            if (m.find()) {
                partner(m.group(1), m.group(2));
            }
        }
    }

    private void sw(int length) {
        Map<Integer, String> newLine = new HashMap<>();
        line.forEach((key, value) -> {
            if (key + length >= line.size())
                newLine.put((key-line.size())+length, value);
            else
                newLine.put(key+length, value);
        });

        line = newLine;
        updatePlaces();
    }

    private void updatePlaces() {
        places = new HashMap<>();
        line.forEach((key, value) -> places.put(value, key));
    }

    private void exchange(int i1, int i2) {
        String p1 = line.get(i1);
        String p2 = line.get(i2);

        line.put(i1, p2);
        line.put(i2, p1);

        places.put(p1, i2);
        places.put(p2, i1);
    }

    private void partner(String p1, String p2) {
        int i1 = places.get(p1);
        int i2 = places.get(p2);

        line.put(i1, p2);
        line.put(i2, p1);

        places.put(p1, i2);
        places.put(p2, i1);
    }

    public String standing() {
        return line.entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getKey))
                .map(Map.Entry::getValue)
                .collect(Collectors.joining());
    }

}
