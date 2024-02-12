package App.Util.Enum;

import java.util.LinkedList;

/**
*An auxiliary class that expands the capabilities of the queue,
*when adding to which the oldest added element is replaced if
*queue is full
*/
public class FixedSizeQueue<T> {
    private final int capacity;
    private final LinkedList<T> queue = new LinkedList<>();

    public FixedSizeQueue(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity should be greater than zero");
        }
        this.capacity = capacity;
    }

    public void add(T element) {
        if (queue.size() >= capacity) {
            //If the queue is full, remove the oldest element
            queue.removeFirst();
        }
        //Adding a new element to the end of the queue
        queue.addLast(element);
    }

    public LinkedList<T> getQueue() {
        return queue;
    }

    public void clear() {
        queue.clear();
    }

    public boolean contain(T obj) {
        return queue.contains(obj);
    }
}
