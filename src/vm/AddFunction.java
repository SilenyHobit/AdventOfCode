package vm;

public class AddFunction extends AbstractTwoArgumentFunction {

    public AddFunction(String register, String value) {
        super(register, value, (a, b) -> a + b);
    }

}
