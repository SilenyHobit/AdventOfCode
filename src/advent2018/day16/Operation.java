package advent2018.day16;

public class Operation {

    private final VMStatus before;
    private final VMStatus after;
    private final String opcode;
    private final String firstOperand;
    private final String secondOperand;
    private final String thirdOperand;

    public Operation(String before, String op, String after) {
        this.before = VMStatus.fromString(before);
        this.after = VMStatus.fromString(after);
        String[] opArray = op.split(" ");
        opcode = opArray[0];
        firstOperand = opArray[1];
        secondOperand = opArray[2];
        thirdOperand = opArray[3];
    }

    public VMStatus getBefore() {
        return before;
    }

    public VMStatus getAfter() {
        return after;
    }

    public String getOpcode() {
        return opcode;
    }

    public String getFirstOperand() {
        return firstOperand;
    }

    public String getSecondOperand() {
        return secondOperand;
    }

    public String getThirdOperand() {
        return thirdOperand;
    }
}
