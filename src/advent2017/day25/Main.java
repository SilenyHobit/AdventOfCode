package advent2017.day25;

import util.InputLoader;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private static final Pattern number = Pattern.compile("(\\d+)");

    public static void main(String[] args) throws Exception {
        List<String> input = InputLoader.loadInput();

        String state = getState(input.get(0));
        int steps = getNumber(input.get(1));

        Map<String, State> states = new HashMap<>();

        for(int i = 3; i < input.size(); i+=10)
            parseState(input, i, states);

        Turing turing = new Turing(state, states);

        while (steps-- != 0)
            turing.step();

        System.out.println(turing.checksum());
    }

    private static void parseState(List<String> input, int start, Map<String, State> states) {
        String id = getState(input.get(start));
        int setFalse = getNumber(input.get(start+2));
        int moveFalse = input.get(start+3).contains("right") ? 1 : -1;
        String stateFalse = getState(input.get(start+4));
        int setTrue = getNumber(input.get(start+6));
        int moveTrue = input.get(start+7).contains("right") ? 1 : -1;
        String stateTrue = getState(input.get(start+8));

        states.put(id, new State(moveTrue, moveFalse, setTrue, setFalse, stateTrue, stateFalse));
    }

    private static int getNumber(String line) {
        Matcher m = number.matcher(line);
        m.find();
        return Integer.parseInt(m.group(1));
    }

    private static String getState(String line) {
        return line.substring(line.length()-2, line.length()-1);
    }

    private static class Turing {

        private final int[] tape = new int[1000001];
        private int position = 500000;

        private String state;
        private final Map<String, State> states;

        public Turing(String state, Map<String, State> states) {
            this.state = state;
            this.states = states;
        }

        public void step() {
            State current = states.get(state);
            if (tape[position] == 0) {
                tape[position] = current.setFalse;
                position += current.moveFalse;
                state = current.stateFalse;
            } else {
                tape[position] = current.setTrue;
                position += current.moveTrue;
                state = current.stateTrue;
            }
        }

        public int checksum() {
            return Arrays.stream(tape).sum();
        }
    }

    private static class State {
        final int moveTrue;
        final int moveFalse;
        final int setTrue;
        final int setFalse;
        final String stateTrue;
        final String stateFalse;

        private State(int moveTrue, int moveFalse, int setTrue, int setFalse, String stateTrue, String stateFalse) {
            this.moveTrue = moveTrue;
            this.moveFalse = moveFalse;
            this.setTrue = setTrue;
            this.setFalse = setFalse;
            this.stateTrue = stateTrue;
            this.stateFalse = stateFalse;
        }
    }

}
