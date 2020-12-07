package advent2020.day7;

import java.util.*;

public class Bag {

    private final String color;
    private final Map<String, Integer> bags;

    public Bag(String color, Map<String, Integer> bags) {
        this.color = color;
        this.bags = Map.copyOf(bags);
    }

    public String getColor() {
        return color;
    }

    public Map<String, Integer> getBags() {
        return bags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bag bag = (Bag) o;
        return Objects.equals(color, bag.color) && Objects.equals(bags, bag.bags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, bags);
    }
}
