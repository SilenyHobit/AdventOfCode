package advent2016.day13;

import util.InputLoader;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class Main {

    private static final Pair target = new Pair(31, 39, 0);

    private static final Set<Pair> visited = new HashSet<>();
    private static final Queue<Pair> queue = new LinkedList<>();
    private static int favoriteNumber;

    public static void main(String[] args) throws Exception {
        String input = InputLoader.loadInput().get(0);
        favoriteNumber = Integer.parseInt(input);

        Pair current = new Pair(1,1, 0);
        visited.add(current);

        while (!current.equals(target)) {
            addPair(current.x-1, current.y, current.depth+1);
            addPair(current.x+1, current.y, current.depth+1);
            addPair(current.x, current.y-1, current.depth+1);
            addPair(current.x, current.y+1, current.depth+1);

            current = queue.poll();
        }

        System.out.println(current.depth);

        System.out.println(visited.stream().filter(Main::isOpen).filter(pair -> pair.depth <= 50).count());
    }

    private static void addPair(long x, long y, int depth) {
        Pair pair = new Pair(x, y, depth);
        if(!visited.contains(pair) && isOpen(pair)) {
            queue.add(pair);
            visited.add(pair);
        }
    }

    private static boolean isOpen(Pair pair) {
        if (pair.x<0 || pair.y<0)
            return false;

        long res = pair.x*pair.x + 3*pair.x + 2*pair.x*pair.y + pair.y + pair.y*pair.y + favoriteNumber;
        return Long.bitCount(res)%2==0;
    }

    private static class Pair {
        private final long x;
        private final long y;
        private final int depth;

        private Pair(long x, long y, int depth) {
            this.x = x;
            this.y = y;
            this.depth = depth;
        }

        @Override
        public int hashCode() {
            return (int) (x+y);
        }

        @Override
        public boolean equals(Object o) {
            Pair other = (Pair) o;
            return x == other.x && y == other.y;
        }
    }

}
