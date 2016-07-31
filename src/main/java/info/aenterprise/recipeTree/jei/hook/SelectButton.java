package info.aenterprise.recipeTree.jei.hook;

import mezz.jei.api.recipe.IRecipeWrapper;

import java.awt.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.config.GuiUtils;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Copyright (c) 2016, AEnterprise
 * http://www.aenterprise.info/
 */
public class SelectButton {
	@SideOnly(Side.CLIENT)
	private static final ResourceLocation gui = new ResourceLocation("recipetree", "textures/gui/overlay.png");

	private int x, y;
	private IRecipeWrapper recipe;

	public SelectButton(int x, int y, IRecipeWrapper recipe) {
		this.x = x;
		this.y = y;
		this.recipe = recipe;
	}

	@SideOnly(Side.CLIENT)
	public void render(Minecraft mc) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.enableAlpha();
		GlStateManager.disableBlend();
		RenderHelper.enableGUIStandardItemLighting();
		mc.getTextureManager().bindTexture(gui);

		GuiUtils.drawTexturedModalRect(x, y, 20, 230, 8, 8, 1);

		GlStateManager.disableAlpha();
		GlStateManager.enableBlend();
		RenderHelper.disableStandardItemLighting();
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, 16, 16);
	}

	public IRecipeWrapper getRecipe() {
		return recipe;
	}
}
