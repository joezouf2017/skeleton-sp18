package lab11.graphs;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s = 0;
    private boolean hasCycle;
    private int[] track;


    public MazeCycles(Maze m) {
        super(m);
        maze = m;
        distTo[s] = 0;
        track = new int[m.V()];
    }

    @Override
    public void solve() {
        dfs(s);
    }

    // Helper methods go here
    public void dfs(int v) {
        marked[v] = true;
        announce();
        for (int nbr : maze.adj(v)) {
            if (hasCycle) {
                return;
            } else if (marked[nbr] && distTo[nbr] != distTo[v] - 1) {
                edgeTo[nbr] = v;
                hasCycle = true;
                int cur = v;
                while (cur != nbr) {
                    edgeTo[cur] = track[cur];
                    cur = track[cur];
                }
                announce();
            } else if (!marked[nbr]) {
                marked[nbr] = true;
                announce();
                distTo[nbr] = distTo[v] + 1;
                track[nbr] = v;
                dfs(nbr);
            }
        }
    }
}

