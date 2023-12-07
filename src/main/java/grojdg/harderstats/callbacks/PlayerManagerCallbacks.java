package grojdg.harderstats.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;

public class PlayerManagerCallbacks {
    public static final Event<AfterPlayerConnect> AFTER_PLAYER_CONNECT = EventFactory.createArrayBacked(AfterPlayerConnect.class,
            (listeners) -> (player) -> {
                for (AfterPlayerConnect listener : listeners) {
                    listener.interact(player);
                }
            });

    public interface AfterPlayerConnect {
        void interact(ServerPlayerEntity player);
    }
}
