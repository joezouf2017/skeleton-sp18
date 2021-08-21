import static org.junit.Assert.*;
import org.junit.Test;
public class TestArrayDequeGold {
    @Test
    public void sadtest() {
        StudentArrayDeque<Integer> d1 = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> d2 = new ArrayDequeSolution<>();
        String message = "\n";
        for (int i = 0; i < 10; i++) {
            double n = StdRandom.uniform();
            if (n < 0.5) {
                d1.addFirst(i);
                d2.addFirst(i);
                message += "addFirst(" + i + ")\n";
            } else {
                d1.addLast(i);
                d2.addLast(i);
                message += "addLast(" + i + ")\n";
            }
        }
        for (int i = 0; i < 10; i++) {
            double n = StdRandom.uniform();
            if (n < 0.5) {
                Integer sad1 = d1.removeFirst();
                Integer sad2 = d2.removeFirst();
                assertEquals(message + "removeFirst()", sad2, sad1);
                message += "removeFirst()\n";
            } else {
                Integer sad1 = d1.removeLast();
                Integer sad2 = d2.removeLast();
                assertEquals(message + "removeLast()", sad2, sad1);
                message += "removeLast()\n";
            }
        }
    }
}
