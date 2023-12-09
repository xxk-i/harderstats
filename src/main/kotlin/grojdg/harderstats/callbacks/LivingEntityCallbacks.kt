package grojdg.harderstats.callbacks

import net.fabricmc.fabric.api.event.EventFactory
import net.minecraft.entity.LivingEntity

fun interface OnFoodEaten {
    fun interact(entity: LivingEntity)

    companion object {
        val event = EventFactory.createArrayBacked(OnFoodEaten::class.java
        ) { listeners ->
            OnFoodEaten { entity ->
                for (listener in listeners) {
                    listener.interact(entity)
                }
            }
        }
    }
}