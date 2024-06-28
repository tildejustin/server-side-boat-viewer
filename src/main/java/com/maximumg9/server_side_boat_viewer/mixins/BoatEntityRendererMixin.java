package com.maximumg9.server_side_boat_viewer.mixins;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.BoatEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.vehicle.BoatEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(BoatEntityRenderer.class)
public abstract class BoatEntityRendererMixin {
    @ModifyArgs(
            method = "render(Lnet/minecraft/entity/vehicle/BoatEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/model/BoatEntityModel;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;IIFFFF)V")
    )
    private void modifyServerSideBoatColor(Args args, BoatEntity boatEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        args.set(4, boatEntity.getServer() == null ? 1.0F : 0.5F);
        args.set(5, boatEntity.getServer() == null ? 1.0F : 0.5F);
        args.set(6, boatEntity.getServer() == null ? 1.0F : 0.5F);
    }
}
