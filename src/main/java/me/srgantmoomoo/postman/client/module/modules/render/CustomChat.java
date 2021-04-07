package me.srgantmoomoo.postman.client.module.modules.render;

import me.srgantmoomoo.postman.api.util.render.JColor;
import me.srgantmoomoo.postman.client.module.Category;
import me.srgantmoomoo.postman.client.module.Module;
import me.srgantmoomoo.postman.client.setting.settings.ColorSetting;
import me.srgantmoomoo.postman.client.setting.settings.NumberSetting;
import net.minecraft.world.BossInfo;
import net.minecraftforge.client.event.RenderTooltipEvent;
import org.lwjgl.input.Keyboard;

import java.awt.*;

public class CustomChat extends Module {

    // The back color is the same as default one (Color.decode(Integer.toString(2130706432)))
    public ColorSetting backColor = new ColorSetting("BackColor", this, new JColor(0, 0, 0));
    public NumberSetting maxHeight = new NumberSetting("Max Height", this, 200, -1, 1000, 1);
    public NumberSetting maxWidth = new NumberSetting("Max Width", this, 200, -1, 1000, 1);

    public CustomChat() {
        super ("CustomChat", "Allow you to custom your chat", Keyboard.KEY_NONE, Category.RENDER);
        this.addSettings(backColor, maxHeight, maxWidth);
    }
    public static int backColorInt,
                      maxHeightInt,
                      maxWidthInt;

    @Override
    public void onUpdate() {
        backColorInt = backColor.getValue().getRGB();
        maxHeightInt = (int) maxHeight.getValue();
        maxWidthInt = (int) maxWidth.getValue();
    }


}
