package advent2019.intcode;

public class OperationParser {

    public static Operation parse(int index, int[] code) {
        switch (code[index]) {
            case 99:
                return new Stop();
            case 1:
                return new Add(code[index+1], code[index+2], code[index+3]);
            case 2:
                return new Multiply(code[index+1], code[index+2], code[index+3]);
            default:
                throw new IllegalArgumentException();
        }
    }

}
