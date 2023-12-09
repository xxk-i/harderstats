package grojdg.harderstats.callbacks

import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.event.EventFactory
import net.minecraft.entity.player.PlayerEntity

fun interface WaterCallback {
    fun interact(player: PlayerEntity)

    companion object {
        val event: Event<WaterCallback> = EventFactory.createArrayBacked(WaterCallback::class.java
        ) { listeners ->
            WaterCallback { player: PlayerEntity ->
                for (listener in listeners) {
                    listener.interact(player)
                }
            }
        }
    }
}
