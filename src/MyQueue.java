import java.util.*;

public class MyQueue<T> {

    private ArrayList<T> qList = new ArrayList<>();
    int size = 0;

    public MyQueue() {}

    public void enqueue(T item) {
        qList.add(item);
        size++;
    }

    public T dequeue() throws Exception {
        if (empty())
            throw new Exception("Queue is empty!");

        size--;
        return qList.remove(0);
    }

    public boolean empty() {
        return size == 0;
    }

    public int size() {
        return size;
    }
}


