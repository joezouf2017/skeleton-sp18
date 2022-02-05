package lab11.graphs;

import edu.princeton.cs.algs4.MinPQ;
import java.util.Comparator;

/**
 *  @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;

    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /** Estimate of the distance from v to the target. */
    private int h(int v) {
        return Math.abs(maze.toX(v) - maze.toX(t)) + Math.abs(maze.toY(v) - maze.toY(t));
    }

    /** Finds vertex estimated to be closest to target. */
    private int findMinimumUnmarked() {
        return -1;
        /* You do not have to use this method. */
    }

    private class Dcomparator implements Comparator<Integer> {
        @Override
        public int compare(Integer a, Integer b) {
            return distTo[a] + h(a) - distTo[b] - h(b);
        }
    }

    /** Performs an A star search from vertex s. */
    private void astar(int s) {
        MinPQ<Integer> fringe = new MinPQ<>(new Dcomparator());
        fringe.insert(s);
        while (!targetFound) {
            int cur = fringe.delMin();
            marked[cur] = true;
            announce();
            for (int nbr: maze.adj(cur)) {
                if (!marked[nbr] && distTo[nbr] != distTo[cur] - 1) {
                    fringe.insert(nbr);
                    distTo[nbr] = distTo[cur] + 1;
                    edgeTo[nbr] = cur;
                    announce();
                    if (h(nbr) == 0) {
                        marked[nbr] = true;
                        announce();
                        targetFound = true;
                    }
                }
            }
        }
    }

    @Override
    public void solve() {
        astar(s);
    }

}

