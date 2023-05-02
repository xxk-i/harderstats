package grojdg.harderstats;

import net.minecraft.util.Util;

public class PlayerStats {
    private long timeEnteredWater = 0;

    private long timeInWater = 0;

    private boolean isInWater = false;

    private int damageTaken = 0;

    private int mobsKilled = 0;

    private int foodEaten = 0;

    private int experienceGained = 0;

    private void updateTimeInWater(long time) {
        timeInWater += time;
    }

    public void setIsInWater(boolean isInWater) {
        // Entered water
        if (!this.isInWater && isInWater) {
            timeEnteredWater = Util.getMeasuringTimeMs();
            this.isInWater = true;
        }

        // Exited water
        if (this.isInWater && !isInWater) {
            // update time, reset timeEnteredWater
            updateTimeInWater(Util.getMeasuringTimeMs() - timeEnteredWater);
            timeEnteredWater = 0;
            this.isInWater = false;
        }
    }

    public void updateDamageTaken(int damage) {
        damageTaken += damage;
    }

    public void updateMobsKilled(int count) {
        mobsKilled += count;
    }

    public void updateFoodEaten(int count) {
        foodEaten += count;
    }

    public void updateExperiencedGained(int experience) {
        experienceGained += experience;
    }

    public long getTimeInWater() {
        return timeInWater;
    }

    public int getDamageTaken() {
        return damageTaken;
    }

    public int getMobsKilled() {
        return mobsKilled;
    }

    public int getFoodEaten() {
        return foodEaten;
    }

    public int getExperienceGained() {
        return experienceGained;
    }
}
