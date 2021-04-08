package me.srgantmoomoo.postman.api.mixin.mixins;

/*
    - Fixed bug of some random lines appears
 */
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
        if (CustomChat.isEnabled) {
            if (left != 0 && top != 0)
                Gui.drawRect(left, top, right, bottom, CustomChat.backColorInt);
        } else Gui.drawRect(left, top, right, bottom, color);
    }

    // Custom Size
    @Redirect(method={"drawChat"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/renderer/GlStateManager;translate(FFF)V"))
    private void customSize(float x, float y, float z) {
        if (CustomChat.isEnabled) {
            GlStateManager.scale(CustomChat.customScaleVal, CustomChat.customScaleVal, 1f);
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
        if (CustomChat.isEnabled) {
            displayText(text, x, y, CustomChat.normalWordsColorInt, CustomChat.specialWordsColorInt, CustomChat.desyncColorValue);
        } else Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(text, x, y, color);
        return 0;
    }

    private void displayText(String word, float x, float y, int normalColor, int specialColor, boolean desyncRainbow) {
        String[] special = word.split("/s");
        int width = 0;
        int rainbowColor = 0;
        for(int i = 0; i < special.length; i++) {
            if (i % 2 == 0) {
                Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(special[i], x + width, y, normalColor);
                width += Minecraft.getMinecraft().fontRenderer.getStringWidth(special[i]);
            }
            else {
                if (!desyncRainbow) {
                    Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(special[i], x + width, y, specialColor);
                    width += Minecraft.getMinecraft().fontRenderer.getStringWidth(special[i]);
                }
                else {

                    for(String character : special[i].split("(?<=\\G.)")) {
                        Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(character, x + width, y, ColorSetting.getRainbow(rainbowColor, CustomChat.alpha).getRGB());
                        width += Minecraft.getMinecraft().fontRenderer.getStringWidth(character);
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