package info.aenterprise.recipeTree;

import info.aenterprise.recipeTree.gui.GuiRecipeTree;
import info.aenterprise.recipeTree.jei.hook.RecipeGuiHook;
import mezz.jei.gui.RecipesGui;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import net.minecraft.client.Minecraft;
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
	@SubscribeEvent(priority = EventPriority.LOWEST) //making sure we are drawing after JEI
	public void draw(GuiScreenEvent.BackgroundDrawnEvent event) {
		if (event.getGui() instanceof RecipesGui) {
			RecipeGuiHook.update((RecipesGui) event.getGui());

		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority = EventPriority.HIGH) //making sure we are catching it before JEI does
	public void click(GuiScreenEvent.MouseInputEvent.Pre event) {
		if (Mouse.getEventButton() > -1 && Mouse.getEventButtonState()) {
			int x = Mouse.getEventX() * event.getGui().width / event.getGui().mc.displayWidth;
			int y = event.getGui().height - Mouse.getEventY() * event.getGui().height / event.getGui().mc.displayHeight - 1;
			if (RecipeGuiHook.click(x, y))
				event.setCanceled(true);
		}

	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void guiKey(GuiScreenEvent.KeyboardInputEvent.Pre event) {
		int code = Keyboard.getEventKey();
		if (RecipeGuiHook.shouldHook && Minecraft.getMinecraft().currentScreen instanceof GuiRecipeTree && (Minecraft.getMinecraft().gameSettings.keyBindInventory.isActiveAndMatches(code) || code == Keyboard.KEY_ESCAPE))
			RecipeGuiHook.onTreeGuiClose();
	}
}
