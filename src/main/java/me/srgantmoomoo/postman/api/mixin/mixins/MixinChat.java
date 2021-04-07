package me.srgantmoomoo.postman.api.mixin.mixins;

import me.srgantmoomoo.postman.client.module.ModuleManager;
import me.srgantmoomoo.postman.client.module.modules.render.CustomChat;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiNewChat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GuiNewChat.class)
public class MixinChat {

    // This is changing the background of the chat
    @Redirect(method={"drawChat"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/gui/GuiNewChat;drawRect(IIIII)V"))
    private void drawRectHook(int left, int top, int right, int bottom, int color) {
        Gui.drawRect(left, top, right, bottom, ModuleManager.isModuleEnabled("CustomChat") ? CustomChat.backColorInt : color);
    }

    @Inject(method = {"getChatHeight"}, at = @At("HEAD"), cancellable = true)
    public void getChatHeight(CallbackInfoReturnable<Integer> cir) {
        if (CustomChat.maxHeightInt != -1)
            cir.setReturnValue(CustomChat.maxHeightInt);
    }

    @Inject(method = {"getChatWidth"}, at = @At("HEAD"), cancellable = true)
    public void getChatWidth(CallbackInfoReturnable<Integer> cir) {
        if (CustomChat.maxWidthInt != -1)
            cir.setReturnValue(CustomChat.maxWidthInt);
    }

}

/*
    Ps: Minecraft plugin for intellij is hella based
 */