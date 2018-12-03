package advent2017.day15;

public class Main {

    private static final long A_INIT = 783L;
    private static final long A_MULTIPLIER = 16807L;
    private static final long B_INIT = 325L;
    private static final long B_MULTIPLIER = 48271L;

    private static final long MASK = 0b1111111111111111;

    public static void main(String[] args) {
        Generator a = new Generator(A_MULTIPLIER, A_INIT);
        Generator b = new Generator(B_MULTIPLIER, B_INIT);

        int counter = 0;
        for (int i = 0; i < 40000000; i++) {
            long l1 = a.nextValue();
            long l2 = b.nextValue();
            if (compare(l1, l2))
                counter++;
        }

        System.out.println(counter);

        ImprovedGenerator ia = new ImprovedGenerator(A_MULTIPLIER, A_INIT, 4L);
        ImprovedGenerator ib = new ImprovedGenerator(B_MULTIPLIER, B_INIT, 8L);

        counter = 0;
        for (int i = 0; i < 5000000; i++) {
            long l1 = ia.nextValue();
            long l2 = ib.nextValue();
            if (compare(l1, l2))
                counter++;
        }

        System.out.println(counter);
    }

    private static boolean compare(long l1, long l2) {
        long masked1 = l1 & MASK;
        long masked2 = l2 & MASK;

        return masked1 == masked2;
    }

    private static class Generator {
        private final long multiplier;
        private long lastValue;

        private Generator(long multiplier, long lastValue) {
            this.multiplier = multiplier;
            this.lastValue = lastValue;
        }

        long nextValue() {
            lastValue = (lastValue*multiplier)%2147483647L;
            return lastValue;
        }
    }

    private static class ImprovedGenerator {
        private final long multiplier;
        private final long modulo;
        private long lastValue;

        private ImprovedGenerator(long multiplier, long lastValue, long modulo) {
            this.multiplier = multiplier;
            this.modulo = modulo;
            this.lastValue = lastValue;
        }

        long nextValue() {
            do {
                lastValue = (lastValue * multiplier) % 2147483647L;
            } while ((lastValue%modulo) != 0);
            return lastValue;
        }
    }

}
