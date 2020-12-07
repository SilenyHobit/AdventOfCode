package advent2019.intcode;

public class Stop implements Operation {

    @Override
    public void execute(IntcodeVM vm) {
        vm.stop();
    }

}
