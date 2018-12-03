package advent2016.day10;

import util.InputLoader;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private static final Pattern valuePattern = Pattern.compile("value (\\d+) goes to bot (\\d+)");
    private static final Pattern botPattern = Pattern.compile("bot (\\d+) gives low to (bot|output) (\\d+) and high to (bot|output) (\\d+)");

    private static final Map<Integer, Bot> numberToBot = new HashMap<>();
    private static final List<Output> outputs = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        List<String> input = InputLoader.loadInput();

        input.forEach(line -> {
            Matcher m = botPattern.matcher(line);
            if (m.find()) {
                Bot bot = numberToBot.computeIfAbsent(Integer.parseInt(m.group(1)), Bot::new);
                if (m.group(2).equals("bot")) {
                    bot.lowTarget = numberToBot.computeIfAbsent(Integer.parseInt(m.group(3)), Bot::new);
                } else {
                    Output output = new Output(bot, Integer.parseInt(m.group(3)));
                    bot.lowTarget = output;
                    outputs.add(output);
                }
                if (m.group(4).equals("bot")) {
                    bot.highTarget = numberToBot.computeIfAbsent(Integer.parseInt(m.group(5)), Bot::new);
                } else {
                    Output output = new Output(bot, Integer.parseInt(m.group(5)));
                    bot.highTarget = output;
                    outputs.add(output);
                }
                bot.lowTarget.addParent(bot);
                bot.highTarget.addParent(bot);
                return;
            }

            m = valuePattern.matcher(line);
            if (m.find()) {
                Bot bot = numberToBot.computeIfAbsent(Integer.parseInt(m.group(2)), Bot::new);
                Value value = new Value(Integer.parseInt(m.group(1)), bot);
                bot.addParent(value);
                return;
            }

            throw new IllegalArgumentException();
        });

        outputs.forEach(output -> output.getValue(null));

        OptionalInt res = numberToBot.values().stream()
                .filter(bot -> bot.high == 61 && bot.low == 17)
                .mapToInt(bot -> bot.number)
                .findAny();

        System.out.println(res.getAsInt());
        System.out.println(outputValue(0)*outputValue(1)*outputValue(2));
    }

    private static int outputValue(int outputNumber) {
        return outputs.stream().filter(output -> output.number == outputNumber).mapToInt(output -> output.getValue(null)).findAny().getAsInt();
    }

    private interface Node {
        int getValue(Node node);
        void addParent(Node parent);
    }

    private static class Value implements Node {

        private final int val;
        private final Bot child;

        private Value(int val, Bot child) {
            this.val = val;
            this.child = child;
        }

        @Override
        public int getValue(Node node) {
            return val;
        }

        @Override
        public void addParent(Node parent) {

        }
    }

    private static class Bot implements Node {

        private final int number;

        private List<Node> parents = new ArrayList<>();

        private Integer low;
        private Integer high;

        private Node lowTarget;
        private Node highTarget;

        private Bot(int number) {
            this.number = number;
        }

        @Override
        public int hashCode() {
            return number;
        }

        @Override
        public boolean equals(Object o) {
            Bot other = (Bot) o;
            return number == other.number;
        }

        @Override
        public void addParent(Node parent) {
            parents.add(parent);
        }

        @Override
        public int getValue(Node node) {
            if (low == null || high == null) {
                int first = parents.get(0).getValue(this);
                int second = parents.get(1).getValue(this);
                if (first < second) {
                    low = first;
                    high = second;
                } else {
                    high = first;
                    low = second;
                }
            }

            return node.equals(highTarget) ? high : low;
        }
    }

    private static class Output implements Node {
        private final Node parent;
        private final int number;

        private Output(Node parent, int number) {
            this.parent = parent;
            this.number = number;
        }

        @Override
        public int getValue(Node node) {
            return parent.getValue(this);
        }

        @Override
        public void addParent(Node parent) {

        }
    }

}
