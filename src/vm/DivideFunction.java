package vm;

public class DivideFunction extends AbstractTwoArgumentFunction {

    public DivideFunction(String register, String value) {
        super(register, value, (a, b) -> a / b);
    }

}
