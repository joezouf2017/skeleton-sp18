package synthesizer;
import java.util.Iterator;

public interface BoundedQueue<T> extends Iterable<T> {
    /** Returns the size of the buffer. */
    int capacity();

    /** Returns the number of items currently in the buffer. */
    int fillCount();

    /** Adds item x to the end. */
    void enqueue(T x);

    /** Deletes and returns the item at the front. */
    T dequeue();

    /** Returns the item at the front without deleting it. */
    T peek();

    /** Returns true if the buffer is empty. */
    default boolean isEmpty() {
        return fillCount() == 0;
    }

    /** Returns true if the buffer is full. */
    default boolean isFull() {
        return capacity() == fillCount();
    }

    /** Returns an iterator of the buffer. */
    Iterator<T> iterator();
}
