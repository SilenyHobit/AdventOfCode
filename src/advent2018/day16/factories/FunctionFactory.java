package advent2018.day16.factories;

import advent2018.day16.Operation;
import vm.VMFunction;

public abstract class FunctionFactory {

    public abstract VMFunction create(String line);

    public abstract boolean matches(Operation op);

    @Override
    public boolean equals(Object o) {
        return getClass().equals(o.getClass());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
