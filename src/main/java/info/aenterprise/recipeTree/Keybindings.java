package info.aenterprise.recipeTree;

import org.lwjgl.input.Keyboard;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.fml.client.registry.ClientRegistry;

/**
 * Copyright (c) 2016, AEnterprise
 * http://www.aenterprise.info/
 */
public class Keybindings {
	public static final KeyBinding openGui = new KeyBinding("recipetree.key.open", KeyConflictContext.IN_GAME, KeyModifier.CONTROL, Keyboard.KEY_R, "Recipe Tree");

	public static void init() {
		ClientRegistry.registerKeyBinding(openGui);
	}
}
