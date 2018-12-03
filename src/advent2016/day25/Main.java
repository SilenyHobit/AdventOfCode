package advent2016.day25;

public class Main {

    public static void main(String[] args) {
        // analyze the assembunny code first!
        int a = 0;
        outer: while (true) {
            a+=2;
            int base = 2572 + a;

            int remainder = 1;
            while (base != 0) {
                int nextRem = base % 2;
                base /= 2;
                if (nextRem != remainder)
                    remainder = nextRem;
                else
                    continue outer;
            }

            System.out.println(a);
            break;
        }

    }

}
