package advent2017.day18;

public class ReceiveFunction implements VMFunction {

    private final String register;

    public ReceiveFunction(String register) {
        this.register = register;
    }

    @Override
    public void perform(VM vm) {
        vm.receive(register);
    }
}
