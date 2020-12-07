package advent2019.intcode;

public class Multiply implements Operation {

    private final int first;
    private final int second;
    private final int third;

    public Multiply(int first, int second, int third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    @Override
    public void execute(IntcodeVM vm) {
        vm.set(third, vm.get(first) * vm.get(second));
    }
}
