package grojdg.harderstats

import grojdg.harderstats.accessors.TickAccess
import grojdg.harderstats.events.LivingEntityEvents
import grojdg.harderstats.events.PlayerManagerEvents
import grojdg.harderstats.events.ServerPlayerEntityEvents
import grojdg.harderstats.events.WaterEvents
import grojdg.harderstats.listeners.*
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents.ServerStarted
import net.minecraft.server.MinecraftServer
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object HarderStats : ModInitializer {
    val LOGGER: Logger = LoggerFactory.getLogger("harderstats")

	override fun onInitialize() {
		LOGGER.info("Harderstats initializing");

		WaterEvents.AFTER_WATER_SUBMERSION_STATE_UPDATE.register(WaterListeners::onUpdateSubmergedInWater)
		ServerPlayerEntityEvents.AFTER_DAMAGE.register(ServerPlayerEntityListeners::onDamage)
		ServerPlayerEntityEvents.AFTER_DEATH.register(ServerPlayerEntityListeners::onDeath)	// TODO swap with fabric api official AfterDeath event
		ServerPlayerEntityEvents.AFTER_EXPERIENCE_GAIN.register(ServerPlayerEntityListeners::onExperienceGain)
		ServerPlayerEntityEvents.ON_MOVE_TO_WORLD.register(ServerPlayerEntityListeners::onMoveToWorld)
		ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register(CombatListeners::onKilledEntity)
		LivingEntityEvents.ON_FOOD_EATEN.register(FoodListeners::onEatFood)
		PlayerManagerEvents.AFTER_PLAYER_CONNECT.register(PlayerManagerListeners::afterPlayerConnect)

		// set our timer
		ServerLifecycleEvents.SERVER_STARTED.register(ServerStarted { server: MinecraftServer -> (server as TickAccess).harderstats_setTimer(InfoReceptionService.dispatchTime) })
	}
}