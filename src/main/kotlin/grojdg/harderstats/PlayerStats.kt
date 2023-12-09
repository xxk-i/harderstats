package grojdg.harderstats

class PlayerStats {
    var damageTaken = 0
        private set
    var mobsKilled = 0
        private set
    var foodEaten = 0
        private set
    var experienceGained = 0
        private set
    var waterTimer: StatTimer = StatTimer()
    var netherTimer: StatTimer = StatTimer()
    fun updateDamageTaken(damage: Int) {
        damageTaken += damage
    }

    fun updateMobsKilled(count: Int) {
        mobsKilled += count
    }

    fun updateFoodEaten(count: Int) {
        foodEaten += count
    }

    fun updateExperiencedGained(experience: Int) {
        experienceGained += experience
    }

    fun resetDamageTaken() {
        damageTaken = 0
    }

    fun resetMobsKilled() {
        mobsKilled = 0
    }

    fun resetFoodEaten() {
        foodEaten = 0
    }

    fun resetExperienceGained() {
        experienceGained = 0
    }
}
