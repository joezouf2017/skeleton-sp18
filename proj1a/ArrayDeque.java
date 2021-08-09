public class ArrayDeque<T> {
    private int size;
    private T[] items;
    private int front;
    private int end;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        front = 4;
        end = 4;
    }

    public boolean isEmpty() {
        return size == 0 && front == end;
    }

    public int size() {
        return size;
    }

    public T get(int index) {
        if (isEmpty() || index >= size) {
            return null;
        }
        return items[index];
    }

    public void addFirst(T item) {
        front--;
        if (front == -1) {
            front = items.length - 1;
        }
        items[front] = item;
        size++;
        if (front == end) {
            resize(items.length * 2);
        }
    }

    public void addLast(T item) {
        items[end] = item;
        end++;
        if (end == items.length) {
            end = 0;
        }
        size++;
        if (end == front) {
            resize(items.length * 2);
        }
    }

    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        T first = (T) items[front];
        if (first == null) {
            return null;
        }
        items[front] = null;
        size--;
        front++;
        if (front == items.length) {
            front = 0;
        }
        if (isShrink()) {
            resize(items.length / 2);
        }
        return first;
    }

    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        end--;
        if (end == -1) {
            end = items.length - 1;
        }
        T last = (T) items[end];
        if (last == null) {
            return null;
        }
        items[end] = null;
        size--;
        if (isShrink()) {
            resize(items.length / 2);
        }
        return last;
    }

    private void resize(int length) {
        T[] newitems = (T[]) new Object[length];
        int index = 0;
        for (int i = front; i != end; i = (i + 1) % items.length) {
            newitems[index++] = items[i];
        }
        front = 0;
        end = index;
        items = newitems;
    }

    private boolean isShrink() {
        return size < items.length / 4 && items.length > 16;
    }

    public void printDeque() {
        for (T i : items) {
            System.out.print(i + " ");
        }
    }
}
