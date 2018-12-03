package advent2017.day17;

import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final int INPUT = 369;

    public static void main(String[] args) {
        List<Integer> buffer = new ArrayList<>();
        buffer.add(0);

        int index = 0;

        for (int i = 1; i <= 2017; i++) {
            index = (index + INPUT) % buffer.size();
            index +=1;
            buffer.add(index, i);
        }

        System.out.println(buffer.get(index+1));

        index = 0;
        int afterZero = 0;

        for (int i = 1; i <= 50000000; i++) {
            index = (index + INPUT) % i;
            index +=1;
            if (index == 1) {
                afterZero = i;
            }
        }

        System.out.println(afterZero);
    }

}
