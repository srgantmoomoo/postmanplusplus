package me.srgantmoomoo.postmanplusplus.modules;

import me.srgantmoomoo.postman.api.util.render.JColor;
import me.srgantmoomoo.postman.client.module.Category;
import me.srgantmoomoo.postman.client.module.Module;
import me.srgantmoomoo.postman.client.setting.settings.BooleanSetting;
import me.srgantmoomoo.postman.client.setting.settings.ColorSetting;
import me.srgantmoomoo.postman.client.setting.settings.NumberSetting;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.BossInfo;
import net.minecraftforge.client.event.RenderTooltipEvent;
import org.lwjgl.input.Keyboard;

import java.awt.*;

public class CustomChat extends Module {

    // The back color is the same as default one (Color.decode(Integer.toString(2130706432)))
    public ColorSetting backColor = new ColorSetting("BackColor", this, new JColor(0, 0, 0));
    public ColorSetting postmanColor = new ColorSetting("PostMan color", this, new JColor(0, 0, 0));
    public NumberSetting maxHeight = new NumberSetting("Max Height", this, -1, -1, 500, 1);
    public NumberSetting maxWidth = new NumberSetting("Max Width", this, -1, -1, 500, 1);
    public NumberSetting xTranslation = new NumberSetting("X Translation", this, -1, -1, 700, 1);
    public NumberSetting yTranslation = new NumberSetting("Y Translation", this, -1, -1, 500, 1);
    public NumberSetting customScale = new NumberSetting("Custom Scale", this, 1, -3, 3, .05);

    public CustomChat() {
        super ("" + TextFormatting.RESET + TextFormatting.ITALIC + "CustomChat" + TextFormatting.DARK_PURPLE + "++", "Allow you to custom your chat", Keyboard.KEY_NONE, Category.RENDER);
        this.addSettings(backColor, postmanColor, maxHeight, maxWidth, xTranslation, yTranslation, customScale);
    }
    public static int   backColorInt,
                        postManColorInt,
                        maxHeightInt,
                        maxWidthInt;
    public static double    xTrans,
                            yTrans,
                            customScaleVal;

    @Override
    public void onUpdate() {
        backColorInt = backColor.getValue().getRGB();
        postManColorInt = postmanColor.getValue().getRGB();
        maxHeightInt = (int) maxHeight.getValue();
        maxWidthInt = (int) maxWidth.getValue();
        xTrans = xTranslation.getValue();
        yTrans = yTranslation.getValue();
        customScaleVal = customScale.getValue();
    }


}
