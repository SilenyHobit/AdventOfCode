package advent2019.day2;

import advent2019.intcode.IntcodeVM;
import util.ExecutionWatcher;
import util.InputLoader;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws Exception {
        var watcher = new ExecutionWatcher();
        var stringCode = InputLoader.loadInput().get(0);

        int[] code = Arrays.stream(stringCode.split(","))
                .mapToInt(Integer::parseInt)
                .toArray();

        watcher.parsed();

        int[] part1 = Arrays.copyOf(code, code.length);
        part1[1] = 12;
        part1[2] = 2;

        var vm = new IntcodeVM(part1);
        vm.run();

        watcher.part1(vm.get(0));

        outer:
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                int[] codeCopy = Arrays.copyOf(code, code.length);
                codeCopy[1] = i;
                codeCopy[2] = j;
                vm = new IntcodeVM(codeCopy);
                vm.run();
                if (vm.get(0) == 19690720) {
                    watcher.part2(100 * i + j);
                    break outer;
                }
            }
        }

        watcher.finish();

    }

}
