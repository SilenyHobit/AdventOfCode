package advent2016.day7;

import util.InputLoader;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private static final Pattern abba = Pattern.compile("(.)(.)\\2\\1");
    private static final Pattern invalidAbba = Pattern.compile("\\[[^\\]]*(.)(.)\\2\\1[^\\[]*\\]");

    private static final Pattern aba = Pattern.compile("([^\\[\\]])([^\\[\\]])\\1");

    public static void main(String[] args) throws Exception {
        List<String> input = InputLoader.loadInput();

        long validTLSAddresses = input.stream()
                .filter(address -> {
                    Matcher valid = abba.matcher(address);
                    Matcher invalid = invalidAbba.matcher(address);

                    return isAbba(valid) && !isAbba(invalid);
                })
                .count();

        long validSSLAddresses = input.stream()
                .filter(Main::isSSL)
                .count();

        System.out.println(validTLSAddresses);
        System.out.println(validSSLAddresses);
    }

    private static boolean isAbba(Matcher m) {
        int start = 0;
        while (m.find(start)) {
            if (!m.group(1).equals(m.group(2)))
                return true;
            start = m.start(2);
        }

        return false;
    }

    private static boolean isSSL(String address) {
        Matcher m = aba.matcher(address);

        int start = 0;
        while (m.find(start)) {
            String g1 = m.group(1);
            String g2 = m.group(2);

            Pattern validAba = Pattern.compile("(^[^\\[]*" + g1 + g2 + g1 + ")|(\\][^\\[]*" + g1 + g2 + g1 + ")");
            Pattern validBab = Pattern.compile("\\[[^\\]]*" + g2 + g1 + g2 + "[^\\[]*\\]");

            if (!g1.equals(g2) && validAba.matcher(address).find() && validBab.matcher(address).find()) {
                return true;
            }
            start = m.start(2);
        }

        return false;
    }

}
