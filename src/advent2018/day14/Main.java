package advent2018.day14;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    private static int input = 652601;
    private static List<Integer> expected = Arrays.asList(6,5,2,6,0,1);

    public static void main(String[] args) {
        int firstIndex = 0;
        int secondIndex = 1;
        List<Integer> recipes = new ArrayList<>(1000000);
        recipes.add(3);
        recipes.add(7);

        while (true) {
            int sum = recipes.get(firstIndex) + recipes.get(secondIndex);

            int tmp = sum % 10;
            if (sum >= 10)
                recipes.add(sum/10);
            recipes.add(tmp);
            if (recipes.size() > input + 10)
                break;

            firstIndex = (1+recipes.get(firstIndex) + firstIndex) % recipes.size();
            secondIndex = (1+recipes.get(secondIndex) + secondIndex) % recipes.size();
        }

        String result = recipes.stream().skip(input).limit(10L).map(Object::toString).collect(Collectors.joining());
        System.out.println(result);

        firstIndex = 0;
        secondIndex = 1;
        recipes = new ArrayList<>(1000000);
        recipes.add(3);
        recipes.add(7);

        while (true) {
            int sum = recipes.get(firstIndex) + recipes.get(secondIndex);

            int tmp = sum % 10;
            if (sum >= 10) {
                recipes.add(sum / 10);
                if (isResult(recipes))
                    break;
            }
            recipes.add(tmp);
            if (isResult(recipes))
                break;

            firstIndex = (1+recipes.get(firstIndex) + firstIndex) % recipes.size();
            secondIndex = (1+recipes.get(secondIndex) + secondIndex) % recipes.size();
        }

        System.out.println(recipes.size() - expected.size());
    }

    private static boolean isResult(List<Integer> recipes) {
        if (recipes.size() < expected.size())
            return false;
        for (int i = 0; i < expected.size(); i++) {
            if (!recipes.get((recipes.size()-1)-i).equals(expected.get((expected.size()-1)-i)))
                return false;
        }

        return true;
    }

}
