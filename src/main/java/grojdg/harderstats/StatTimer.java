package grojdg.harderstats;

import net.minecraft.util.Util;

public class StatTimer {
    private long timeStarted;
    private boolean isTicking;
    private long timeElapsed;

    public StatTimer() {
        timeStarted = 0;
        isTicking = false;
        timeElapsed = 0;
    }

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
}
