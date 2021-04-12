package me.srgantmoomoo.postman.client.module.modules.player;

import java.util.Arrays;

import org.lwjgl.input.Keyboard;

import me.srgantmoomoo.postman.client.module.Category;
import me.srgantmoomoo.postman.client.module.Module;
import me.srgantmoomoo.postman.client.setting.settings.ModeSetting;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ChatSuffix extends Module {
	public ModeSetting modes = new ModeSetting("mode", this, "plusplus", "plusplus", "++");
	
	public ChatSuffix() {
		super ("chatSuffix", "adds postman suffix to all of ur chat msg's.", Keyboard.KEY_NONE, Category.PLAYER);
		this.addSettings(modes);
	}

	@SubscribeEvent
	public void onChat(final ClientChatEvent event) {
		if(toggled) {
			for (final String s : Arrays.asList("/", ".", "-", ",", ":", ";", "'", "\"", "+", "\\", "@", "#")) {
				if (event.getMessage().startsWith(s)) return;
			}																													//plusplus
			if(modes.is("plusplus")) event.setMessage(event.getMessage() + " " + "\u1566\u1D18\u1D0F\u0455\u1D1B\u0E95\u1D00\u03B7\u1D18\u029F\u1D1C\u0455\u1D18\u029F\u1D1C\u0455 \u0020\u0455\u1D1B\u044F\u1D0F\u03B7\u0262\u1564");
			else if(modes.is("++")) event.setMessage(event.getMessage() + " " + "\u1566\u1D18\u1D0F\u0455\u1D1B\u0E95\u1D00\u03B7\u002b\u002b \u0020\u0455\u1D1B\u044F\u1D0F\u03B7\u0262\u1564");
			// \u1566 \u1D18 \u1D0F \u0455 \u1D1B \u0E95 \u1D00 \u03B7 \u0020 \u0455 \u1D1B \u044F \u1D0F \u03B7 \u0262 \u1564 á•¦á´˜á´�Ñ•á´›àº•á´€Î· Ñ•á´›Ñ�á´�Î·É¢á•¤
		}
	}
	
	public void onEnable() {
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	public void onDisable() {
		MinecraftForge.EVENT_BUS.unregister(this);
	}
}