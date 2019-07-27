package advent2018.day22;

class Node {

    private final int x;
    private final int y;
    private final int tool;
    private final int time;

    Node(int x, int y, int tool, int time) {
        this.x = x;
        this.y = y;
        this.tool = tool;
        this.time = time;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getTool() {
        return tool;
    }

    public int getTime() {
        return time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        if (x != node.x) return false;
        if (y != node.y) return false;
        return tool == node.tool;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        result = 31 * result + tool;
        return result;
    }
}
