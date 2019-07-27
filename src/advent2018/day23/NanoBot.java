package advent2018.day23;

import java.util.StringJoiner;

class NanoBot {

    static final NanoBot ZERO = new NanoBot(0,0,0,0);

    private final long x;
    private final long y;
    private final long z;
    private final long range;

    NanoBot(long x, long y, long z, long range) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.range = range;
    }

    public long getRange() {
        return range;
    }

    public long getX() {
        return x;
    }

    public long getY() {
        return y;
    }

    public long getZ() {
        return z;
    }

    NanoBot reduce(long divideBy) {
        return new NanoBot(x/divideBy, y/divideBy, z/divideBy, range/divideBy);
    }

    NanoBot increase(long multiplyBy) {
        return new NanoBot(x*multiplyBy, y*multiplyBy, z*multiplyBy, range*multiplyBy);
    }

    boolean isBotInRange(NanoBot bot) {
        return distanceFrom(bot) <= range;
    }

    long distanceFrom(NanoBot bot) {
        return Math.abs(x - bot.x) + Math.abs(y - bot.y) + Math.abs(z - bot.z);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", NanoBot.class.getSimpleName() + "[", "]")
                .add("x=" + x)
                .add("y=" + y)
                .add("z=" + z)
                .add("range=" + range)
                .toString();
    }
}
