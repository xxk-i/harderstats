package grojdg.harderstats

import net.minecraft.util.Util

class StatTimer {
    var timeStarted: Long = 0
        private set

    var timeElapsed: Long = 0
        private set

    var isTicking = false

    fun startTicking() {
        if (!isTicking) {
            timeStarted = Util.getMeasuringTimeMs()
            isTicking = true
        }
    }

    fun stopTicking() {
        if (isTicking) {
            timeElapsed = Util.getMeasuringTimeMs() - timeStarted
            isTicking = false
        }
    }

    fun reset() {
        timeStarted = 0
        isTicking = false
        timeElapsed = 0
    }
}