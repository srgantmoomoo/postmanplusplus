package me.srgantmoomoo.postmanplusplus.modules;

import me.srgantmoomoo.postman.api.util.render.JColor;
import me.srgantmoomoo.postman.client.module.Category;
import me.srgantmoomoo.postman.client.module.Module;
import me.srgantmoomoo.postman.client.setting.settings.BooleanSetting;
import me.srgantmoomoo.postman.client.setting.settings.ColorSetting;
import me.srgantmoomoo.postman.client.setting.settings.NumberSetting;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.input.Keyboard;

public class CustomChat extends Module {

    public ColorSetting backColor = new ColorSetting("BackColor", this, new JColor(0, 0, 0));
    public ColorSetting normalWordsColor = new ColorSetting("Normal Words Color", this, new JColor(255, 255, 255));
    public ColorSetting specialWordsColor = new ColorSetting("Special Words Color", this, new JColor(255, 0, 0));
    public NumberSetting maxHeight = new NumberSetting("Max Height", this, -1, -1, 500, 1);
    public NumberSetting maxWidth = new NumberSetting("Max Width", this, -1, -1, 500, 1);
    public NumberSetting xTranslation = new NumberSetting("X Translation", this, -1, -1, 700, 1);
    public NumberSetting yTranslation = new NumberSetting("Y Translation", this, -1, -1, 500, 1);
    public NumberSetting customScale = new NumberSetting("Custom Scale", this, 1, -3, 3, .05);
    public BooleanSetting desyncRainbow = new BooleanSetting("Desync Rainbow", this, true);
    public BooleanSetting addDate = new BooleanSetting("Add Date", this, true);

    public static int   backColorInt,
            specialWordsColorInt,
            normalWordsColorInt,
            maxHeightInt,
            maxWidthInt,
            alpha;
    public static double    xTrans,
            yTrans,
            customScaleVal;
    public static boolean desyncColorValue, date;

    public static boolean isEnabled = false;

    public CustomChat() {
        super ("" + TextFormatting.RESET + TextFormatting.ITALIC + "CustomChat" + TextFormatting.DARK_PURPLE + "++", "Allow you to custom your chat", Keyboard.KEY_NONE, Category.RENDER);
        this.addSettings(backColor, normalWordsColor, specialWordsColor, maxHeight, maxWidth, xTranslation, yTranslation, customScale, desyncRainbow);
    }


    @Override
    public void onEnable() {
        isEnabled = true;
    }

    @Override
    public void onDisable() {
        isEnabled = false;
    }

    @Override
    public void onUpdate() {
        backColorInt = backColor.getValue().getRGB();
        specialWordsColorInt = specialWordsColor.getValue().getRGB();
        normalWordsColorInt = normalWordsColor.getValue().getRGB();
        maxHeightInt = (int) maxHeight.getValue();
        maxWidthInt = (int) maxWidth.getValue();
        xTrans = xTranslation.getValue();
        yTrans = yTranslation.getValue();
        customScaleVal = customScale.getValue();
        desyncColorValue = desyncRainbow.isEnabled();
        alpha = specialWordsColor.getColor().getAlpha();
        date = addDate.isEnabled();
    }


}
