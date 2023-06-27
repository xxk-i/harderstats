package grojdg.harderstats.mixin;

import com.mojang.authlib.GameProfile;
import grojdg.harderstats.callbacks.ServerPlayerEntityCallbacks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {

    @Inject(method = "damage", at = @At(value = "TAIL"))
    private void onDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        ServerPlayerEntityCallbacks.AFTER_DAMAGE.invoker().interact(source, amount, (ServerPlayerEntity) (Object) this);
    }

    @Inject(method = "onDeath", at = @At("TAIL"))
    private void onDeath(DamageSource source, CallbackInfo ci) {
        ServerPlayerEntityCallbacks.AFTER_DEATH.invoker().interact(source, (ServerPlayerEntity) (Object) this);
    }

    @Inject(method = "addExperience", at = @At("TAIL"))
    private void onAddExperience(int experience, CallbackInfo ci) {
        ServerPlayerEntityCallbacks.AFTER_EXPERIENCE_GAIN.invoker().interact(experience, (ServerPlayerEntity) (Object) this);
    }

    @Inject(method = "onDisconnect", at = @At("TAIL"))
    private void onPlayerDisconnect(CallbackInfo ci) {
        ServerPlayerEntityCallbacks.AFTER_PLAYER_DISCONNECT.invoker().interact((ServerPlayerEntity) (Object) this);
    }

    @Inject(method = "moveToWorld", at = @At("HEAD"))
    private void onMoveToWorld(ServerWorld destination, CallbackInfoReturnable<Entity> ci) {
        ServerPlayerEntityCallbacks.ON_MOVE_TO_WORLD.invoker().interact(destination, (ServerPlayerEntity) (Object) this);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void onConstructPlayer(MinecraftServer server, ServerWorld world, GameProfile profile, CallbackInfo ci) {
        ServerPlayerEntityCallbacks.ON_CONSTRUCT_PLAYER.invoker().interact(world, (ServerPlayerEntity) (Object) this);
    }
}