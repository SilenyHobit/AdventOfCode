package advent2020.day4;

public class PassportAggregator {

    private int part1Counter;
    private int part2Counter;

    PassportAggregator nextPassport(Passport passport) {
        if (passport.part1Valid())
            part1Counter++;
        if (passport.part2Valid())
            part2Counter++;
        return this;
    }

    int part1() {
        return part1Counter;
    }

    int part2() {
        return part2Counter;
    }

}
