package advent2016.day5;

import util.InputLoader;

import java.security.MessageDigest;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws Exception {
        Instant start = Instant.now();
        String input = InputLoader.loadInput().get(0);

        String[] password1 = new String[8];
        String[] password2 = new String[8];
        long counter = 0L;
        int passCounter1 = 0;
        int passCounter2 = 0;

        while (passCounter1 < 8 || passCounter2 < 8) {
            byte[] hash = toHash(input + counter++);

            if (hash[0] == 0 && hash[1] == 0 && (hash[2]&0xff) <= 0x0f) {
                int thirdByte = hash[2]&0xff;

                if (passCounter1 < 8)
                    password1[passCounter1++] = Integer.toHexString(thirdByte);

                if (thirdByte < 8 && password2[thirdByte] == null) {
                    int fourthByte = hash[3]&0xff;
                    password2[thirdByte] = Integer.toHexString(fourthByte).substring(0, 1);
                    passCounter2++;
                }

            }
        }

        System.out.println(Arrays.stream(password1).collect(Collectors.joining()));
        System.out.println(Arrays.stream(password2).collect(Collectors.joining()));

        System.out.println(Duration.between(start, Instant.now()));
    }

    private static byte[] toHash(String str) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        return md.digest(str.getBytes("UTF-8"));
    }

}
