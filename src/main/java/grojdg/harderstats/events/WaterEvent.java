package grojdg.harderstats.events;

import grojdg.harderstats.InfoReceptionService;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;

public class WaterEvent {

    public static ActionResult onUpdateSubmergedInWater(PlayerEntity player) {
//        if (player.isWet() && !HarderStats.inWater) {
//            HarderStats.inWater = true;
//            HarderStats.startTimeInWater = Util.getMeasuringTimeMs();
//        }
//
//        else if (!player.isWet() && HarderStats.inWater) {
//            long timeInWater = Util.getMeasuringTimeMs() - HarderStats.startTimeInWater;
////            HarderStats.LOGGER.info("" + player.getGameProfile().getName() + " was in water for " + timeInWater / 1000 + " seconds!");
//            HarderStats.inWater = false;
//
//            InfoReceptionService infoReceptionService = InfoReceptionService.InfoReceptionServiceFactory.get();
//            infoReceptionService.updateTimeInWater(player.getGameProfile().getId(), timeInWater);
//        }

        InfoReceptionService.InfoReceptionServiceFactory.get().setIsInWater(player.getUuid(), player.isWet());

        return ActionResult.PASS;
    }
}
