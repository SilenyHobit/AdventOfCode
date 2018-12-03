package advent2017.day20;

import util.InputLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

    private static final Pattern pattern = Pattern.compile("p=<(-?\\d+),(-?\\d+),(-?\\d+)>, v=<(-?\\d+),(-?\\d+),(-?\\d+)>, a=<(-?\\d+),(-?\\d+),(-?\\d+)>");

    public static void main(String[] args) throws Exception {
        List<String> input = InputLoader.loadInput();

        List<Particle> particles = IntStream.range(0, input.size())
                .mapToObj(i -> {
                    Matcher m = pattern.matcher(input.get(i));
                    m.find();
                    int[] position = {Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)), Integer.parseInt(m.group(3))};
                    int[] velocity = {Integer.parseInt(m.group(4)), Integer.parseInt(m.group(5)), Integer.parseInt(m.group(6))};
                    int[] acceleration = {Integer.parseInt(m.group(7)), Integer.parseInt(m.group(8)), Integer.parseInt(m.group(9))};
                    return new Particle(i, position, velocity, acceleration);
                })
                .collect(Collectors.toList());

        // for simplcity. acceleration can't be compared as this as it's value depends on velocity as well (slowing down or speeding up)
        particles.stream()
                .sorted()
                .forEach(particle -> System.out.println(particle.number + " " + Arrays.toString(particle.position) + " " + Arrays.toString(particle.velocity) + " " + Arrays.toString(particle.acceleration)));

        // can't be bothered with geometry, brute force it is.
        int counter = 100;
        while (counter-- != 0) {
            List<Particle> copy = new ArrayList<>(particles);
            for (int i = 0; i < particles.size(); i++)
                for (int j = i+1; j < particles.size(); j++)
                    if (particles.get(i).equals(particles.get(j))) {
                        copy.remove(particles.get(i));
                        copy.remove(particles.get(j));
                    }

            particles = copy;
            particles.forEach(Particle::tick);
        }

        System.out.println(particles.size());
    }

    private static class Particle implements Comparable<Particle> {

        final int number;
        final int[] position;
        final int[] velocity;
        final int[] acceleration;

        private Particle(int number, int[] position, int[] velocity, int[] acceleration) {
            this.number = number;
            this.position = position;
            this.velocity = velocity;
            this.acceleration = acceleration;
        }

        private int sum (int[] arr) {
            return Math.abs(arr[0])+Math.abs(arr[1])+Math.abs(arr[2]);
        }

        void tick() {
            velocity[0] += acceleration[0];
            velocity[1] += acceleration[1];
            velocity[2] += acceleration[2];

            position[0] += velocity[0];
            position[1] += velocity[1];
            position[2] += velocity[2];
        }

        @Override
        public int compareTo(Particle particle) {
            int result = Integer.compare(sum(acceleration), sum(particle.acceleration));
            if (result == 0) result = Integer.compare(sum(velocity), sum(particle.velocity));
            if (result == 0) result = Integer.compare(sum(position), sum(particle.position));
            return result;
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(position);
        }

        @Override
        public boolean equals(Object o) {
            Particle other = (Particle) o;
            return Arrays.equals(position, other.position);
        }
    }

}
