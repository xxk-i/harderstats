package grojdg.harderstats;

import grojdg.harderstats.accessors.TickAccess;
import grojdg.harderstats.callbacks.PlayerManagerCallbacks;
import grojdg.harderstats.callbacks.ServerPlayerEntityCallbacks;
import grojdg.harderstats.callbacks.FoodCallback;
import grojdg.harderstats.callbacks.WaterCallback;
import grojdg.harderstats.events.*;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HarderStats implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger(HarderStatsConstants.MOD_ID);

	@Override
	public void onInitialize() {
		WaterCallback.EVENT.register(WaterEvent::onUpdateSubmergedInWater);
		ServerPlayerEntityCallbacks.AFTER_DAMAGE.register(ServerPlayerEntityEvents::onDamage);
		ServerPlayerEntityCallbacks.AFTER_DEATH.register(ServerPlayerEntityEvents::onDeath);
		ServerPlayerEntityCallbacks.AFTER_EXPERIENCE_GAIN.register(ServerPlayerEntityEvents::onExperienceGain);
		ServerPlayerEntityCallbacks.AFTER_PLAYER_DISCONNECT.register(ServerPlayerEntityEvents::onPlayerDisconnect);
		ServerPlayerEntityCallbacks.ON_MOVE_TO_WORLD.register(ServerPlayerEntityEvents::onMoveToWorld);
		ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register(KilledEntityEvent::onKilledEntity);
		FoodCallback.EVENT.register(FoodEvent::onEatFood);
		PlayerManagerCallbacks.AFTER_PLAYER_CONNECT.register(PlayerManagerEvents::afterPlayerConnect);

		// set our timer
		ServerLifecycleEvents.SERVER_STARTED.register((server) -> {
			((TickAccess)server).harderstats_setTimer(InfoReceptionService.InfoReceptionServiceFactory.get().dispatchTime);
		});
	}
}