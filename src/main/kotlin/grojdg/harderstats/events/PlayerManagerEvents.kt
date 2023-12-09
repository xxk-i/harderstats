package grojdg.harderstats.events

import net.fabricmc.fabric.api.event.EventFactory
import net.minecraft.server.network.ServerPlayerEntity

object PlayerManagerEvents {
    fun interface AfterPlayerConnect {
        fun interact(player: ServerPlayerEntity)
    }

    val AFTER_PLAYER_CONNECT = EventFactory.createArrayBacked(AfterPlayerConnect::class.java
    ) { listeners ->
        AfterPlayerConnect { player ->
            for (listener in listeners) {
                listener.interact(player)
            }
        }
    }
}