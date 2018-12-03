package util;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Conversion<T> {

    private final Pattern pattern;
    private final Function<Matcher, T> producer;

    public Conversion(Pattern pattern, Function<Matcher, T> producer) {
        this.pattern = pattern;
        this.producer = producer;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public Function<Matcher, T> getProducer() {
        return producer;
    }
}
