package grojdg.harderstats.events

import grojdg.harderstats.InfoReceptionService
import net.minecraft.entity.LivingEntity
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.ActionResult

object FoodEvents {
    fun onEatFood(entity: LivingEntity): ActionResult? {
        InfoReceptionService.updateFoodEaten((entity as ServerPlayerEntity).gameProfile.id, 1)
        return ActionResult.PASS
    }
}