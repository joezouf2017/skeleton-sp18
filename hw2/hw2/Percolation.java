package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF grid;
    private byte[] map;
    private int side;
    private int opensites;
    private boolean percolates;
    /* create N-by-N grid, with all sites initially blocked */
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        side = N;
        grid = new WeightedQuickUnionUF(N * N);
        map = new byte[N * N];
        opensites = 0;
        percolates = false;
    }

    /* open the site (row, col) if it is not open already */
    public void open(int row, int col) {
        validate(row, col);
        int n = xyTo1D(row, col);
        if (map[n] != 0) {
            return;
        }
        map[n] = 100;
        if (row == 0) {
            map[n] |= 101;
        }
        if (row == side - 1) {
            map[n] |= 110;
        }
        opensites++;
        int[] x = {1, -1, 0, 0};
        int[] y = {0, 0, 1, -1};
        for (int i = 0; i < 4; i++) {
            int nearx = row + x[i];
            int neary = col + y[i];
            if (fair(nearx, neary)) {
                int adjblock = xyTo1D(nearx, neary);
                if (isOpen(nearx, neary)) {
                    int root = grid.find(adjblock);
                    map[n] |= map[root];
                    grid.union(n, adjblock);
                }
            }
        }
        int root = grid.find(n);
        map[root] |= map[n];
        if (map[root] == 111) {
            percolates = true;
        }
    }

    /* is the site (row, col) open? */
    public boolean isOpen(int row, int col) {
        validate(row, col);
        int n = xyTo1D(row, col);
        return map[n] != 0;
    }

    /* is the site (row, col) full? */
    public boolean isFull(int row, int col) {
        validate(row, col);
        int n = grid.find(xyTo1D(row, col));
        return (map[n] & 101) == 101;
    }

    /* number of open sites */
    public int numberOfOpenSites() {
        return opensites;
    }

    /* does the system percolate? */
    public boolean percolates() {
        return percolates;
    }

    private int xyTo1D(int r, int c) {
        return r * side + c;
    }

    private boolean fair(int row, int col) {
        return !(row < 0 | row >= side | col < 0 | col >= side);
    }

    private void validate(int row, int col) {
        if (row < 0 | row >= side | col < 0 | col >= side) {
            throw new IndexOutOfBoundsException();
        }
    }

    public static void main(String[] args) {

    }
}
