package grojdg.harderstats.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;

public interface WaterCallback {
    Event<WaterCallback> EVENT = EventFactory.createArrayBacked(WaterCallback.class,
            (listeners) -> (player) -> {
                for (WaterCallback listener : listeners) {
                    ActionResult result = listener.interact(player);

                    if (result != ActionResult.PASS) {{
                        return result;
                    }}
                }

                return ActionResult.PASS;
            });

    ActionResult interact(PlayerEntity player);
}
