package grojdg.harderstats.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
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


    public interface AfterDamage {
        ActionResult interact(DamageSource source, float amount, ServerPlayerEntity player);
    }

    public interface AfterDeath {
        ActionResult interact(DamageSource source, ServerPlayerEntity player);
    }

    public interface AfterExperienceGain {
        void interact(int experience, ServerPlayerEntity player);
    }
}
