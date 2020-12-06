package advent2016.day14;

import util.InputLoader;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private static final Pattern triplet = Pattern.compile("(.)\\1\\1");

    public static void main(String[] args) throws Exception {
        String input = InputLoader.loadInput().get(0);

        System.out.println(getLastIndex(input, 1));
        System.out.println(getLastIndex(input, 2017));
    }

    private static int getLastIndex(String input, int hashCount) throws Exception {
        int counter = 0;
        List<Candidate> found = new ArrayList<>();

        List<Candidate> candidates = new ArrayList<>();

        while (true) {
            String hash = hash(input + counter, hashCount);

            Iterator<Candidate> candidateIterator = candidates.iterator();
            while (candidateIterator.hasNext()) {
                Candidate candidate = candidateIterator.next();
                if (candidate.index + 1000 < counter) {
                    candidateIterator.remove();
                    continue;
                }

                Matcher m = candidate.pattern.matcher(hash);
                if (m.find()) {
                    candidateIterator.remove();
                    found.add(candidate);
                }
            }

            if (found.size() < 64) {
                Matcher m = triplet.matcher(hash);

                if (m.find()) {
                    String letter = m.group(1);
                    candidates.add(new Candidate(counter, Pattern.compile(letter + letter + letter + letter + letter)));
                }
            } else if (candidates.isEmpty())
                break;

            counter++;
        }

        return found.stream()
                .sorted(Comparator.comparing(candidate -> candidate.index))
                .map(candidate -> candidate.index)
                .skip(63)
                .findFirst()
                .get();
    }

    private static String hash(String original, int times) throws Exception {
        String resultString = original;
        while (times-- != 0) {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] result = digest.digest(resultString.getBytes());
            BigInteger bigInteger = new BigInteger(1, result);
            resultString = String.format(
                    "%0" + (result.length << 1) + "x", bigInteger);
        }

        return resultString;
    }

    private static class Candidate {
        private final int index;
        private final Pattern pattern;

        private Candidate(int index, Pattern pattern) {
            this.index = index;
            this.pattern = pattern;
        }
    }

}
