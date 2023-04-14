package grojdg.harderstats;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HarderStatsClient implements ClientModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger(HarderStatsConstants.MOD_ID);

	@Override
	public void onInitializeClient() {
		LOGGER.error("This mod is SERVER-SIDE only! Please uninstall!");
	}
}