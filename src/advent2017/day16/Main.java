package advent2017.day16;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        String line = Files.readAllLines(Paths.get("inputs/2017/day16")).get(0).trim();

        Dance dance = new Dance();
        List<String> memory = new ArrayList<>();
        memory.add(dance.standing());

        dance.perform(line);

        memory.add(dance.standing());

        System.out.println(dance.standing());

        for (int i = 1; i < 1000000000; i++) {
            dance.perform(line);
            String standing = dance.standing();
            if (memory.get(0).equals(standing)) {
                break;
            }
            memory.add(standing);
        }

        System.out.println(memory.get(1000000000%memory.size()));
    }

}
