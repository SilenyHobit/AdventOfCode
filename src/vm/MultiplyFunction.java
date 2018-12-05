package vm;

public class MultiplyFunction extends AbstractTwoArgumentFunction {

    public MultiplyFunction(String register, String value) {
        super(register, value, (a, b) -> a * b);
    }
}
