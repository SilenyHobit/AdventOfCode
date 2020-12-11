package advent2020.day11;

import util.ExecutionWatcher;
import util.InputLoader;

public class Main {

    public static void main(String[] args) throws Exception {
        var watcher = new ExecutionWatcher();
        int[][] input = InputLoader.loadInput().stream()
                .map(line -> line.chars().toArray())
                .toArray(int[][]::new);
        watcher.parsed();

        var gameOfLife = new GameOfLife(input, 4, false);

        boolean running;
        do {
            running = gameOfLife.cycle();
        } while (running);

        watcher.part1(gameOfLife.countOccupied());

        gameOfLife = new GameOfLife(input, 5, true);

        do {
            running = gameOfLife.cycle();
        } while (running);

        watcher.part2(gameOfLife.countOccupied());

        watcher.finish();

    }

}
