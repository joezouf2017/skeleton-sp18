public class LinkedListDeque<T> {
    private int size;
    private ListNode sentinel = new ListNode(null, null, null);

    public class ListNode {
        private T item;
        private ListNode next;
        private ListNode prev;
        public ListNode(T i, ListNode p, ListNode n) {
            item = i;
            next = n;
            prev = p;
        }
    }

    public LinkedListDeque(T x) {
        ListNode first = new ListNode(x, sentinel, sentinel);
        size = 1;
        sentinel.next = first;
        sentinel.prev = first;
    }

    public LinkedListDeque() {
        size = 0;
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
    }

    public boolean isEmpty() {
        return sentinel.next == sentinel;
    }

    public int size() {
        return size;
    }

    public void addFirst(T item) {
        sentinel.next = new ListNode(item, sentinel, sentinel.next);
        sentinel.next.next.prev = sentinel.next;
        size++;
    }

    public void addLast(T item) {
        sentinel.prev = new ListNode(item, sentinel.prev, sentinel);
        sentinel.prev.prev.next = sentinel.prev;
        size++;
    }

    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        T first = sentinel.next.item;
        sentinel.next.next.prev = sentinel;
        sentinel.next = sentinel.next.next;
        size--;
        return first;
    }

    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        T last = sentinel.prev.item;
        sentinel.prev.prev.next = sentinel;
        sentinel.prev = sentinel.prev.prev;
        size--;
        return last;
    }

    public T get(int index) {
        if (isEmpty() || index >= size) {
            return null;
        }
        ListNode temp = sentinel.next;
        while (index > 0) {
            temp = temp.next;
            index--;
        }
        return temp.item;
    }

    public T getRecursive(int index) {
        if (isEmpty() || index >= size) {
            return null;
        }
        ListNode temp = sentinel.next;
        if (index == 0) {
            return temp.item;
        }
        return getRecursivehelper(index - 1, temp.next);
    }

    public T getRecursivehelper(int index, ListNode l) {
        if (index == 0) {
            return l.item;
        }
        return getRecursivehelper(index - 1, l.next);
    }

    public void printDeque() {
        ListNode temp = sentinel.next;
        while (temp != sentinel) {
            System.out.print(temp.item);
            temp = temp.next;
        }
    }
}
