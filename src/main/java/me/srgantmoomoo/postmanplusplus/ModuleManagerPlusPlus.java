package me.srgantmoomoo.postmanplusplus;

import me.srgantmoomoo.postman.client.module.ModuleManager;
import me.srgantmoomoo.postmanplusplus.modules.InstantMine;
import me.srgantmoomoo.postmanplusplus.modules.SkyColor;
import me.srgantmoomoo.postmanplusplus.modules.TargetHud;

public class ModuleManagerPlusPlus {
	
	public ModuleManagerPlusPlus() {
		ModuleManager.modules.add(new SkyColor());
		ModuleManager.modules.add(new TargetHud());	
		ModuleManager.modules.add(new InstantMine());
	}

}