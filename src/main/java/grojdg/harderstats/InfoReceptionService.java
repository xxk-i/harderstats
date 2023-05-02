package grojdg.harderstats;

import com.google.gson.JsonObject;
import grojdg.harderstats.networking.HTTPSender;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

// Service that accepts updates to stats on players
// and can dispatch that info to the hardercore-api
public class InfoReceptionService {
    private static final String rawURL = "http://127.0.0.1:8080";
    private HashMap<UUID, PlayerStats> playerStats;

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

    // dispatch sends out the collection info and wipes the hashmap
    public void dispatch(boolean wait) {
        //iterate through the hashmap oopsie
        for (Map.Entry<UUID, PlayerStats> entry : playerStats.entrySet()) {
            UUID uuid  = entry.getKey();
            PlayerStats stats = entry.getValue();

            JsonObject jsonInfo = new JsonObject();
            jsonInfo.addProperty("auth", auth);
            jsonInfo.addProperty("uuid", uuid.toString());

            // we only send the stat if it's actually changed from default
            if (stats.getTimeInWater() != 0) {
                jsonInfo.addProperty("timeInWater", stats.getTimeInWater());
            }

            if (stats.getDamageTaken() != 0) {
                jsonInfo.addProperty("damageTaken", stats.getDamageTaken());
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

            // if we have any stats added (2 is auth and ID)
            if (jsonInfo.size() > 2) {
                HTTPSender.send(rawURL + "/world/stats/" + uuid, jsonInfo.toString(), wait);
            }
        }

        // reset stats
        playerStats = new HashMap<>();
    }

//    public void updateTimeInWater(UUID uuid, long time) {
//        if (!isAcceptingStats) {
//            return;
//        }
//
//        createStatsEntry(uuid);
//        PlayerStats stats = playerStats.get(uuid);
//        stats.updateTimeInWater(time);
//    }

    public void setIsInWater(UUID uuid, boolean submerged) {
        if (!isAcceptingStats) {
            return;
        }

        createStatsEntry(uuid);
        PlayerStats stats = playerStats.get(uuid);
        stats.setIsInWater(submerged);
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
