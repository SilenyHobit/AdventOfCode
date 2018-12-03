package advent2016.day19;

import util.InputLoader;

public class Main {

    public static void main(String[] args) throws Exception {
        String input = InputLoader.loadInput().get(0);
        int elfs = Integer.parseInt(input);

        int closest = 2;
        while (closest <= elfs) {
            closest *= 2;
        }
        closest /= 2;

        int diff = elfs-closest;

        System.out.println(diff * 2 + 1);

        int i = 1;

        while (i*3 < elfs)
            i*=3;

        System.out.println(elfs - i + Math.max(elfs-2*i, 0));


    }

}
