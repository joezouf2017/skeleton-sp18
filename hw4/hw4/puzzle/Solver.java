package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;
import java.util.Comparator;
import java.util.LinkedList;

public class Solver {
    private int moves = 0;
    private LinkedList<WorldState> solution;

    class SearchNode {
        private WorldState current;
        private int move;
        private SearchNode previous;
        private int distance;

        public SearchNode(WorldState cur, int m, SearchNode pre) {
            current = cur;
            move = m;
            previous = pre;
            distance = cur.estimatedDistanceToGoal();
        }
    }

    class SearchNodecomparator implements Comparator<SearchNode> {
        @Override
        public int compare(SearchNode A, SearchNode B) {
            return A.distance + A.move - B.distance - B.move;
        }
    }

    public Solver(WorldState initial) {
        MinPQ<SearchNode> queue = new MinPQ<>(new SearchNodecomparator());
        queue.insert(new SearchNode(initial, 0, null));
        SearchNode result = queue.delMin();
        solution = new LinkedList<>();
        while (!result.current.isGoal()) {
            for (WorldState neighbor : result.current.neighbors()) {
                if (result.previous == null) {
                    queue.insert(new SearchNode(neighbor, result.move + 1, result));
                } else if (!neighbor.equals(result.previous.current)) {
                    queue.insert(new SearchNode(neighbor, result.move + 1, result));
                }
            }
            result = queue.delMin();
            moves = result.move;
        }
        while (result != null) {
            solution.addFirst(result.current);
            result = result.previous;
        }
    }

    public int moves() {
        return moves;
    }

    public Iterable<WorldState> solution() {
        return solution;
    }
}
