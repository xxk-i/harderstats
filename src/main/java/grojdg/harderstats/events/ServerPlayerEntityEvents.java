package grojdg.harderstats.events;

import grojdg.harderstats.InfoReceptionService;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;

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
    }

    public static void onPlayerConnect(ServerPlayerEntity player) {
        InfoReceptionService.InfoReceptionServiceFactory.get().setIsInWater(player.getUuid(), player.isWet());
    }
}
