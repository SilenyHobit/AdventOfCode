package advent2016.day12;

public class Increment implements Instruction {

    private final String register;
    private final boolean valid;

    public Increment(String register) {
        this.register = register;
        boolean valid;
        try {
            Integer.parseInt(register);
            valid = false;
        } catch (Exception e) {
            valid = true;
        }
        this.valid = valid;
    }

    @Override
    public void perform(VM vm) {
        if (valid)
            vm.set(register, vm.get(register) + 1);
        vm.jump(1);
    }

    @Override
    public Instruction toggle() {
        return new Decrement(register);
    }
}
