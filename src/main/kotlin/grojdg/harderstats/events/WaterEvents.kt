package grojdg.harderstats.events

import grojdg.harderstats.InfoReceptionService
import net.minecraft.entity.player.PlayerEntity

object WaterEvents {
    fun onUpdateSubmergedInWater(player: PlayerEntity) {
        InfoReceptionService.setIsInWater(player.uuid, player.isWet)
    }
}