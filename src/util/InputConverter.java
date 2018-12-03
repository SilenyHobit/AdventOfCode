package util;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputConverter<T> {

    private final List<Conversion<T>> conversions;

    public InputConverter(List<Conversion<T>> conversions) {
        this.conversions = conversions;
    }

    public T convert(String line) {
        return conversions.stream()
                .map(conversion -> tryConvert(line, conversion.getPattern(), conversion.getProducer()))
                .filter(Optional::isPresent)
                .findFirst()
                .map(Optional::get)
                .orElseThrow(() -> new IllegalArgumentException(line));
    }

    private Optional<T> tryConvert(String line, Pattern pattern, Function<Matcher, T> producer) {
        return Optional.of(line)
                .map(pattern::matcher)
                .filter(Matcher::find)
                .map(producer);
    }

}
