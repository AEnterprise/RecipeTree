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
	public static void update(RecipesGui recipesGui) {
		if (!shouldHook)
			return;
		List<RecipeLayout> list = (List<RecipeLayout>) ReflectionHelper.getValue(recipesGui, "recipeLayouts");
		if (cache.size() != list.size() || list.size() != buttons.size() || !cache.containsAll(list))
			updateCache(list);
		for (SelectButton button: buttons) {
			button.render(recipesGui.mc);
		}
	}



	private static void updateCache(List<RecipeLayout> list) {
		cache = list;
		buttons.clear();
		for (RecipeLayout layout: list) {
			IRecipeCategory category = (IRecipeCategory) ReflectionHelper.getValue(layout, "recipeCategory");
			IRecipeWrapper recipe = (IRecipeWrapper) ReflectionHelper.getValue(layout, "recipeWrapper");
			buttons.add(new SelectButton(layout.getPosX() + category.getBackground().getWidth() + 2, layout.getPosY() + category.getBackground().getHeight() - 8, recipe));
		}
	}

}
