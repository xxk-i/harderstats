package grojdg.harderstats.listeners

import grojdg.harderstats.HarderStats
import grojdg.harderstats.InfoReceptionService
import net.minecraft.entity.damage.DamageSource
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.world.World

object ServerPlayerEntityListeners {
    fun onDamage(source: DamageSource, amount: Float, player: ServerPlayerEntity) {
        if (source.name != "hih" && source.type.msgId() != "outOfWorld") {
            InfoReceptionService.updateDamageTaken(player.gameProfile.id, amount.toInt())
        }
    }

    fun onDeath(source: DamageSource, player: ServerPlayerEntity) {
        // this is the type we kill with manually in HeartOfHearts
        if (source.type.msgId() != "outOfWorld") {
            val entityName = if (source.source == null) "null" else source.source!!.name.string
            InfoReceptionService.dispatchWorldDeath(
                    player.gameProfile.id,
                    entityName,
                    source.type.msgId()
            )
        }
    }

    fun onExperienceGain(experience: Int, player: ServerPlayerEntity) {
        InfoReceptionService.updateExperienceGained(player.uuid, experience)
    }

    // we need to reset timers on disconnect
    fun onPlayerDisconnect(player: ServerPlayerEntity) {
        InfoReceptionService.setIsInWater(player.uuid, false)
        InfoReceptionService.setIsInNether(player.uuid, false)
    }

    fun onMoveToWorld(destination: ServerWorld, player: ServerPlayerEntity) {
        HarderStats.LOGGER.info("Player is being moved into a world")
        if (destination.registryKey === World.NETHER) {
            InfoReceptionService.setIsInNether(player.uuid, true)
        }
        if (destination.registryKey === World.OVERWORLD) {
            InfoReceptionService.setIsInNether(player.uuid, false)
        }
    }
}