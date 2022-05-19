package lab14;

import lab14lib.Generator;

public class SawToothGenerator implements Generator {
    private int state;
    private int period;

    public SawToothGenerator(int period) {
        state = 0;
        this.period = period;
    }

    public double next() {
        state = (state + 1) % period;
        return normalize(state);
    }

    private double normalize(int state) {
        return ((double) state) * 2 / period - 1;
    }
}
