package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;
import java.util.Comparator;
import java.util.ArrayList;

public class Solver {
    private int moves;
    private ArrayList<WorldState> solution;

    private class SearchNode {
        private WorldState current;
        private int move;
        private SearchNode previous;

        public SearchNode(WorldState cur, int m, SearchNode pre) {
            current = cur;
            move = m;
            previous = pre;
        }
    }

    private class SearchNodecomparator implements Comparator<SearchNode> {
        @Override
        public int compare(SearchNode A, SearchNode B) {
            return A.current.estimatedDistanceToGoal() + A.move
                    - B.current.estimatedDistanceToGoal() - B.move;
        }
    }

    public Solver(WorldState initial) {
        MinPQ<SearchNode> queue = new MinPQ<>(new SearchNodecomparator());
        queue.insert(new SearchNode(initial, 0, null));
        SearchNode result = queue.delMin();
        moves = 0;
        solution = new ArrayList<>(initial.estimatedDistanceToGoal());
        solution.add(result.current);
        while (!result.current.isGoal()) {
            moves++;
            for (WorldState neighbor : result.current.neighbors()) {
                if (!neighbor.equals(result.previous)) {
                    queue.insert(new SearchNode(neighbor, moves, result));
                }
            }
            result = queue.delMin();
            solution.add(result.current);
        }
    }

    public int moves() {
        return moves;
    }

    public Iterable<WorldState> solution() {
        return solution;
    }
}
