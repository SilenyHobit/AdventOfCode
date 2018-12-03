package advent2016.day4;

import util.InputLoader;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

    private static final Pattern linePattern = Pattern.compile("([^0-9]*)([0-9]+)\\[([a-z]+)\\]");

    public static void main(String[] args) throws Exception {
        List<String> input = InputLoader.loadInput();

        List<Room> validRooms = input.stream()
                .map(Room::new)
                .filter(Room::isValid)
                .collect(Collectors.toList());

        int result = validRooms.stream()
                .mapToInt(Room::getId)
                .sum();

        System.out.println(result);

        String target = validRooms.stream()
                .map(Room::decrypt)
                .filter(str -> str.contains("north"))
                .findAny()
                .get();

        System.out.println(target);
    }

    private static class Room {

        private final String name;
        private final String code;
        private final String checksum;

        private Room(String code) {
            Matcher m = linePattern.matcher(code);

            if (!m.find())
                throw new IllegalArgumentException();

            this.name = m.group(1);
            this.code = m.group(2);
            this.checksum = m.group(3);
        }

        boolean isValid() {
            String encrypted = name.replaceAll("-", "");

            List<Character> result = encrypted.chars()
                    .mapToObj(i -> (char) i)
                    .collect(Collectors.groupingBy(
                            Function.identity(),
                            Collectors.counting()
                    ))
                    .entrySet().stream()
                    .sorted(new LetterComparator())
                    .limit(5)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());

            Optional<Boolean> error = IntStream.range(0, 5)
                    .mapToObj(i -> result.get(i).equals(checksum.charAt(i)))
                    .filter(b -> !b)
                    .findAny();

            return !error.isPresent();
        }

        int getId() {
            return Integer.parseInt(code);
        }

        String decrypt() {
            int mod = 'z' - 'a' + 1;
            int offset = Integer.parseInt(code);

            String decrypted = name.chars()
                    .map(i -> {
                        if (i == '-')
                            return i;

                        int pos = i - 'a';
                        pos += offset;
                        pos %= mod;

                        return pos + 'a';
                    })
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();

            return decrypted + code;
        }
    }

    private static class LetterComparator implements Comparator<Map.Entry<Character, Long>> {

        @Override
        public int compare(Map.Entry<Character, Long> characterLongEntry, Map.Entry<Character, Long> t1) {
            int res = t1.getValue().compareTo(characterLongEntry.getValue());
            if (res == 0)
                res = characterLongEntry.getKey().compareTo(t1.getKey());
            return res;
        }

    }

}
