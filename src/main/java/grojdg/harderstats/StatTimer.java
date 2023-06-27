package grojdg.harderstats;

import net.minecraft.util.Util;

public class StatTimer {
    private long timeStarted = 0;
    private boolean isTicking = false;
    private long timeElapsed = 0;

    public void setIsTicking(boolean isTicking) {
        if (!this.isTicking && isTicking) {
            this.timeStarted = Util.getMeasuringTimeMs();
            this.isTicking = true;
        }

        if (this.isTicking && !isTicking) {
            this.timeElapsed = Util.getMeasuringTimeMs() - timeStarted;
            timeStarted = 0;
            this.isTicking = false;
        }
    }

    public long getTimeElapsed() {
        return this.timeElapsed;
    }

    public long getTimeStarted() {
        return this.timeStarted;
    }

    public void reset() {
        this.timeStarted = 0;
        this.isTicking = false;
        this.timeElapsed = 0;
    }
}
