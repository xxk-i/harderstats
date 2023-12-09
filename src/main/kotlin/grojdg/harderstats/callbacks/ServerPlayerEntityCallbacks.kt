package grojdg.harderstats.callbacks

import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.event.EventFactory
import net.minecraft.entity.damage.DamageSource
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld

fun interface AfterDamage {
    fun interact(source: DamageSource, amount: Float, player: ServerPlayerEntity)

    companion object {
        val event: Event<AfterDamage> = EventFactory.createArrayBacked(AfterDamage::class.java
        ) { listeners ->
            AfterDamage { source, amount, player ->
                for (listener in listeners) {
                    listener.interact(source, amount, player)
                }
            }
        }
    }
}

fun interface AfterDeath {
    fun interact(source: DamageSource, player: ServerPlayerEntity)

    companion object {
        val event: Event<AfterDeath> = EventFactory.createArrayBacked(AfterDeath::class.java
        ) { listeners ->
            AfterDeath { source, player ->
                for (listener in listeners) {
                    listener.interact(source, player)
                }
            }
        }
    }
}

fun interface AfterExperienceGain {
    fun interact(experience: Int, player: ServerPlayerEntity)

    companion object {
        val event: Event<AfterExperienceGain> = EventFactory.createArrayBacked(AfterExperienceGain::class.java
        ) { listeners ->
            AfterExperienceGain { experience, player ->
                for (listener in listeners) {
                    listener.interact(experience, player)
                }
            }
        }
    }
}

fun interface AfterPlayerDisconnect {
    fun interact(player: ServerPlayerEntity?)

    companion object {
        val event: Event<AfterPlayerDisconnect> = EventFactory.createArrayBacked(AfterPlayerDisconnect::class.java
        ) { listeners ->
            AfterPlayerDisconnect { player ->
                for (listener in listeners) {
                    listener.interact(player)
                }
            }
        }
    }
}

fun interface OnMoveToWorld {
    fun interact(destination: ServerWorld, player: ServerPlayerEntity)
    
    companion object {
        val event: Event<OnMoveToWorld> = EventFactory.createArrayBacked(OnMoveToWorld::class.java
        ) { listeners ->
            OnMoveToWorld { world, player ->
                for (listener in listeners) {
                    listener.interact(world, player)
                }
            }
        }
    }
}

fun interface OnConstructPlayer {
    fun interact(world: ServerWorld, player: ServerPlayerEntity)

    companion object {
        val event: Event<OnConstructPlayer> = EventFactory.createArrayBacked(OnConstructPlayer::class.java
        ) { listeners ->
            OnConstructPlayer { world, player ->
                for (listener in listeners) {
                    listener.interact(world, player)
                }
            }
        }
    }
}
