package grojdg.harderstats.events;

import grojdg.harderstats.HarderStats;
import grojdg.harderstats.InfoReceptionService;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

public class PlayerManagerEvents {

    // start back up the nether timer if the player joins and spawns into the nether
    public static void afterPlayerConnect(ServerPlayerEntity player) {
        HarderStats.LOGGER.info("afterPlayerConnect is in world: " + player.getWorld().getRegistryKey());
        if (player.getWorld().getRegistryKey() == World.NETHER) {
            InfoReceptionService.InfoReceptionServiceFactory.get().setIsInNether(player.getUuid(), true);
        }
    }
}
