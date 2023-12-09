package grojdg.harderstats.events

import grojdg.harderstats.HarderStats
import grojdg.harderstats.InfoReceptionService
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.world.World

object PlayerManagerEvents {
    // start back up the nether timer if the player joins and spawns into the nether
    fun afterPlayerConnect(player: ServerPlayerEntity) {
        HarderStats.LOGGER.info("afterPlayerConnect is in world: " + player.world.registryKey)
        if (player.world.registryKey === World.NETHER) {
            InfoReceptionService.setIsInNether(player.uuid, true)
        }
    }
}