package lab14;

import lab14lib.Generator;

public class AcceleratingSawToothGenerator implements Generator {
    private int state;
    private int period;
    private double factor;
    private boolean finish;

    public AcceleratingSawToothGenerator(int period, double factor) {
        state = 0;
        this.period = period;
        this.factor = factor;
        finish = false;
    }

    public double next() {
        if (finish) {
            period *= factor;
            finish = false;
        }
        if ((state + 1) % period == 0) {
            finish = true;
        }
        state = (state + 1) % period;
        return normalize(state);
    }

    private double normalize(int s) {
        return ((double) s) * 2 / period - 1;
    }
}
