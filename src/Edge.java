public class Edge extends Node {
    private int id;

    public Edge(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String toString() {
        return this.id + "";
    }
}
