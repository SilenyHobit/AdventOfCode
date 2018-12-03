package advent2016.day16;

import util.InputLoader;

public class Main {

    private static final int TARGET_1 = 272;
    private static final int TARGET_2 = 35651584;

    public static void main(String[] args) throws Exception {
        String input = InputLoader.loadInput().get(0);

        System.out.println(calculate(input, TARGET_1));
        System.out.println(calculate(input, TARGET_2));
    }

    private static String calculate(String input, int targetSize) {
        while (input.length() < targetSize) {
            input = extend(input);
        }

        input = input.substring(0, targetSize);

        return checksum(input);
    }

    private static String extend(String string) {
        StringBuilder builder = new StringBuilder();

        string.chars()
                .forEach(i -> {
                    if (i == '0')
                        builder.append("1");
                    else if (i == '1')
                        builder.append("0");
                });

        return string + "0" + builder.reverse().toString();
    }

    private static String checksum(String string) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < string.length(); i+=2) {
            if (string.charAt(i) == string.charAt(i+1))
                builder.append("1");
            else
                builder.append("0");
        }

        String result = builder.toString();

        if (result.length() % 2 == 1) {
            return result;
        } else {
            return checksum(result);
        }
    }

}
