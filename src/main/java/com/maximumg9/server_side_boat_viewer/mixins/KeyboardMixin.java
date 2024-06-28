package com.maximumg9.server_side_boat_viewer.mixins;

import com.maximumg9.server_side_boat_viewer.ServerSideBoatViewer;
import net.minecraft.client.*;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Keyboard.class)
public abstract class KeyboardMixin {
    @Shadow
    @Final
    private MinecraftClient client;

    @Shadow
    protected abstract void debugWarn(String key, Object... args);

    @Inject(method = "processF3", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/ChatHud;addMessage(Lnet/minecraft/text/Text;)V", ordinal = 0), slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=debug.pause.help")))
    private void addValueChoice(int key, CallbackInfoReturnable<Boolean> cir) {
        this.client.inGameHud.getChatHud().addMessage(Text.of("F3 + Y = Turn on rendering of server versions of boats (singleplayer only)"));
    }

    @Inject(method = "processF3", at = @At(value = "TAIL"), cancellable = true)
    private void addKeybind(int key, CallbackInfoReturnable<Boolean> cir) {
        if (key == GLFW.GLFW_KEY_Y) {
            ServerSideBoatViewer.renderingServerBoats = !ServerSideBoatViewer.renderingServerBoats;
            this.debugWarn(ServerSideBoatViewer.renderingServerBoats ? "Activated Server Boat Rendering" : "Deactivated Server Boat Rendering");
            cir.setReturnValue(true);
        }
    }
}
