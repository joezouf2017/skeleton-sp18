package hw4.puzzle;

import java.util.Arrays;
import java.util.LinkedList;

public class Board implements WorldState {
    private int[][] tile;
    private int size;


    public Board(int[][] tiles) {
        tile = Arrays.stream(tiles).map(int[]::clone).toArray(int[][]::new);
        size = tile.length;
    }

    public int tileAt(int i, int j) {
        if (i < 0 || j < 0 || i > size - 1 || j > size - 1) {
            throw new IndexOutOfBoundsException("i is " + i + ", j is " + j);
        }
        return tile[i][j];
    }

    public int size() {
        return size;
    }

    @Override
    public Iterable<WorldState> neighbors() {
        LinkedList<WorldState> neighbors = new LinkedList<>();
        int zx = -1;
        int zy = -1;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (tileAt(i, j) == 0) {
                    zx = i;
                    zy = j;
                }
            }
        }
        int[][] copy = Arrays.stream(tile).map(int[]::clone).toArray(int[][]::new);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (Math.abs(i - zx) + Math.abs(j - zy) == 1) {
                    copy[zx][zy] = copy[i][j];
                    copy[i][j] = 0;
                    Board neighbor = new Board(copy);
                    neighbors.add(neighbor);
                    copy[i][j] = copy[zx][zy];
                    copy[zx][zy] = 0;
                }
            }
        }
        return neighbors;
    }

    public int hamming() {
        int count = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == size - 1 && j == size - 1) {
                    break;
                }
                if (tileAt(i, j) != i * size + j + 1) {
                    count++;
                }
            }
        }
        return count;
    }

    public int manhattan() {
        int count = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (tileAt(i, j) == 0) {
                    continue;
                }
                if (tileAt(i, j) != i * size + j + 1) {
                    count += Math.abs(i - (tile[i][j] - 1) / size)
                            + Math.abs(j - (tile[i][j] - 1) % size);
                }
            }
        }
        return count;
    }

    @Override
    public int estimatedDistanceToGoal() {
        return manhattan();
    }

    @Override
    public boolean equals(Object y) {
        if (this == y) {
            return true;
        }
        if (y == null || getClass() != y.getClass()) {
            return false;
        }
        String stry = y.toString();
        String strx = toString();
        return strx.equals(stry);
    }

    /** Returns the string representation of the board. 
      * Uncomment this method. */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i, j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

    @Override
    public int hashCode() {
        int result = tile == null ? 0 : tile.hashCode();
        result = result * 31 + size;
        return result;
    }
}
