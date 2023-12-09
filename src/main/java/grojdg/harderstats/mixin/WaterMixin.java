package grojdg.harderstats.mixin;

import grojdg.harderstats.events.WaterCallback;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class WaterMixin {
	@Inject(at = @At("TAIL"), method = "updateWaterSubmersionState")
	private void onUpdateWaterSubmersionState(CallbackInfoReturnable<Boolean> cir) {
		WaterCallback.Companion.getEvent().invoker().interact((PlayerEntity) (Object) this);
	}
}