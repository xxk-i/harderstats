package grojdg.harderstats.listeners

import grojdg.harderstats.InfoReceptionService
import net.minecraft.entity.player.PlayerEntity

object WaterListeners {
    fun onUpdateSubmergedInWater(player: PlayerEntity) {
        InfoReceptionService.setIsInWater(player.uuid, player.isWet)
    }
}