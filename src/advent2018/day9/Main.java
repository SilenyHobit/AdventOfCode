package advent2018.day9;

import util.Conversion;
import util.InputConverter;
import util.InputLoader;
import util.Pair;

import java.util.*;
import java.util.regex.Pattern;

public class Main {

    private static final Conversion<Pair<Integer, Integer>> conversion = new Conversion<>(Pattern.compile("(\\d+) players; last marble is worth (\\d+) points"),
            m -> new Pair<>(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2))));

    public static void main(String[] args) throws Exception {
        Pair<Integer, Integer> input = InputLoader.loadInput(new InputConverter<>(Collections.singletonList(conversion))).get(0);

        long[] players = new long[input.getFirst()];
        CircularList<Integer> marbles = new CircularList<>(0);

        input = new Pair<>(input.getFirst(), input.getSecond()*100);

        int player = 0;
        for (int i = 1; i <= input.getSecond(); i++) {
            if (i % 23 == 0) {
                marbles.rotate(-7);
                players[player] += marbles.remove();
                players[player] += i;
            } else {
                marbles.rotate(2);
                marbles.add(i);
            }
            player = (player+1) % input.getFirst();
        }

        System.out.println(Arrays.stream(players).max().getAsLong());
    }

    private static class CircularList<T> {

        private Item<T> current;

        public CircularList(T first) {
            this.current = new Item<>(first);
            this.current.next = current;
            this.current.previous = current;
        }

        void add(T item) {
            Item<T> newItem = new Item<>(item);
            Item<T> previous = current.previous;
            current.previous = newItem;
            newItem.next = current;
            newItem.previous = previous;
            previous.next = newItem;
            current = newItem;
        }

        T rotate(int diff) {
            if (diff > 0) {
                while(diff-- != 0)
                    current = current.next;
            } else {
                while(diff++ != 0)
                    current = current.previous;
            }
            return current.value;
        }

        T remove() {
            current.previous.next = current.next;
            current.next.previous = current.previous;
            T value = current.value;
            current = current.next;
            return value;
        }
    }

    private static class Item<T> {
        Item<T> previous;
        Item<T> next;
        final T value;

        private Item(T value) {
            this.value = value;
        }
    }

}
