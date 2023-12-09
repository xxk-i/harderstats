package grojdg.harderstats.mixin;

import grojdg.harderstats.events.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
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
    private void afterDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        AfterDamage.Companion.getEvent().invoker().interact(source, amount, (ServerPlayerEntity) (Object) this);
    }

    @Inject(method = "onDeath", at = @At("TAIL"))
    private void afterDeath(DamageSource source, CallbackInfo ci) {
        AfterDeath.Companion.getEvent().invoker().interact(source, (ServerPlayerEntity) (Object) this);
    }

    @Inject(method = "addExperience", at = @At("TAIL"))
    private void afterAddExperience(int experience, CallbackInfo ci) {
        AfterExperienceGain.Companion.getEvent().invoker().interact(experience, (ServerPlayerEntity) (Object) this);
    }

    @Inject(method = "onDisconnect", at = @At("TAIL"))
    private void afterPlayerDisconnect(CallbackInfo ci) {
        AfterPlayerDisconnect.Companion.getEvent().invoker().interact((ServerPlayerEntity) (Object) this);
    }

    @Inject(method = "moveToWorld", at = @At("HEAD"))
    private void onMoveToWorld(ServerWorld destination, CallbackInfoReturnable<Entity> ci) {
        OnMoveToWorld.Companion.getEvent().invoker().interact(destination, (ServerPlayerEntity) (Object) this);
    }
}