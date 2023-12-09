package grojdg.harderstats.listeners

import grojdg.harderstats.InfoReceptionService
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld

object CombatListeners {
    fun onKilledEntity(world: ServerWorld?, entity: Entity, killedEntity: LivingEntity) {
        if (entity.isPlayer && !killedEntity.isPlayer) {
            InfoReceptionService.updateMobsKilled((entity as ServerPlayerEntity).gameProfile.id, 1)
        }
    }
}