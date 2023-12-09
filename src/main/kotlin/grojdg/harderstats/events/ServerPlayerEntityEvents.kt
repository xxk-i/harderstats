package grojdg.harderstats.events

import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.event.EventFactory
import net.minecraft.entity.damage.DamageSource
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld

object ServerPlayerEntityEvents {
    fun interface AfterDamage {
        fun interact(source: DamageSource, amount: Float, player: ServerPlayerEntity)
    }

    val AFTER_DAMAGE: Event<AfterDamage> = EventFactory.createArrayBacked(AfterDamage::class.java
    ) { listeners ->
        AfterDamage { source, amount, player ->
            for (listener in listeners) {
                listener.interact(source, amount, player)
            }
        }
    }

    fun interface AfterDeath {
        fun interact(source: DamageSource, player: ServerPlayerEntity)
    }

    val AFTER_DEATH: Event<AfterDeath> = EventFactory.createArrayBacked(AfterDeath::class.java
    ) { listeners ->
        AfterDeath { source, player ->
            for (listener in listeners) {
                listener.interact(source, player)
            }
        }
    }

    fun interface AfterExperienceGain {
        fun interact(experience: Int, player: ServerPlayerEntity)
    }

    val AFTER_EXPERIENCE_GAIN: Event<AfterExperienceGain> = EventFactory.createArrayBacked(AfterExperienceGain::class.java
    ) { listeners ->
        AfterExperienceGain { experience, player ->
            for (listener in listeners) {
                listener.interact(experience, player)
            }
        }
    }

    fun interface AfterPlayerDisconnect {
        fun interact(player: ServerPlayerEntity?)
    }

    val AFTER_PLAYER_DISCONNECT: Event<AfterPlayerDisconnect> = EventFactory.createArrayBacked(AfterPlayerDisconnect::class.java
    ) { listeners ->
        AfterPlayerDisconnect { player ->
            for (listener in listeners) {
                listener.interact(player)
            }
        }
    }

    fun interface OnMoveToWorld {
        fun interact(destination: ServerWorld, player: ServerPlayerEntity)
    }

    val ON_MOVE_TO_WORLD: Event<OnMoveToWorld> = EventFactory.createArrayBacked(OnMoveToWorld::class.java
    ) { listeners ->
        OnMoveToWorld { world, player ->
            for (listener in listeners) {
                listener.interact(world, player)
            }
        }
    }

    fun interface OnConstructPlayer {
        fun interact(world: ServerWorld, player: ServerPlayerEntity)
    }

    val ON_CONSTRUCT_PLAYER: Event<OnConstructPlayer> = EventFactory.createArrayBacked(OnConstructPlayer::class.java
    ) { listeners ->
        OnConstructPlayer { world, player ->
            for (listener in listeners) {
                listener.interact(world, player)
            }
        }
    }
}