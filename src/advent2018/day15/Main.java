package advent2018.day15;

import util.InputLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) throws Exception {
        System.out.println(part1());
        System.out.println(part2());
    }

    private static int part1() throws Exception {
        Field field = buildField(3);
        field.fight();
        return field.result();
    }

    private static int part2() throws Exception {
        int counter = 4;
        while (true) {
            Field field = buildField(counter);
            long elves = field.countElves();
            field.fight();
            if (elves == field.countElves())
                return field.result();

            counter++;
        }
    }

    private static Field buildField(int elfAp) throws Exception {
        int[][] input = InputLoader.loadInput().stream()
                .map(String::chars)
                .map(IntStream::toArray)
                .toArray(int[][]::new);

        List<Fighter> fighters = new ArrayList<>();
        for (int i = 0; i < input.length; i++)
            for (int j = 0; j < input[i].length; j++) {
                if (input[i][j] == 'E') {
                    fighters.add(new Elf(new Node(j, i, null, 0), elfAp));
                    input[i][j] = '.';
                } else if (input[i][j] == 'G') {
                    fighters.add(new Goblin(new Node(j, i, null, 0)));
                    input[i][j] = '.';
                }
            }

        return new Field(fighters, input);
    }

}
