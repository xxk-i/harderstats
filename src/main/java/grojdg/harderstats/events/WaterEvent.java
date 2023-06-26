package grojdg.harderstats.events;

import grojdg.harderstats.InfoReceptionService;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;

public class WaterEvent {

    public static ActionResult onUpdateSubmergedInWater(PlayerEntity player) {
        InfoReceptionService.InfoReceptionServiceFactory.get().setIsInWater(player.getUuid(), player.isWet());

        return ActionResult.PASS;
    }
}
