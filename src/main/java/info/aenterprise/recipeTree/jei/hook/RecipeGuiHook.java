package info.aenterprise.recipeTree.jei.hook;

import info.aenterprise.recipeTree.gui.GuiRecipeTree;
import info.aenterprise.recipeTree.util.ReflectionHelper;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.gui.RecipeLayout;
import mezz.jei.gui.RecipesGui;

import java.util.ArrayList;
import java.util.List;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Copyright (c) 2016, AEnterprise
 * http://www.aenterprise.info/
 */
public class RecipeGuiHook {

	public static boolean shouldHook = false;
	private static GuiRecipeTree gui;
	private static List<RecipeLayout> cache = new ArrayList<>();
	private static List<SelectButton> buttons = new ArrayList<>();

	public static void onTreeGuiOpen(GuiRecipeTree gui) {
		shouldHook = true;
		RecipeGuiHook.gui = gui;
	}

	public static void onTreeGuiClose() {
		shouldHook = false;
	}

	@SideOnly(Side.CLIENT)
	@SuppressWarnings("unchecked")
	public static void render(RecipesGui recipesGui) {
		if (!shouldHook)
			return;
		for (SelectButton button: buttons) {
			button.render(recipesGui.mc);
		}
	}


	@SuppressWarnings("unchecked")
	public static void updateCache(RecipesGui gui) {
		cache = (List<RecipeLayout>) ReflectionHelper.getValue(gui, "recipeLayouts");
		buttons.clear();
		for (RecipeLayout layout : cache) {
			IRecipeCategory category = (IRecipeCategory) ReflectionHelper.getValue(layout, "recipeCategory");
			IRecipeWrapper recipe = (IRecipeWrapper) ReflectionHelper.getValue(layout, "recipeWrapper");
			buttons.add(new SelectButton(layout.getPosX() + category.getBackground().getWidth() + 2, layout.getPosY() + category.getBackground().getHeight() - 8, recipe));
		}
	}

	@SideOnly(Side.CLIENT)
	public static boolean click(int x, int y) {
		if (!shouldHook)
			return false;
		for (SelectButton button : buttons) {
			if (button.getBounds().contains(x, y)) {
				gui.open();
				gui.recieveRecipe(button.getRecipe());
				return true;
			}
		}
		return false;
	}

}
