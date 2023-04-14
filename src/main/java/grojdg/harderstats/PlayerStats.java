package grojdg.harderstats;


public class PlayerStats {
    private long timeInWater = 0;

    private int damageTaken = 0;

    private int mobsKilled = 0;

    private int foodEaten = 0;

    private int experienceGained = 0;

    private boolean hasDied = false;

    public void updateTimeInWater(long time) {
        timeInWater += time;
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

    public void setHasDied() {
        hasDied = true;
    }

    public long getTimeInWater() {
        return timeInWater;
    }

    public int getDamageTaken() {
        return damageTaken;
    }

    public boolean getHasDied() {
        return hasDied;
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
