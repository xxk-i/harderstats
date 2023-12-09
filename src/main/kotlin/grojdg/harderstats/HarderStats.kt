package grojdg.harderstats

import grojdg.harderstats.accessors.TickAccess
import grojdg.harderstats.callbacks.*
import grojdg.harderstats.events.*
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

		WaterCallback.event.register(WaterEvents::onUpdateSubmergedInWater)
		AfterDamage.event.register(ServerPlayerEntityEvents::onDamage)
		AfterDeath.event.register(ServerPlayerEntityEvents::onDeath)	// TODO swap with fabric api official AfterDeath event
		AfterExperienceGain.event.register(ServerPlayerEntityEvents::onExperienceGain)
		OnMoveToWorld.event.register(ServerPlayerEntityEvents::onMoveToWorld)
		ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register(EntityEvents::onKilledEntity)
		OnFoodEaten.event.register(FoodEvents::onEatFood)
		AfterPlayerConnect.event.register(PlayerManagerEvents::afterPlayerConnect)

		// set our timer
		ServerLifecycleEvents.SERVER_STARTED.register(ServerStarted { server: MinecraftServer -> (server as TickAccess).harderstats_setTimer(InfoReceptionService.dispatchTime) })
	}
}