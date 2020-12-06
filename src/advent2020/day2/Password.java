package advent2020.day2;

class Password {

    private final int min;
    private final int max;
    private final char required;
    private final String password;

    Password(int min, int max, String required, String password) {
        this.min = min;
        this.max = max;
        this.required = required.charAt(0);
        this.password = password;
    }

    boolean validate() {
        int counter = 0;
        int index = -1;
        while ((index = password.indexOf(required, index + 1)) != -1) {
            counter++;
        }
        return min <= counter && counter <= max;
    }

    boolean validate2() {
        boolean valid = false;
        if (min <= password.length() && password.charAt(min - 1) == required)
            valid = true;

        if (max <= password.length() && password.charAt(max - 1) == required)
            valid = !valid;

        return valid;
    }

}
