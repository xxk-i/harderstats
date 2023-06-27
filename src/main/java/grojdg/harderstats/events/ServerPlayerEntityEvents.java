package grojdg.harderstats.events;

import grojdg.harderstats.HarderStats;
import grojdg.harderstats.InfoReceptionService;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;

public class ServerPlayerEntityEvents {
    public static ActionResult onDamage(DamageSource source, float amount, ServerPlayerEntity player) {
        if((!source.getName().equals("hih") && !source.getType().msgId().equals("outOfWorld"))) {
            InfoReceptionService.InfoReceptionServiceFactory.get().updateDamageTaken(player.getGameProfile().getId(), (int) amount);
        }

        return ActionResult.PASS;
    }

    public static ActionResult onDeath(DamageSource source, ServerPlayerEntity player) {
        // this is the type we kill with manually in HeartOfHearts
        if (!source.getType().msgId().equals("outOfWorld")) {
            String entityName = source.getSource() == null ? "null" : source.getSource().getEntityName();
            InfoReceptionService.InfoReceptionServiceFactory.get().dispatchWorldDeath(
                    player.getGameProfile().getId(),
                    entityName,
                    source.getType().msgId()
            );
        }

        return ActionResult.PASS;
    }

    public static void onExperienceGain(int experience, ServerPlayerEntity player) {
        InfoReceptionService.InfoReceptionServiceFactory.get().updateExperienceGained(player.getUuid(), experience);
    }

    // we need to reset timers on disconnect
    public static void onPlayerDisconnect(ServerPlayerEntity player) {
        InfoReceptionService.InfoReceptionServiceFactory.get().setIsInWater(player.getUuid(), false);
        InfoReceptionService.InfoReceptionServiceFactory.get().setIsInNether(player.getUuid(), false);
    }

    // start back up nether timer if player is created (connects) and is in the nether
    public static void onConstructPlayer(ServerWorld world, ServerPlayerEntity player) {
        if (world.getRegistryKey() == World.NETHER) {
            InfoReceptionService.InfoReceptionServiceFactory.get().setIsInNether(player.getUuid(), true);
        }
    }

    public static void onMoveToWorld(ServerWorld destination, ServerPlayerEntity player) {
        if (destination.getRegistryKey() == World.NETHER) {
            InfoReceptionService.InfoReceptionServiceFactory.get().setIsInNether(player.getUuid(), true);
        }

        if (destination.getRegistryKey() == World.OVERWORLD) {
            InfoReceptionService.InfoReceptionServiceFactory.get().setIsInNether(player.getUuid(), false);
        }
    }
}
