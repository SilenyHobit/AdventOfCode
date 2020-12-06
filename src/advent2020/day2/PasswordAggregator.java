package advent2020.day2;

public class PasswordAggregator {

    private int task1Valid;
    private int task2Valid;

    public PasswordAggregator next(Password password) {
        if (password.validate())
            task1Valid++;
        if (password.validate2())
            task2Valid++;
        return this;
    }

    int task1() {
        return task1Valid;
    }

    int task2() {
        return task2Valid;
    }

}
