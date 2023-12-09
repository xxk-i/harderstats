package grojdg.harderstats

import net.fabricmc.api.ClientModInitializer
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object HarderstatsClient : ClientModInitializer {
	private val LOGGER: Logger = LoggerFactory.getLogger("${HarderStatsConstants.MOD_ID}")

	override fun onInitializeClient() {
		LOGGER.error("This mod is SERVER-SIDE only! Please uninstall!");
	}
}