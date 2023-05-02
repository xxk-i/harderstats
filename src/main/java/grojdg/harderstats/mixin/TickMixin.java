package grojdg.harderstats.mixin;

import grojdg.harderstats.InfoReceptionService;
import grojdg.harderstats.accessors.TickAccess;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// this timer is a piece of shit copypasta from the fabricmc discord bot
// because apparently "threads and java.util.timer CAN crash" whatever the fuck
// that actually means
//
// its gross as fuck and i will rebel against it at the nearest convenient time
@Mixin(MinecraftServer.class)
public class TickMixin implements TickAccess {
    @Unique
    private long time;

    @Inject(method = "tick", at = @At("TAIL"))
    private void onTick(CallbackInfo ci) {
        if (--time == 0L ) {
            InfoReceptionService service = InfoReceptionService.InfoReceptionServiceFactory.get();
            service.dispatch(false );
            time = service.dispatchTime;
        }
    }

    @Override
    public void harderstats_setTimer(long time) {
        this.time = time;
    }
}
