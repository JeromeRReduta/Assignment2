public class EdgeList<T> {
    private Edge head;
    private int size = 0;

    public EdgeList() {}

    public void link (int id) {
        if (head == null) {
            head = new Edge(id);
            size++;
        }

        Edge current = head, prev = head;

        while (current != null) {
            if (current.getId() == id)
                return;

            prev = current;
            current = (Edge)current.getNext();
        }

        prev.setNext(new Edge(id));
        size++;
    }

    public Edge get(int i) throws Exception {
        if (i < 0 || i >= size)
            throw new Exception("Out of bounds!");

        int index = 0;
        Edge current = head;

        while (index != i) {
            current = (Edge)current.getNext();
            i++;
        }

        return current;
    }

    public int size() {
        return size;
    }
}
