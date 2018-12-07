package advent2018.day7;

import util.Pair;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Plan {

    private final Map<String, Set<String>> dependencyMap = new HashMap<>();
    private final Set<String> steps = new HashSet<>();
    private final Set<String> used = new HashSet<>();
    private final Queue<String> available = new PriorityQueue<>();

    Plan process(Pair<String, String> pair) {
        dependencyMap.computeIfAbsent(pair.getFirst(), p -> new HashSet<>()).add(pair.getSecond());
        steps.add(pair.getFirst());
        steps.add(pair.getSecond());
        return this;
    }

    private Stream<String> roots() {
        return steps.stream()
                .filter(this::isRoot);
    }

    private Queue<String> addRoot(Queue<String> queue) {
        return roots()
                .map(queue::add)
                .reduce(queue, (q,s) -> q, (a,b) -> a);
    }

    private boolean isRoot(String step) {
        return dependencyMap.values().stream().filter(l -> l.contains(step)).count() == 0L;
    }

    String path() {
        return Optional.of(new PriorityQueue<String>())
                .map(this::addRoot)
                .map(this::path)
                .orElseThrow(RuntimeException::new);
    }

    private String path(Queue<String> queue) {
        return Stream.generate(queue::poll)
                .filter(step -> !used.contains(step))
                .filter(this::isRoot)
                .limit(steps.size())
                .map(step -> addDeps(step, queue))
                .map(this::remove)
                .collect(Collectors.joining());
    }

    String remove(String step) {
        return Optional.of(step)
                .map(l -> used.add(step))
                .map(b -> dependencyMap.remove(step))
                .map(l -> step)
                .orElse(step);
    }

    private String addDeps(String step, Queue<String> queue) {
        return dependencyMap.getOrDefault(step, new HashSet<>()).stream()
                .map(queue::add)
                .reduce(step,(s,b) -> step, (a,b) -> a);
    }

    private Queue<String> cleanup(Queue<String> queue) {
        return used.stream()
                .map(step -> queue.stream().filter(step::equals).collect(Collectors.toList()))
                .map(list -> list.stream().map(queue::remove).count())
                .reduce(queue, (q,a) -> q, (a,b) -> a);
    }

    Queue<String> available() {
        return Optional.of(addRoot(available))
                .map(this::cleanup)
                .orElseThrow(RuntimeException::new);
    }

    Optional<Plan> asOptional() {
        return Optional.of(this);
    }

}
