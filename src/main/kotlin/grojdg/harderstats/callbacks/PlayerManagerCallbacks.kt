package grojdg.harderstats.callbacks

import net.fabricmc.fabric.api.event.EventFactory
import net.minecraft.server.network.ServerPlayerEntity

fun interface AfterPlayerConnect {
    fun interact(player: ServerPlayerEntity)

    companion object {
        val event = EventFactory.createArrayBacked(AfterPlayerConnect::class.java
        ) { listeners ->
            AfterPlayerConnect { player ->
                for (listener in listeners) {
                    listener.interact(player)
                }
            }
        }
    }
}