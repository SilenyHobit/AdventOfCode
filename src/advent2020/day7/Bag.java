package advent2020.day7;

import java.util.*;

public class Bag {

    private final String color;
    private final Map<String, Integer> bags;
    private List<Bag> potentialParents = new ArrayList<>();
    private boolean visited;

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

    public void addPotentialParent(Bag parent) {
        potentialParents = new ArrayList<>(potentialParents);
        potentialParents.add(parent);
    }

    public Collection<Bag> getPotentialParents() {
        return Collections.unmodifiableCollection(potentialParents);
    }

    public void visit() {
        visited = true;
    }

    public boolean isVisited() {
        return visited;
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
