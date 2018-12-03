package advent2016.day9;

import util.InputLoader;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private static final Pattern p = Pattern.compile("\\((\\d+)x(\\d+)\\)");

    public static void main(String[] args) throws Exception {
        String input = InputLoader.loadInput().get(0);

        int result = 0;
        Matcher m = p.matcher(input);

        int start = 0;
        while(m.find(start)) {
            int groupStart = m.start();
            int groupEnd = m.end();
            if (groupStart > start)
                result+= groupStart - start;

            int length = Integer.parseInt(m.group(1));
            int count = Integer.parseInt(m.group(2));

            result += length * count;

            start = groupEnd + length;
        }

        if (start < input.length())
            result += input.length() - start;

        System.out.println(result);

        System.out.println(calculateGroup(input));
    }

    private static long calculateGroup(String group) {
        long result = 0L;
        Matcher m = p.matcher(group);
        int start = 0;
        while(m.find(start)) {
            int groupStart = m.start();
            int groupEnd = m.end();
            if (groupStart > start)
                result+= groupStart - start;

            int length = Integer.parseInt(m.group(1));
            int count = Integer.parseInt(m.group(2));

            String subGroup = group.substring(groupEnd, groupEnd + length);

            result += calculateGroup(subGroup) * count;

            start = groupEnd + length;
        }

        if (start < group.length())
            result += group.length() - start;

        return result;
    }

}
