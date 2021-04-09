package me.srgantmoomoo.mixin.mixins;

import me.srgantmoomoo.postman.client.setting.settings.ColorSetting;
import me.srgantmoomoo.postmanplusplus.modules.CustomChat;
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
        // If enabled
        if (CustomChat.isEnabled) {
            // If it's not the scrollbar
            if (left != 0 && top != 0)
                // Draw
                Gui.drawRect(left, top, right, bottom, CustomChat.backColorInt);
        } else Gui.drawRect(left, top, right, bottom, color);
    }

    // Custom Size
    @Redirect(method={"drawChat"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/renderer/GlStateManager;translate(FFF)V"))
    private void customSize(float x, float y, float z) {
        // If enable
        if (CustomChat.isEnabled) {
            // Custom Scale
            GlStateManager.scale(CustomChat.customScaleVal, CustomChat.customScaleVal, 1f);
            // Translate it
            GlStateManager.translate(CustomChat.xTrans != -1 ? CustomChat.xTrans : x,
                    CustomChat.yTrans != -1 ? -CustomChat.yTrans : y,
                    z);
        } else {
            GlStateManager.translate(x, y, z);
        }
    }

    // Color
    @Redirect(method={"drawChat"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/gui/FontRenderer;drawStringWithShadow(Ljava/lang/String;FFI)I"))
    private int drawStringWithShadow(FontRenderer fontRenderer, String text, float x, float y, int color) {
        // If enable
        if (CustomChat.isEnabled) {
            // Display text
            displayText(text, x, y);
        } else Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(text, x, y, color);
        return 0;
    }

    private void displayText(String word, float x, float y) {
        // Split everything by specials
        String[] special = word.split("/s");
        int width = 0;
        int rainbowColor = 0;
        // Iterate every words
        for(int i = 0; i < special.length; i++) {
            // If it's a normal text
            if (i % 2 == 0) {
                // Normal
                Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(special[i], x + width, y, CustomChat.normalWordsColorInt);
                width += Minecraft.getMinecraft().fontRenderer.getStringWidth(special[i]);
            }
            else {
                // If we want normal rainbow
                if (!CustomChat.desyncColorValue) {
                    // Color with rainbow
                    Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(special[i], x + width, y, CustomChat.specialWordsColorInt);
                    width += Minecraft.getMinecraft().fontRenderer.getStringWidth(special[i]);
                }
                else {
                    // Else, desync rainbow
                    for(String character : special[i].split("")) {
                        // Color 1 character
                        Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(character, x + width, y, ColorSetting.getRainbow(rainbowColor, CustomChat.alpha).getRGB());
                        // Add width
                        width += Minecraft.getMinecraft().fontRenderer.getStringWidth(character);
                        // Add rainbow
                        rainbowColor += 1;
                    }
                }
            }
        }
    }

    // This is for custom height
    @Inject(method = {"getChatHeight"}, at = @At("HEAD"), cancellable = true)
    public void getChatHeight(CallbackInfoReturnable<Integer> cir) {
        if (CustomChat.isEnabled) {
            if (CustomChat.maxHeightInt != -1)
                cir.setReturnValue(CustomChat.maxHeightInt);
        }
    }

    // This is for custom width
    @Inject(method = {"getChatWidth"}, at = @At("HEAD"), cancellable = true)
    public void getChatWidth(CallbackInfoReturnable<Integer> cir) {
        if (CustomChat.isEnabled) {
            if (CustomChat.maxWidthInt != -1)
                cir.setReturnValue(CustomChat.maxWidthInt);
        }
    }

}

/*
    Ps: Minecraft plugin for intellij is hella based
 */
