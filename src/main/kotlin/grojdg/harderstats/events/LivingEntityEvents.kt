package grojdg.harderstats.events

import net.fabricmc.fabric.api.event.EventFactory
import net.minecraft.entity.LivingEntity

object LivingEntityEvents {
    fun interface OnFoodEaten {
        fun interact(entity: LivingEntity)
    }

    val ON_FOOD_EATEN = EventFactory.createArrayBacked(OnFoodEaten::class.java
    ) { listeners ->
        OnFoodEaten { entity ->
            for (listener in listeners) {
                listener.interact(entity)
            }
        }
    }
}
