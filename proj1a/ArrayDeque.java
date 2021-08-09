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
        if (items[front] == null) {
            return items[front + 1 + index % items.length];
        }
        return items[front + index % items.length];
    }

    public void addFirst(T item) {
        if (end + 1 % items.length == front) {
            resize(items.length * 2);
        }
        front--;
        if (front == -1) {
            front = items.length - 1;
        }
        items[front] = item;
        size++;
    }

    public void addLast(T item) {
        if (end + 1 % items.length == front) {
            resize(items.length * 2);
        }
        items[end] = item;
        end++;
        if (end == items.length) {
            end = 0;
        }
        size++;
    }

    public T removeFirst() {
        if (isEmpty() || items[front] == null) {
            return null;
        }
        T first = items[front];
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
        if (items[end] == null) {
            return null;
        }
        T last = items[end];
        items[end] = null;
        size--;
        if (isShrink()) {
            resize(items.length / 2);
        }
        return last;
    }

    private void resize(int length) {
        if (items[front] == null) {
            front = front + 1 % items.length;
        }
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
