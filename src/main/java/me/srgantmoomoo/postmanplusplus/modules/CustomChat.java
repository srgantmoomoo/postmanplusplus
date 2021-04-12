package me.srgantmoomoo.postmanplusplus.modules;

import me.srgantmoomoo.postman.api.util.render.JColor;
import me.srgantmoomoo.postman.client.module.Category;
import me.srgantmoomoo.postman.client.module.Module;
import me.srgantmoomoo.postman.client.setting.settings.BooleanSetting;
import me.srgantmoomoo.postman.client.setting.settings.ColorSetting;
import me.srgantmoomoo.postman.client.setting.settings.NumberSetting;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

import java.util.Date;

public class CustomChat extends Module {

    // Settings
    public ColorSetting backColor = new ColorSetting("backColor", this, new JColor(0, 0, 0));
    public ColorSetting normalWordsColor = new ColorSetting("normalWordsColor", this, new JColor(255, 255, 255));
    public ColorSetting specialWordsColor = new ColorSetting("specialWordsColor", this, new JColor(255, 0, 0));
    public NumberSetting maxHeight = new NumberSetting("maxHeight", this, -1, -1, 500, 1);
    public NumberSetting maxWidth = new NumberSetting("maxWidth", this, -1, -1, 500, 1);
    public NumberSetting xTranslation = new NumberSetting("xTranslation", this, -1, -1, 700, 1);
    public NumberSetting yTranslation = new NumberSetting("yTranslation", this, -1, -1, 500, 1);
    public NumberSetting customScale = new NumberSetting("customScale", this, 1, -3, 3, .05);
    public BooleanSetting desyncRainbow = new BooleanSetting("desyncRainbow", this, true);
    public BooleanSetting addDate = new BooleanSetting("addDate", this, true);

    // Static variable
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

    // Init
    public CustomChat() {
        super ("" + TextFormatting.RESET + TextFormatting.ITALIC + "customChat" + TextFormatting.OBFUSCATED + "++", "allows you to customize chat.", Keyboard.KEY_NONE, Category.RENDER);
        this.addSettings(backColor, normalWordsColor, specialWordsColor, maxHeight, maxWidth, xTranslation, yTranslation, customScale, desyncRainbow, addDate);
    }

    // Enable
    public void onEnable() {
        isEnabled = true;
        if (addDate.isEnabled())
            MinecraftForge.EVENT_BUS.register(this);
    }

    // Disable
    public void onDisable() {
        isEnabled = false;
        if (addDate.isEnabled())
            MinecraftForge.EVENT_BUS.unregister(this);
    }

    // Update
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

    // Message Recive
    @SubscribeEvent
    public void messageRecive(ClientChatReceivedEvent event) {
        // Add date
        Date date = new Date();
        event.setMessage(new TextComponentString("/s<" + date.toString().split(" ")[3] + ">/s " + event.getMessage().getFormattedText()));
    }


}
