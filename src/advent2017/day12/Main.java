package advent2017.day12;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {

    private static final Pattern PATTERN = Pattern.compile("([0-9]+) <-> (.*)");

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("inputs/2017/day12"));
        Map<String, List<String>> links = new HashMap<>();

        lines.forEach(line -> {
            Matcher m = PATTERN.matcher(line);
            m.find();
            List<String> list = Arrays.stream(m.group(2).split(",")).map(String::trim).collect(Collectors.toList());
            links.put(m.group(1), list);
        });

        int counter = 0;
        String root = "0";
        while (!links.isEmpty()) {
            counter +=1;
            Set<String> group = getGroup(root, links);

            System.out.println(root + ": " + group.size());

            group.forEach(links::remove);
            if (!links.isEmpty())
                root = links.keySet().stream().findAny().get();
        }

        System.out.println(counter);

    }

    private static Set<String> getGroup(String root, Map<String, List<String>> links) {
        Queue<String> toAnalyze = new LinkedList<>();
        toAnalyze.add(root);
        Set<String> group = new HashSet<>();
        group.add(root);

        while (!toAnalyze.isEmpty()) {
            String program = toAnalyze.poll();
            links.get(program).stream()
                    .filter(link -> !group.contains(link))
                    .forEach(link -> {
                        group.add(link);
                        toAnalyze.add(link);
                    });

        }
        return group;
    }

}
