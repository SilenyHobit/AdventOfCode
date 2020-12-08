package advent2020.day8;

import advent2020.vm.*;
import util.Conversion;
import util.ExecutionWatcher;
import util.InputConverter;
import util.InputLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class Main {

    private static final Conversion<VM2020Op> NOP = new Conversion<>(Pattern.compile("nop ([+-]\\d+)"), matcher -> new Nop(Integer.parseInt(matcher.group(1))));
    private static final Conversion<VM2020Op> ACC = new Conversion<>(Pattern.compile("acc ([+-]\\d+)"), matcher -> new Acc(Long.parseLong(matcher.group(1))));
    private static final Conversion<VM2020Op> JMP = new Conversion<>(Pattern.compile("jmp ([+-]\\d+)"), matcher -> new Jmp(Integer.parseInt(matcher.group(1))));

    public static void main(String[] args) throws Exception {
        var watcher = new ExecutionWatcher();

        List<VM2020Op> operations = InputLoader.loadInput(new InputConverter<>(Arrays.asList(NOP, ACC, JMP)));

        watcher.parsed();

        var vm = new VM2020(operations);
        vm.run();

        watcher.part1(vm.getAccumulator());

        for (int i = 0; i < operations.size(); i++) {
            if (test(i, operations, watcher))
                break;
        }

        watcher.finish();
    }

    private static boolean test(int index, List<VM2020Op> operations, ExecutionWatcher watcher) {
        VM2020Op op = operations.get(index);
        List<VM2020Op> modifiedOps;
        if (op instanceof Jmp) {
            modifiedOps = new ArrayList<>(operations);
            modifiedOps.remove(index);
            modifiedOps.add(index, ((Jmp) op).toNop());
        } else if (op instanceof Nop) {
            modifiedOps = new ArrayList<>(operations);
            modifiedOps.remove(index);
            modifiedOps.add(index, ((Nop) op).toJmp());
        } else
            return false;


        modifiedOps.forEach(VM2020Op::reset);
        var modifiedVM = new VM2020(modifiedOps);
        modifiedVM.run();

        if (modifiedVM.isFinished())
            watcher.part2(modifiedVM.getAccumulator());

        return modifiedVM.isFinished();
    }

}
