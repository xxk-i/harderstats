package grojdg.harderstats;

import com.google.gson.JsonObject;
import grojdg.harderstats.networking.HTTPSender;
import net.minecraft.util.Util;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

// Service that accepts updates to stats on players
// and can dispatch that info to the hardercore-api
public class InfoReceptionService {
    private static final String rawURL = "http://127.0.0.1:8080";

    private static long uptime = 0;
//    private static final String rawURL = "http://35.170.249.207:8080";
    private HashMap<UUID, PlayerStats> playerStats;

    public static long startupTime = Util.getMeasuringTimeMs();

    // ticks to wait until dispatching
    // ~20 ticks per second
    public long dispatchTime = 200L;

    // enables dispatching of stats to hardercore-api
    // there is *not* a way of setting this back to true because it expects
    // a server reset to start a new world and initialize the service back to its defaults
    private boolean isAcceptingStats = true;

    private String auth;

    public InfoReceptionService() {
        playerStats = new HashMap();

        try {
            auth = Files.readString(Paths.get("", "auth.txt").toAbsolutePath());

        } catch (Exception e) {
            HarderStats.LOGGER.error("Reading auth failed: " + e);
        }
    }

    // dispatch sends out the collection info (uptime and player stats) and resets them as needed
    public void dispatch(boolean wait) {
        boolean refreshStats = false;

        // iterate through the hashmap oopsie
        for (Map.Entry<UUID, PlayerStats> entry : playerStats.entrySet()) {
            UUID uuid = entry.getKey();
            PlayerStats stats = entry.getValue();

            JsonObject jsonInfo = new JsonObject();
            jsonInfo.addProperty("auth", auth);
            jsonInfo.addProperty("uuid", uuid.toString());

            // we only send the stat if it's actually changed from default
            if (stats.waterTimer.getTimeElapsed() != 0) {
                jsonInfo.addProperty("timeInWater", stats.waterTimer.getTimeElapsed());
                stats.waterTimer.reset();
            }

            if (stats.netherTimer.getTimeElapsed() != 0) {
                jsonInfo.addProperty("timeInNether", stats.netherTimer.getTimeElapsed());
                stats.netherTimer.reset();
            }

            if (stats.getDamageTaken() != 0) {
                jsonInfo.addProperty("damageTaken", stats.getDamageTaken());
                stats.resetDamageTaken();
            }

            if (stats.getMobsKilled() != 0) {
                jsonInfo.addProperty("mobsKilled", stats.getMobsKilled());
                stats.resetMobsKilled();
            }

            if (stats.getFoodEaten() != 0) {
                jsonInfo.addProperty("foodEaten", stats.getFoodEaten());
                stats.resetFoodEaten();
            }

            if (stats.getExperienceGained() != 0) {
                jsonInfo.addProperty("experienceGained", stats.getExperienceGained());
                stats.resetExperienceGained();
            }

            // if we have any stats added (2 is auth and ID)
            if (jsonInfo.size() > 2) {
                HTTPSender.send(rawURL + "/world/stats/" + uuid, jsonInfo.toString(), wait);
            }
        }

        // update uptime
        uptime = Util.getMeasuringTimeMs() - startupTime;

        JsonObject uptimeJson = new JsonObject();
        uptimeJson.addProperty("auth", auth);
        uptimeJson.addProperty("uptime", uptime);

        HTTPSender.send(rawURL + "/world/uptime", uptimeJson.toString(), false);
    }

    public void setIsInWater(UUID uuid, boolean submerged) {
        if (!isAcceptingStats) {
            return;
        }

        createStatsEntry(uuid);
        PlayerStats stats = playerStats.get(uuid);
        stats.waterTimer.setIsTicking(submerged);
    }

    public void setIsInNether(UUID uuid, boolean isInNether) {
        if (!isAcceptingStats) {
            return;
        }

        createStatsEntry(uuid);
        PlayerStats stats = playerStats.get(uuid);
        stats.netherTimer.setIsTicking(isInNether);
    }

    public void updateDamageTaken(UUID uuid, int damage) {
        if (!isAcceptingStats) {
            return;
        }

        createStatsEntry(uuid);
        PlayerStats stats = playerStats.get(uuid);
        stats.updateDamageTaken(damage);
    }

    public void updateMobsKilled(UUID uuid, int count) {
        if (!isAcceptingStats) {
            return;
        }

        createStatsEntry(uuid);
        PlayerStats stats = playerStats.get(uuid);
        stats.updateMobsKilled(count);
    }

    public void updateFoodEaten(UUID uuid, int count) {
        if (!isAcceptingStats) {
            return;
        }

        createStatsEntry(uuid);
        PlayerStats stats = playerStats.get(uuid);
        stats.updateFoodEaten(count);
    }

    public void updateExperienceGained(UUID uuid, int experience) {
        if (!isAcceptingStats) {
            return;
        }

        createStatsEntry(uuid);
        PlayerStats stats = playerStats.get(uuid);
        stats.updateExperiencedGained(experience);
    }

    // upon world death:
    //      - turn off stat collection
    //      - dispatch current stat pool
    //      - finally, tell API to kill the world
    public void dispatchWorldDeath(UUID uuid, String sourceName, String sourceType) {
        // don't collect any more stats after someone has died

        //TODO UN COMMENT THIS AH
        this.setAcceptingStats(false);

        // dispatch current stat pool
        this.dispatch(true);

        JsonObject json = new JsonObject();
        json.addProperty("auth", this.auth);
        json.addProperty("killer", uuid.toString());
        json.addProperty("sourceName", sourceName);
        json.addProperty("sourceType", sourceType);

        HTTPSender.send(rawURL + "/world/kill", json.toString(), false);
    }

    private void createStatsEntry(UUID uuid) {
        if (playerStats.get(uuid) == null) {
            playerStats.put(uuid, new PlayerStats());
        }
    }

    public void setAcceptingStats(boolean acceptingStats) {
        this.isAcceptingStats = acceptingStats;
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
