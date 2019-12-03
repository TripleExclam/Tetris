package tetris.util;
import javafx.animation.AnimationTimer;

public abstract class FastAnimationTimer extends AnimationTimer {

    private long sleepNs = 0;

    long prevTime = 0;

    public FastAnimationTimer( long sleepMs) {
        this.sleepNs = sleepMs * 1_000_000;
    }

    @Override
    public void handle(long now) {

        // some delay
        if ((now - prevTime) < sleepNs) {
            return;
        }

        prevTime = now;

        handle();
    }

    public abstract void handle();

}
