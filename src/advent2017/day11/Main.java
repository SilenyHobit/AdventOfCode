package advent2017.day11;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws IOException {
        String line = Files.readAllLines(Paths.get("inputs/2017/day11")).get(0).trim();
        Agent agent = new Agent();

        Arrays.stream(line.split(","))
                .forEach(agent::perform);

        System.out.println(agent.steps());
        System.out.println(agent.max);
    }

    private static class Agent {
        int x = 0;
        int y = 0;

        int max = 0;

        void perform(String command) {
            switch (command) {
                case "n":
                    n();
                    break;
                case "s":
                    s();
                    break;
                case "ne":
                    ne();
                    break;
                case "nw":
                    nw();
                    break;
                case "se":
                    se();
                    break;
                case "sw":
                    sw();
                    break;
                default:
                    throw new IllegalArgumentException();
            }

            int steps = steps();
            if (steps > max)
                max = steps;
        }

        void n() {
            y+=2;
        }

        void s() {
            y-=2;
        }

        void ne() {
            y+=1;
            x+=1;
        }

        void nw() {
            y+=1;
            x-=1;
        }

        void se() {
            y-=1;
            x+=1;
        }

        void sw() {
            y-=1;
            x-=1;
        }

        int steps() {
            int horizontal = Math.abs(x);
            int vertical = Math.max(Math.abs(y) - Math.abs(x), 0)/2;
            return horizontal + vertical;
        }
    }

}
