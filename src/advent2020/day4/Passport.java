package advent2020.day4;

import java.util.function.Consumer;
import java.util.regex.Pattern;

public class Passport {

    private static final Pattern BYR_VALIDATION = Pattern.compile("(19[2-9][0-9])|2000|2001|2002");
    private static final Pattern IYR_VALIDATION = Pattern.compile("(201[0-9])|2020");
    private static final Pattern EYR_VALIDATION = Pattern.compile("(202[0-9])|2030");
    private static final Pattern HGT_VALIDATION = Pattern.compile("(1[5-8][0-9]|19[0-3])cm|(59|6[0-9]|7[0-6])in");
    private static final Pattern HCL_VALIDATION = Pattern.compile("#[0-9a-f]{6}");
    private static final Pattern ECL_VALIDATION = Pattern.compile("amb|blu|brn|gry|grn|hzl|oth");
    private static final Pattern PID_VALIDATION = Pattern.compile("\\d{9}");

    private final String byr;
    private final String iyr;
    private final String eyr;
    private final String hgt;
    private final String hcl;
    private final String ecl;
    private final String pid;
    private final String cid;

    private final boolean part1Valid;
    private final boolean part2Valid;

    private Passport(String byr, String iyr, String eyr, String hgt, String hcl, String ecl, String pid, String cid) {
        this.byr = byr;
        this.iyr = iyr;
        this.eyr = eyr;
        this.hgt = hgt;
        this.hcl = hcl;
        this.ecl = ecl;
        this.pid = pid;
        this.cid = cid;

        part1Valid = validate();
        part2Valid = validate2();
    }

    private boolean validate() {
        return byr != null
                && iyr != null
                && eyr != null
                && hgt != null
                && hcl != null
                && ecl != null
                && pid != null;
    }

    private boolean validate2() {
        return part1Valid
                && BYR_VALIDATION.matcher(byr).matches()
                && IYR_VALIDATION.matcher(iyr).matches()
                && EYR_VALIDATION.matcher(eyr).matches()
                && HGT_VALIDATION.matcher(hgt).matches()
                && HCL_VALIDATION.matcher(hcl).matches()
                && ECL_VALIDATION.matcher(ecl).matches()
                && PID_VALIDATION.matcher(pid).matches();
    }

    public boolean part1Valid() {
        return part1Valid;
    }

    public boolean part2Valid() {
        return part2Valid;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private static final Pattern BYR_PATTERN = Pattern.compile("byr:([^ ]+)");
        private static final Pattern IYR_PATTERN = Pattern.compile("iyr:([^ ]+)");
        private static final Pattern EYR_PATTERN = Pattern.compile("eyr:([^ ]+)");
        private static final Pattern HGT_PATTERN = Pattern.compile("hgt:([^ ]+)");
        private static final Pattern HCL_PATTERN = Pattern.compile("hcl:([^ ]+)");
        private static final Pattern ECL_PATTERN = Pattern.compile("ecl:([^ ]+)");
        private static final Pattern PID_PATTERN = Pattern.compile("pid:([^ ]+)");
        private static final Pattern CID_PATTERN = Pattern.compile("cid:([^ ]+)");

        private String byr;
        private String iyr;
        private String eyr;
        private String hgt;
        private String hcl;
        private String ecl;
        private String pid;
        private String cid;

        private Builder() {
        }

        public Builder addLine(String line) {
            setValue(line, BYR_PATTERN, value -> byr = value);
            setValue(line, IYR_PATTERN, value -> iyr = value);
            setValue(line, EYR_PATTERN, value -> eyr = value);
            setValue(line, HGT_PATTERN, value -> hgt = value);
            setValue(line, HCL_PATTERN, value -> hcl = value);
            setValue(line, ECL_PATTERN, value -> ecl = value);
            setValue(line, PID_PATTERN, value -> pid = value);
            setValue(line, CID_PATTERN, value -> cid = value);
            return this;
        }

        private void setValue(String line, Pattern pattern, Consumer<String> consumer) {
            var matcher = pattern.matcher(line);
            if (matcher.find())
                consumer.accept(matcher.group(1));
        }

        public Passport build() {
            return new Passport(byr, iyr, eyr, hgt, hcl, ecl, pid, cid);
        }

    }

}
