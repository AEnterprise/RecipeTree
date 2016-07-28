package info.aenterprise.recipeTree.jei.hook;

import mezz.jei.api.recipe.IRecipeWrapper;

import java.awt.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Copyright (c) 2016, AEnterprise
 * http://www.aenterprise.info/
 */
public class SelectButton {
	@SideOnly(Side.CLIENT)
	private static final ResourceLocation checkmark = new ResourceLocation("recipetree", "textures/gui/greencheck.png");
	private static final int size = 8;

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
		mc.getTextureManager().bindTexture(checkmark);

		Tessellator tessellator = Tessellator.getInstance();
		VertexBuffer wr = tessellator.getBuffer();
		wr.begin(7, DefaultVertexFormats.POSITION_TEX);
		float  uScale = 1f / 0x8;
		float  vScale = 1f / 0x8;
		wr.pos(x    , y + 8, 1).tex(0 * uScale, (8 * vScale)).endVertex();
		wr.pos(x + 8, y + 8, 1).tex(8 * uScale, (8 * vScale)).endVertex();
		wr.pos(x + 8, y    , 1).tex(8 * uScale, (0 * vScale)).endVertex();
		wr.pos(x    , y    , 1).tex(0 * uScale, (0 * vScale)).endVertex();
		tessellator.draw();

		GlStateManager.disableAlpha();
		GlStateManager.enableBlend();
		RenderHelper.disableStandardItemLighting();
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, 8, 8);
	}

	public IRecipeWrapper getRecipe() {
		return recipe;
	}
}
