package grojdg.harderstats.events;

import grojdg.harderstats.InfoReceptionService;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;

public class FoodEvent {

    public static ActionResult onEatFood(LivingEntity entity) {
        InfoReceptionService.InfoReceptionServiceFactory.get().updateFoodEaten(((ServerPlayerEntity)entity).getGameProfile().getId(), 1);

        return ActionResult.PASS;
    }
}
