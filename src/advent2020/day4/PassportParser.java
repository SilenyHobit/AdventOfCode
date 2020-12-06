package advent2020.day4;

import java.util.ArrayList;
import java.util.List;

class PassportParser {

    private Passport.Builder builder = Passport.builder();
    private final List<Passport> passports = new ArrayList<>();

    PassportParser nextLine(String line) {
        if (line.isEmpty()) {
            passports.add(builder.build());
            builder = Passport.builder();
        }

        builder = builder.addLine(line);
        return this;
    }

    List<Passport> finish() {
        passports.add(builder.build());
        builder = Passport.builder();
        return new ArrayList<>(passports);
    }

}
