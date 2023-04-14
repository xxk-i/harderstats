package grojdg.harderstats.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ActionResult;

public interface FoodCallback {
    Event<FoodCallback> EVENT = EventFactory.createArrayBacked(FoodCallback.class,
            (listeners) -> (entity) -> {
                for (FoodCallback listener : listeners) {
                    listener.interact(entity);
                }

                return ActionResult.PASS;
            });

    ActionResult interact(LivingEntity entity);
}
