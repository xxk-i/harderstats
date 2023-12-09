package grojdg.harderstats

import grojdg.harderstats.networking.InfoSender

import com.google.gson.JsonObject
import net.minecraft.util.Util
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

// Service that accepts updates to stats on players
// and can dispatch that info to the hardercore-api
object InfoReceptionService {
    private var uptime: Long = 0;

    private val playerStats: HashMap<UUID, PlayerStats> = HashMap()

    private val rawURL = "http://127.0.0.1:8080"

    // ticks to wait until dispatching
    // ~20 ticks per second
    var dispatchTime = 200L

    // enables dispatching of stats to hardercore-api
    // there is *not* a way of setting this back to true because it expects
    // a server reset to start a new world and initialize the service back to its defaults
    private var isAcceptingStats = true
    private var auth: String? = null

    var startupTime = Util.getMeasuringTimeMs()

    init {
        try {
            auth = Files.readString(Paths.get("", "auth.txt").toAbsolutePath())
        } catch (e: Exception) {
            HarderStats.LOGGER.error("Reading auth failed: $e")
        }
    }

    // dispatch sends out the collection info (uptime and player stats) and resets them as needed
    fun dispatch(wait: Boolean) {
        val refreshStats = false

        // iterate through the hashmap oopsie
        playerStats.forEach { uuid, stats ->
            val jsonInfo = JsonObject()
            jsonInfo.addProperty("auth", auth)
            jsonInfo.addProperty("uuid", uuid.toString())

            // we only send the stat if it's actually changed from default
            if (stats.waterTimer.timeElapsed != 0L) {
                jsonInfo.addProperty("timeInWater", stats.waterTimer.timeElapsed)
                stats.waterTimer.reset()
            }
            if (stats.netherTimer.timeElapsed != 0L) {
                jsonInfo.addProperty("timeInNether", stats.netherTimer.timeElapsed)
                stats.netherTimer.reset()
            }
            if (stats.damageTaken != 0) {
                jsonInfo.addProperty("damageTaken", stats.damageTaken)
                stats.resetDamageTaken()
            }
            if (stats.mobsKilled != 0) {
                jsonInfo.addProperty("mobsKilled", stats.mobsKilled)
                stats.resetMobsKilled()
            }
            if (stats.foodEaten != 0) {
                jsonInfo.addProperty("foodEaten", stats.foodEaten)
                stats.resetFoodEaten()
            }
            if (stats.experienceGained != 0) {
                jsonInfo.addProperty("experienceGained", stats.experienceGained)
                stats.resetExperienceGained()
            }

            // if we have any stats added (2 is auth and ID)
            if (jsonInfo.size() > 2) {
                InfoSender.send("$rawURL/world/stats/$uuid", jsonInfo.toString(), wait)
            }
        }

        // update uptime
        uptime = Util.getMeasuringTimeMs() - startupTime
        val uptimeJson = JsonObject()
        uptimeJson.addProperty("auth", auth)
        uptimeJson.addProperty("uptime", uptime)
        InfoSender.send("$rawURL/world/stats/", uptimeJson.toString(), false)
    }

    fun setIsInWater(uuid: UUID, submerged: Boolean) {
        if (!isAcceptingStats) {
            return
        }
        createStatsEntry(uuid)
        playerStats[uuid]?.let { stats ->
            if (submerged) {
                stats.waterTimer.startTicking()
            } else {
                stats.waterTimer.stopTicking()
            }
        }
    }

    fun setIsInNether(uuid: UUID, isInNether: Boolean) {
        if (!isAcceptingStats) {
            return
        }
        createStatsEntry(uuid)
        playerStats[uuid]?.let { stats ->
            if (isInNether) {
                stats.netherTimer.startTicking()
            } else {
                stats.netherTimer.stopTicking()
            }
        }
    }

    fun updateDamageTaken(uuid: UUID, damage: Int) {
        if (!isAcceptingStats) {
            return
        }
        createStatsEntry(uuid)
        val stats = playerStats[uuid]
        stats!!.updateDamageTaken(damage)
        playerStats[uuid]
    }

    fun updateMobsKilled(uuid: UUID, count: Int) {
        if (!isAcceptingStats) {
            return
        }
        createStatsEntry(uuid)
        val stats = playerStats[uuid]
        stats!!.updateMobsKilled(count)
    }

    fun updateFoodEaten(uuid: UUID, count: Int) {
        if (!isAcceptingStats) {
            return
        }
        createStatsEntry(uuid)
        val stats = playerStats[uuid]
        stats!!.updateFoodEaten(count)
    }

    fun updateExperienceGained(uuid: UUID, experience: Int) {
        if (!isAcceptingStats) {
            return
        }
        createStatsEntry(uuid)
        val stats = playerStats[uuid]
        stats!!.updateExperiencedGained(experience)
    }

    // upon world death:
    //      - turn off stat collection
    //      - dispatch current stat pool
    //      - finally, tell API to kill the world
    fun dispatchWorldDeath(uuid: UUID, sourceName: String?, sourceType: String?) {
        // don't collect any more stats after someone has died

        //TODO UN COMMENT THIS AH
        setAcceptingStats(false)

        // dispatch current stat pool
        dispatch(true)
        val json = JsonObject()
        json.addProperty("auth", auth)
        json.addProperty("killer", uuid.toString())
        json.addProperty("sourceName", sourceName)
        json.addProperty("sourceType", sourceType)
        InfoSender.send("$rawURL/world/kill", json.toString(), false)
    }

    private fun createStatsEntry(uuid: UUID) {
        if (playerStats[uuid] == null) {
            playerStats[uuid] = PlayerStats()
        }
    }

    private fun setAcceptingStats(acceptingStats: Boolean) {
        isAcceptingStats = acceptingStats
    }
}