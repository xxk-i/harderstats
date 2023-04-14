package grojdg.harderstats.events;

import grojdg.harderstats.InfoReceptionService;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;

public class KilledEntityEvent {

    public static void onKilledEntity(ServerWorld world, Entity entity, LivingEntity killedEntity) {
        if (entity.isPlayer() && !killedEntity.isPlayer()) {
            InfoReceptionService.InfoReceptionServiceFactory.get().updateMobsKilled(entity.getUuid(), 1);
        }
    }
}
