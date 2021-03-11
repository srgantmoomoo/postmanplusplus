package me.srgantmoomoo.postmanplusplus.modules;

import org.lwjgl.input.Keyboard;

import me.srgantmoomoo.postman.api.util.render.JColor;
import me.srgantmoomoo.postman.client.module.Category;
import me.srgantmoomoo.postman.client.module.Module;
import me.srgantmoomoo.postman.client.setting.settings.BooleanSetting;
import me.srgantmoomoo.postman.client.setting.settings.ColorSetting;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SkyColor extends Module {
	public BooleanSetting fog = new BooleanSetting("fog", this, false);
	public ColorSetting colorJ = new ColorSetting("color", this, new JColor(255, 255, 255, 255));

    public SkyColor() {
        super("skyColor" + TextFormatting.LIGHT_PURPLE + "++", "colors the sky", Keyboard.KEY_NONE, Category.RENDER);
        this.addSettings(fog, colorJ);
    }
    
    @SubscribeEvent
    public void onFogColorRender(EntityViewRenderEvent.FogColors event) {
        JColor color = colorJ.getValue();
        event.setRed(color.getRed() / 255f);
        event.setGreen(color.getGreen() / 255f);
        event.setBlue(color.getBlue() / 255f);
    }

    @SubscribeEvent
    public void fog(EntityViewRenderEvent.FogDensity event) {
        if (!fog.isEnabled()) {
            event.setDensity(0);
            event.setCanceled(true);
        }
    }

    public void onEnable() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void onDisable() {
        MinecraftForge.EVENT_BUS.unregister(this);
    }
}
