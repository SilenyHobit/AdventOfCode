package advent2015.day15;

import util.InputLoader;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

    private static final Pattern pattern = Pattern.compile("([a-zA-z]+): capacity (-?\\d+), durability (-?\\d+), flavor (-?\\d+), texture (-?\\d+), calories (\\d+)");

    public static void main(String[] args) throws Exception {
        List<Ingredient> ingredients = InputLoader.loadInput().stream()
                .map(line -> {
                    Matcher m = pattern.matcher(line);
                    m.find();
                    return new Ingredient(m.group(1), Long.parseLong(m.group(2)), Long.parseLong(m.group(3)), Long.parseLong(m.group(4)), Long.parseLong(m.group(5)), Long.parseLong(m.group(6)));
                })
                .collect(Collectors.toList());

        AtomicLong result = new AtomicLong(0L);
        calculate(ingredients, 100, new Cookie(ingredients.size()), result, 0, -1);
        System.out.println(result);
        result = new AtomicLong(0L);
        calculate(ingredients, 100, new Cookie(ingredients.size()), result, 0, 500);
        System.out.println(result);
    }

    private static void calculate(List<Ingredient> ingredients, int teaspoons, Cookie cookie, AtomicLong highest, int depth, int caloriesLimit) {
        if (ingredients.size() == 1) {
            cookie.add(ingredients.get(0), teaspoons, depth);
            long value = cookie.value();
            if (highest.get() < value && (caloriesLimit < 0 || cookie.calories() == caloriesLimit))
                highest.set(value);

            return;
        }

        Ingredient ingredient = ingredients.remove(0);
        IntStream.range(0, teaspoons+1)
                .forEach(i -> {
                    cookie.add(ingredient, i, depth);
                    calculate(ingredients, teaspoons-i, cookie, highest, depth+1, caloriesLimit);
                });
        ingredients.add(0, ingredient);
    }

    private static class Ingredient {
        private final String name;
        private final long capacity;
        private final long durability;
        private final long flavor;
        private final long texture;
        private final long calories;

        private Ingredient(String name, long capacity, long durability, long flavor, long texture, long calories) {
            this.name = name;
            this.capacity = capacity;
            this.durability = durability;
            this.flavor = flavor;
            this.texture = texture;
            this.calories = calories;
        }
    }

    private static class Cookie {
        private final Ingredient[] ingredients;
        private final int[] teaspoons;

        public Cookie(int ingredientCount) {
            ingredients = new Ingredient[ingredientCount];
            teaspoons = new int[ingredientCount];
        }

        void add(Ingredient ingredient, int teaspoons, int index) {
            ingredients[index] = ingredient;
            this.teaspoons[index] = teaspoons;
        }

        long value() {
            long capacity = 0;
            long durability = 0;
            long flavor = 0;
            long texture = 0;

            for (int i = 0; i < ingredients.length; i++) {
                capacity += ingredients[i].capacity * teaspoons[i];
                durability += ingredients[i].durability * teaspoons[i];
                flavor += ingredients[i].flavor * teaspoons[i];
                texture += ingredients[i].texture * teaspoons[i];
            }

            return Math.max(capacity, 0) * Math.max(durability, 0) * Math.max(flavor, 0) * Math.max(texture, 0);
        }

        long calories() {
            return IntStream.range(0, ingredients.length)
                    .mapToLong(i -> ingredients[i].calories*teaspoons[i])
                    .sum();
        }
    }

}
