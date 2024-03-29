package grojdg.harderstats.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;

public class ServerPlayerEntityCallbacks {
    public static final Event<AfterDamage> AFTER_DAMAGE = EventFactory.createArrayBacked(AfterDamage.class,
            (listeners) -> (source, amount, player) -> {
                for (AfterDamage listener : listeners) {
                    listener.interact(source, amount, player);
                }

                return ActionResult.PASS;
            });


    public static final Event<AfterDeath> AFTER_DEATH = EventFactory.createArrayBacked(AfterDeath.class,
            (listeners) -> (source, player) -> {
                for (AfterDeath listener : listeners) {
                    ActionResult result = listener.interact(source, player);
                }

                return ActionResult.PASS;
            });

    public static final Event<AfterExperienceGain> AFTER_EXPERIENCE_GAIN = EventFactory.createArrayBacked(AfterExperienceGain.class,
            (listeners) -> (experience, player) -> {
                for (AfterExperienceGain listener : listeners) {
                    listener.interact(experience, player);
                }
            });

    public static final Event<AfterPlayerDisconnect> AFTER_PLAYER_DISCONNECT = EventFactory.createArrayBacked(AfterPlayerDisconnect.class,
            (listeners) -> (player) ->  {
                for (AfterPlayerDisconnect listener : listeners) {
                    listener.interact(player);
                }
            });

    public static final Event<OnMoveToWorld> ON_MOVE_TO_WORLD = EventFactory.createArrayBacked(OnMoveToWorld.class,
            (listeners) -> (destination, player) -> {
                for (OnMoveToWorld listener: listeners) {
                    listener.interact(destination, player);
                }
            });

    public static final Event<OnConstructPlayer> ON_CONSTRUCT_PLAYER = EventFactory.createArrayBacked(OnConstructPlayer.class,
            (listeners) -> (world, player) -> {
                for (OnConstructPlayer listener: listeners) {
                    listener.interact(world, player);
                }
            });

    public interface AfterDamage {
        ActionResult interact(DamageSource source, float amount, ServerPlayerEntity player);
    }

    public interface AfterDeath {
        ActionResult interact(DamageSource source, ServerPlayerEntity player);
    }

    public interface AfterExperienceGain {
        void interact(int experience, ServerPlayerEntity player);
    }

    public interface AfterPlayerDisconnect {
        void interact(ServerPlayerEntity player);
    }

    public interface OnMoveToWorld {
        void interact(ServerWorld destination, ServerPlayerEntity player);
    }

    public interface OnConstructPlayer {
        void interact(ServerWorld world, ServerPlayerEntity player);
    }
}
