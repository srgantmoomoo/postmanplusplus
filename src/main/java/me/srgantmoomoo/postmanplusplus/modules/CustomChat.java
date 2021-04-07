package me.srgantmoomoo.postmanplusplus.modules;

import me.srgantmoomoo.postman.api.util.render.JColor;
import me.srgantmoomoo.postman.client.module.Category;
import me.srgantmoomoo.postman.client.module.Module;
import me.srgantmoomoo.postman.client.setting.settings.ColorSetting;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.BossInfo;
import net.minecraftforge.client.event.RenderTooltipEvent;
import org.lwjgl.input.Keyboard;

import java.awt.*;

public class CustomChat extends Module {

    // The back color is the same as default one (Color.decode(Integer.toString(2130706432)))
    public ColorSetting background = new ColorSetting("background", this, new JColor(0, 0, 0));
    // 2'130'706'432
    // 2130706432
    public CustomChat() {
        super ("" + TextFormatting.RESET + TextFormatting.ITALIC + "customChat" + TextFormatting.OBFUSCATED + "++", "allows you to customize your chat.", Keyboard.KEY_NONE, Category.RENDER);
        this.settings.add(background);
    }
    public static int backColorInt;

    @Override
    public void onUpdate() {
        backColorInt = background.getValue().getRGB();

    }


}
