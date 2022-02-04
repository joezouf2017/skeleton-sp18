package lab11.graphs;

import java.util.Queue;
import java.util.ArrayDeque;

/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s;
    private int t;
    private Maze maze;
    private Queue<Integer> fringe;


    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs() {
        fringe = new ArrayDeque<>();
        fringe.add(s);
        int cur = fringe.remove();
        marked[s] = true;
        announce();
        while (!marked[t]) {
            for (int nbr : maze.adj(cur)) {
                if (!marked[nbr]) {
                    fringe.add(nbr);
                    edgeTo[nbr] = cur;
                    distTo[nbr] = distTo[cur] + 1;
                }
            }
            cur = fringe.remove();
            marked[cur] = true;
            announce();
        }
    }


    @Override
    public void solve() {
        bfs();
    }
}

