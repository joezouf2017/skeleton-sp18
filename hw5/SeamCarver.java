import edu.princeton.cs.algs4.Picture;
import java.awt.Color;
import java.util.LinkedList;
public class SeamCarver {
    private Picture picture;
    private double[][] energy;

    public SeamCarver(Picture picture) {
        this.picture = picture;
        energy = new double[picture.height()][picture.width()];
        setEnergy(picture);
    }

    public Picture picture() {
        return new Picture(picture);
    }

    public int width() {
        return picture.width();
    }

    public int height() {
        return picture.height();
    }

    public double energy(int x, int y) {
        if (x < 0 || x >= picture.width() || y < 0 || y >= picture.height()) {
            throw new IndexOutOfBoundsException();
        }
        return energy[y][x];
    }

    public int[] findHorizontalSeam() {
        Picture transpose = new Picture(picture.height(), picture.width());
        for (int i = 0; i < picture.width(); i++) {
            for (int j = 0; j < picture.height(); j++) {
                transpose.set(j, i, picture.get(i, j));
            }
        }
        Picture origin = picture;
        picture = transpose;
        energy = new double[picture.height()][picture.width()];
        setEnergy(picture);
        int[] seam = findVerticalSeam();
        picture = origin;
        energy = new double[picture.height()][picture.width()];
        setEnergy(picture);
        return seam;
    }

    public int[] findVerticalSeam() {
        double[][] minimum = new double[picture.height()][picture.width()];
        if (minimum[0].length == 1) {
            return new int[minimum.length];
        }
        for (int j = 0; j < picture.height(); j++) {
            for (int i = 0; i < picture.width(); i++) {
                minimum[j][i] = M(i, j, minimum);
            }
        }
        double m = Double.MAX_VALUE;
        int start = 0;
        for (int i = 0; i < picture.width(); i++) {
            if (minimum[picture.height() - 1][i] < m) {
                start = i;
                m = minimum[picture.height() - 1][i];
            }
        }
        return seam(minimum, start);
    }

    private double M(int x, int y, double[][] minimum) {
        if (y == 0) {
            return energy[y][x];
        }
        if (x == 0) {
            double m = Math.min(minimum[y - 1][x], minimum[y - 1][x + 1]);
            return m + energy[y][x];
        }
        if (x == minimum[0].length - 1) {
            double m = Math.min(minimum[y - 1][x], minimum[y - 1][x - 1]);
            return m + energy[y][x];
        }
        double m1 = Math.min(minimum[y - 1][x], minimum[y - 1][x + 1]);
        double m = Math.min(m1, minimum[y - 1][x - 1]);
        return m + energy[y][x];
    }

    private int[] seam(double[][] minimum, int start) {
        LinkedList<Integer> path = new LinkedList<>();
        path.addFirst(start);
        double m = minimum[minimum.length - 1][start];
        //System.out.println(m);
        m -= energy[minimum.length - 1][start];
        for (int j = minimum.length - 2; j > -1; j--) {
            for (int i = start - 1; i < start + 2; i++) {
                if (i == -1 || i == minimum[0].length) {
                    continue;
                }
                if (minimum[j][i] == m) {
                    path.addFirst(i);
                    start = i;
                    m -= energy[j][i];
                    //System.out.println("index is" + start);
                    break;
                }
            }
        }
        int[] output = new int[minimum.length];
        for (int i = 0; i < output.length; i++) {
            //System.out.println("done" + i);
            output[i] = path.removeFirst();
        }
        return output;
    }

    private void setEnergy(Picture p) {
        for (int i = 0; i < p.width(); i++) {
            for (int j = 0; j < p.height(); j++) {
                Color x1 = p.get((i + 1 + p.width()) % p.width(), j);
                Color x2 = p.get((i - 1 + p.width()) % p.width(), j);
                Color y1 = p.get(i, (j + 1 + p.height()) % p.height());
                Color y2 = p.get(i, (j - 1 + p.height()) % p.height());
                int r1 = x1.getRed();
                int r2 = x2.getRed();
                int r3 = y1.getRed();
                int r4 = y2.getRed();
                int g1 = x1.getGreen();
                int g2 = x2.getGreen();
                int g3 = y1.getGreen();
                int g4 = y2.getGreen();
                int b1 = x1.getBlue();
                int b2 = x2.getBlue();
                int b3 = y1.getBlue();
                int b4 = y2.getBlue();
                int deltaX = (r1 - r2) * (r1 - r2) + (g1 - g2) * (g1 - g2) + (b1 - b2) * (b1 - b2);
                int deltaY = (r3 - r4) * (r3 - r4) + (g3 - g4) * (g3 - g4) + (b3 - b4) * (b3 - b4);
                energy[j][i] = deltaX + deltaY;
            }
        }
    }

    public void removeHorizontalSeam(int[] seam) {
        if (seam.length != picture.width()) {
            throw new IllegalArgumentException();
        }
        for (int i = 1; i < seam.length; i++) {
            if ((seam[i] - seam[i - 1]) * (seam[i] - seam[i - 1]) > 1) {
                throw new IllegalArgumentException();
            }
        }
        picture = SeamRemover.removeHorizontalSeam(picture, seam);
        setEnergy(picture);
    }

    public void removeVerticalSeam(int[] seam) {
        if (seam.length != picture.height()) {
            throw new IllegalArgumentException();
        }
        for (int i = 1; i < seam.length; i++) {
            if ((seam[i] - seam[i - 1]) * (seam[i] - seam[i - 1]) > 1) {
                throw new IllegalArgumentException();
            }
        }
        picture = SeamRemover.removeVerticalSeam(picture, seam);
        setEnergy(picture);
    }

    /**public static void main(String[] args) {
        Picture p = new Picture("images/6x5.png");
        SeamCarver sc = new SeamCarver(p);
        for (int j = 0; j < p.height(); j++) {
            for (int i = 0; i < p.width(); i++) {
                System.out.print(sc.energy[j][i] + " ");
            }
            System.out.println();
        }
        System.out.println();
        double[][] minimum = new double[p.height()][p.width()];
        for (int j = 0; j < p.height(); j++) {
            for (int i = 0; i < p.width(); i++) {
                minimum[j][i] = sc.M(i, j, minimum);
            }
        }
        for (double[] a : minimum) {
            for (double b : a) {
                System.out.print(b + " ");
            }
            System.out.println();
        }
        int[] seam = sc.seam(minimum, 2);
        for (int i : seam) {
            System.out.print(i + " ");
        }
        int[] s2 = sc.findHorizontalSeam();
        for (int i : s2) {
            System.out.print(i + " ");
        }
    }**/
}
