package grojdg.harderstats.mixin;

import grojdg.harderstats.callbacks.FoodCallback;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(LivingEntity.class)
public class FoodMixin {

    @Inject(method = "eatFood", at = @At("TAIL"))
    private void onEatFood(World world, ItemStack stack, CallbackInfoReturnable<ItemStack> cir) {
        FoodCallback.EVENT.invoker().interact((LivingEntity) (Object) this);
    }
}
