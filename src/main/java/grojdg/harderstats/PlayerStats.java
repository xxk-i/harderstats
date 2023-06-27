package grojdg.harderstats;

public class PlayerStats {
    private int damageTaken = 0;

    private int mobsKilled = 0;

    private int foodEaten = 0;

    private int experienceGained = 0;

    public StatTimer waterTimer = new StatTimer();

    public StatTimer netherTimer = new StatTimer();

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

    public void resetDamageTaken() {
        damageTaken = 0;
    }

    public void resetMobsKilled() {
        mobsKilled = 0;
    }

    public void resetFoodEaten() {
        foodEaten = 0;
    }

    public void resetExperienceGained() {
        experienceGained = 0;
    }
}
