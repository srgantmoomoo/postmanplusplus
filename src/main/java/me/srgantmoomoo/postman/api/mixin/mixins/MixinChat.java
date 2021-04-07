package me.srgantmoomoo.postman.api.mixin.mixins;

import me.srgantmoomoo.postman.client.module.ModuleManager;
import me.srgantmoomoo.postman.client.module.modules.render.CustomChat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.renderer.GlStateManager;
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

    // Custom Size
    @Redirect(method={"drawChat"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/renderer/GlStateManager;translate(FFF)V"))
    private void customSize(float x, float y, float z) {
        GlStateManager.scale(CustomChat.customScaleVal, CustomChat.customScaleVal, 1f);
        GlStateManager.translate(   CustomChat.xTrans != -1 ? CustomChat.xTrans : x,
                                    CustomChat.yTrans != -1 ? -CustomChat.yTrans : y,
                                    z);
    }

    // Color
    @Redirect(method={"drawChat"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/gui/FontRenderer;drawStringWithShadow(Ljava/lang/String;FFI)I"))
    private int drawStringWithShadow(FontRenderer fontRenderer, String text, float x, float y, int color) {
        if (text.contains("postman")) {
            Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(text, x, y, CustomChat.postManColorInt);
        } else {
            Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(text, x, y, color);
        }
        return 0;
    }

    // This is for custom height
    @Inject(method = {"getChatHeight"}, at = @At("HEAD"), cancellable = true)
    public void getChatHeight(CallbackInfoReturnable<Integer> cir) {
        if (CustomChat.maxHeightInt != -1)
            cir.setReturnValue(CustomChat.maxHeightInt);
    }

    // This is for custom width
    @Inject(method = {"getChatWidth"}, at = @At("HEAD"), cancellable = true)
    public void getChatWidth(CallbackInfoReturnable<Integer> cir) {
        if (CustomChat.maxWidthInt != -1)
            cir.setReturnValue(CustomChat.maxWidthInt);
    }

}

/*
    Ps: Minecraft plugin for intellij is hella based
 */