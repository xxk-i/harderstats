package grojdg.harderstats;

import com.google.gson.JsonObject;
import grojdg.harderstats.networking.HTTPSender;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

// Service that accepts updates to stats on players
// and can dispatch that info to the hardercore-api
public class InfoReceptionService {
    private HashMap<UUID, PlayerStats> playerStats;

    // ticks to wait until dispatching
    // ~20 ticks per second
    public long dispatchTime = 200L;

    public InfoReceptionService() {
        playerStats = new HashMap();
    }

    // dispatch sends out the collection info and wipes the hashmap
    public void dispatch() {
        //iterate through the hashmap oopsie
        for (Map.Entry<UUID, PlayerStats> entry : playerStats.entrySet()) {
            UUID uuid  = entry.getKey();
            PlayerStats stats = entry.getValue();

            JsonObject jsonInfo = new JsonObject();
            jsonInfo.addProperty("UUID", uuid.toString());

            // we only send the stat if it's actually changed from default
            if (stats.getTimeInWater() != 0) {
                jsonInfo.addProperty("timeInWater", stats.getTimeInWater());
            }

            if (stats.getDamageTaken() != 0) {
                jsonInfo.addProperty("damageTaken", stats.getDamageTaken());
            }

            if (stats.getHasDied()) {
                jsonInfo.addProperty("hasDied", true);
            }

            if (stats.getMobsKilled() != 0) {
                jsonInfo.addProperty("mobsKilled", stats.getMobsKilled());
            }

            if (stats.getFoodEaten() != 0) {
                jsonInfo.addProperty("foodEaten", stats.getFoodEaten());
            }

            if (stats.getExperienceGained() != 0) {
                jsonInfo.addProperty("experienceGained", stats.getExperienceGained());
            }

            HTTPSender.send(jsonInfo.toString());
        }

        playerStats = new HashMap<>();
    }

    public void updateTimeInWater(UUID uuid, long time) {
        createStatsEntry(uuid);
        PlayerStats stats = playerStats.get(uuid);
        stats.updateTimeInWater(time);
    }

    public void updateDamageTaken(UUID uuid, int damage) {
        createStatsEntry(uuid);
        PlayerStats stats = playerStats.get(uuid);
        stats.updateDamageTaken(damage);
    }

    public void updateMobsKilled(UUID uuid, int count) {
        createStatsEntry(uuid);
        PlayerStats stats = playerStats.get(uuid);
        stats.updateMobsKilled(count);
    }

    public void updateFoodEaten(UUID uuid, int count) {
        createStatsEntry(uuid);
        PlayerStats stats = playerStats.get(uuid);
        stats.updateFoodEaten(count);
    }

    public void updateExperienceGained(UUID uuid, int experience) {
        createStatsEntry(uuid);
        PlayerStats stats = playerStats.get(uuid);
        stats.updateExperiencedGained(experience);
    }

    public void setHasDied(UUID uuid) {
        createStatsEntry(uuid);
        PlayerStats stats = playerStats.get(uuid);
        stats.setHasDied();
    }

    private void createStatsEntry(UUID uuid) {
        if (playerStats.get(uuid) == null) {
            playerStats.put(uuid, new PlayerStats());
        }
    }

    public static class InfoReceptionServiceFactory {
        private static InfoReceptionService INSTANCE;

        public static synchronized InfoReceptionService get() {
            if (INSTANCE == null) {
                INSTANCE = new InfoReceptionService();
            }

            return INSTANCE;
        }
    }
}
