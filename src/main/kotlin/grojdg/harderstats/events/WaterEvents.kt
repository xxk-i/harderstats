package grojdg.harderstats.events

import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.event.EventFactory
import net.minecraft.entity.player.PlayerEntity

object WaterEvents {
    fun interface AfterWaterSubmersionStateUpdate {
        fun interact(player: PlayerEntity)
    }

    val AFTER_WATER_SUBMERSION_STATE_UPDATE: Event<AfterWaterSubmersionStateUpdate> = EventFactory.createArrayBacked(AfterWaterSubmersionStateUpdate::class.java
    ) { listeners ->
        AfterWaterSubmersionStateUpdate { player: PlayerEntity ->
            for (listener in listeners) {
                listener.interact(player)
            }
        }
    }

}
