package vm;

public class RemainderFunction extends AbstractTwoArgumentFunction {

    public RemainderFunction(String register, String value) {
        super(register, value, (a, b) -> a % b);
    }

}
