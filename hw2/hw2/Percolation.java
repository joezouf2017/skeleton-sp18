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
        if (map[n] >= 100) {
            return;
        }
        if (row == 0) {
            map[n] = 101;
        } else if (row == side - 1) {
            map[n] = 110;
        } else {
            map[n] = 100;
        }
        opensites++;
        for (int[] sites : near(row, col)) {
            if (isOpen(sites[0], sites[1])) {
                grid.union(xyTo1D(sites[0], sites[1]), n);
                map[n] |= map[xyTo1D(sites[0], sites[1])];
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
        return map[n] == 101 | map[n] == 111;
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

    private void validate(int row, int col) {
        if (row < 0 | row >= side | col < 0 | col >= side) {
            throw new IndexOutOfBoundsException();
        }
    }

    private int[][] near(int row, int col) {
        if (row == 0) {
            if (col == 0) {
                return new int[][]{{0, 1}, {1, 0}};
            } else if (col == side - 1) {
                return new int[][]{{0, col - 1}, {1, col}};
            }
            return new int[][]{{0, col - 1}, {1, col}, {0, col + 1}};
        } else if (row == side - 1) {
            if (col == 0) {
                return new int[][]{{row - 1, col}, {row, col + 1}};
            } else if (col == side - 1) {
                return new int[][]{{row, col - 1}, {row - 1, col}};
            }
            return new int[][]{{row, col - 1}, {row - 1, col}, {row, col + 1}};
        } else if (col == 0) {
            return new int[][]{{row - 1, col}, {row, col + 1}, {row + 1, col}};
        } else if (col == side - 1) {
            return new int[][]{{row - 1, col}, {row, col - 1}, {row + 1, col}};
        }
        return new int[][]{{row, col - 1}, {row - 1, col}, {row + 1, col}, {row, col + 1}};
    }
}
