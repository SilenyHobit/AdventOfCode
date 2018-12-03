package advent2016.day12;

public class Decrement implements Instruction {

    private final String register;
    private final boolean valid;

    public Decrement(String register) {
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
            vm.set(register, vm.get(register) - 1);
        vm.jump(1);
    }

    @Override
    public Instruction toggle() {
        return new Increment(register);
    }
}
