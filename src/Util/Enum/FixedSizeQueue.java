package Util.Enum;

import java.util.LinkedList;

/**
 * Вспоомгательный класс, расширяющий возможности очереди,
 * при добавлении в которую заменяется самый старый добавленный элемент, если
 * очередь заполнена
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
            // Если очередь заполнена, удаляем самый старый элемент
            queue.removeFirst();
        }
        // Добавляем новый элемент в конец очереди
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
