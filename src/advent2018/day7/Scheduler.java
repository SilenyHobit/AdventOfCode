package advent2018.day7;

import util.Pair;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class Scheduler {

    private final AtomicInteger time = new AtomicInteger(0);
    private final Plan plan;
    private final List<Integer> workers;
    private final List<String> items;


    Scheduler(Plan plan) {
        this.plan = plan;
        workers = Stream.generate(() -> 0).limit(5).collect(Collectors.toList());
        items = Stream.generate(() -> "").limit(5).collect(Collectors.toList());
    }

    int perform() {
        return Stream.generate(() -> time.updateAndGet(i -> nextTime()))
                .map(t -> finishWork(time.get()))
                .map(c -> scheduleWork(plan.available()))
                .filter(c -> items.stream().filter(item -> !item.isEmpty()).count() == 0L)
                .map(c -> time.get())
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

    private int nextTime() {
        return workers.stream()
                .mapToInt(j -> j)
                .filter(j -> j != 0)
                .min()
                .orElse(0);
    }

    private long finishWork(int t) {
        return IntStream.range(0, workers.size())
                .filter(i -> workers.get(i) == t)
                .mapToObj(i -> plan.remove(items.get(i)))
                .map(plan::remove)
                .map(items::indexOf)
                .map(i -> items.set(i, ""))
                .map(i -> workers.indexOf(t))
                .map(i -> workers.set(i, 0))
                .count();
    }

    private long scheduleWork(Queue<String> steps) {
        return IntStream.range(0, workers.size())
                .filter(i -> workers.get(i) <= time.get())
                .map(i -> addStep(steps, i))
                .count();
    }

    private int addStep(Queue<String> steps, int worker) {
        return Stream.generate(steps::poll)
                .limit(steps.size())
                .filter(step -> !step.isEmpty())
                .filter(step -> !items.contains(step))
                .map(step -> new Pair<>(items.set(worker, step), step))
                .map(step -> workers.set(worker, step.getSecond().charAt(0)-'A'+61+time.get()))
                .findFirst()
                .orElse(0);
    }

}
