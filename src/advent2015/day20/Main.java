package advent2015.day20;

import util.InputLoader;

public class Main {

    public static void main(String[] args) throws Exception {
        int input = Integer.parseInt(InputLoader.loadInput().get(0));

        int[] houses = new int[1000000];
        int[] houses2 = new int[1000000];

        for (int elf = 1; elf < 1000000; elf++) {
            for (int house = elf; house < 1000000; house+=elf) {
                houses[house] += elf;

                if (house <= elf*50)
                    houses2[house] += elf;
            }
        }

        boolean first = true;
        boolean second = true;

        for (int i = 0; i < houses.length; i++) {
            if (first && houses[i]*10 > input) {
                System.out.println(i);
                first = false;
            }
            if (second && houses2[i]*11 > input) {
                System.out.println(i);
                second = false;
            }
        }

    }

}
