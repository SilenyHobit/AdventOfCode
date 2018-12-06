package util;

import java.util.Optional;

public class Printer {

    public static <T, U> U print(T value, U returnValue) {
        return Optional.of(value)
                .map(v -> System.out.printf("%s%n", v))
                .map(w -> returnValue)
                .orElseThrow(RuntimeException::new);
    }

}
