package com.maximumg9.server_side_boat_viewer.mixins;

import com.maximumg9.server_side_boat_viewer.ServerSideBoatViewer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.*;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
    @Shadow
    @Final
    private MinecraftClient client;

    @Inject(method = "renderEntity", at = @At("HEAD"))
    public void renderEntity(Entity entity, double cameraX, double cameraY, double cameraZ, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, CallbackInfo ci) {
        if (entity.getType() != EntityType.BOAT || !ServerSideBoatViewer.renderingServerBoats) {
            return;
        }
        IntegratedServer server = MinecraftClient.getInstance().getServer();
        if ((server == null) || (MinecraftClient.getInstance().player == null)) {
            return;
        }
        ServerWorld world = server.getPlayerManager().getPlayer(MinecraftClient.getInstance().player.getUuid()).getServerWorld();
        Entity ssentity = world.getEntity(entity.getUuid());
        if (ssentity == null) {
            return;
        }
        double d = ssentity.getX();
        double e = ssentity.getY();
        double f = ssentity.getZ();
        float g = entity.getYaw(tickDelta);
        this.client.getEntityRenderManager().render(ssentity, d - cameraX, e - cameraY, f - cameraZ, g, tickDelta, matrices, vertexConsumers, this.client.getEntityRenderManager().getLight(entity, tickDelta));
    }
}
