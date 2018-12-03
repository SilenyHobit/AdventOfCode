package advent2017.day4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("inputs/2017/day4"));
        int counter = 0, counter2 = 0;

        topCycle: for (String line : lines) {
            Set<String> words = new HashSet<>();
            for (String word : line.split(" ")) {
                if (words.contains(word))
                    continue topCycle;
                words.add(word);
            }
            counter++;
        }

        topCycle: for (String line : lines) {
            String[] words = line.split(" ");
            for (int i = 0; i < words.length; i++) {
                String word1 = words[i];
                for (int j = i+1; j < words.length; j++) {
                    String word2 = words[j];
                    if (word1.length() == word2.length()) {
                        for (char c : word1.toCharArray())
                            word2 = word2.replaceFirst("" + c, "");
                        if (word2.isEmpty())
                            continue topCycle;
                    }
                }
            }
            counter2++;
        }

        System.out.println("First: " + counter);
        System.out.println("Second: " + counter2);
    }

}
