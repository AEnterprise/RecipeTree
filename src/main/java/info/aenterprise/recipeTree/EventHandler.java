package info.aenterprise.recipeTree;

import info.aenterprise.recipeTree.gui.GuiRecipeTree;
import info.aenterprise.recipeTree.jei.hook.RecipeGuiHook;
import mezz.jei.gui.RecipesGui;

import org.lwjgl.input.Keyboard;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Copyright (c) 2016, AEnterprise
 * http://www.aenterprise.info/
 */
public class EventHandler {

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void keyInput(InputEvent.KeyInputEvent event) {
		if (Keybindings.openGui.isActiveAndMatches(Keyboard.getEventKey())) {
			GuiRecipeTree gui = new GuiRecipeTree();
			gui.open();
			RecipeGuiHook.onTreeGuiOpen(gui);
		}
	}

	@SideOnly(Side.CLIENT)
	@SuppressWarnings("unchecked")
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void draw(GuiScreenEvent.BackgroundDrawnEvent event) {
		if (event.getGui() instanceof RecipesGui) {
			RecipeGuiHook.update((RecipesGui) event.getGui());

		}
	}
}
