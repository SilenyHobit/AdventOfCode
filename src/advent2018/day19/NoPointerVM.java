package advent2018.day19;

import vm.GeneralVM;
import vm.VMFunction;

import java.util.List;

public class NoPointerVM extends GeneralVM {

    private final String pointerAdress;

    public NoPointerVM(List<VMFunction> program, String pointerAddress) {
        super(program);
        this.pointerAdress = pointerAddress;
    }

    @Override
    public long get(String register) {
        if (pointerAdress.equals(register))
            return position.get();

        return super.get(register);
    }

    @Override
    public long set(String register, long value) {
        if (pointerAdress.equals(register)) {
            position.set((int) value);
        }

        return super.set(register, value);
    }
}
