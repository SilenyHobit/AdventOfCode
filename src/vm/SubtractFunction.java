package vm;

public class SubtractFunction extends AbstractTwoArgumentFunction {

    public SubtractFunction(String register, String value) {
        super(register, value, (a, b) -> a - b);
    }

}
