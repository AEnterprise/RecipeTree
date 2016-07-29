package info.aenterprise.recipeTree.gui;

import info.aenterprise.recipeTree.tree.Branch;
import info.aenterprise.recipeTree.tree.RecipeLeaf;
import info.aenterprise.recipeTree.tree.Tree;
import info.aenterprise.recipeTree.util.Log;
import mezz.jei.api.recipe.IRecipeWrapper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Copyright (c) 2016, AEnterprise
 * http://www.aenterprise.info/
 */
@SideOnly(Side.CLIENT)
public class GuiRecipeTree extends GuiContainer {
	private static final ResourceLocation OVERLAY = new ResourceLocation("recipetree", "textures/gui/overlay.png");

	private Tree<RecipeLeaf> tree;

	public GuiRecipeTree() {
		super(new DummyContainer());
		this.xSize = 200;
		this.ySize = 200;
	}

	public void open() {
		mc = Minecraft.getMinecraft();
		mc.displayGuiScreen(this);
		Log.info("opening");
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		Minecraft.getMinecraft().getTextureManager().bindTexture(OVERLAY);
		drawTexturedModalRect((this.width - this.xSize) / 2, (this.height - this.ySize) / 2, 0, 0, 200, 200);
	}

	public void recieveRecipe(IRecipeWrapper recipe) {
		if (tree == null) {
			tree = new Tree<>(new RecipeLeaf((ItemStack) recipe.getOutputs().get(0)));
		}
		for (Object o : recipe.getInputs()) {
			if (o instanceof ItemStack) {
				tree.addBranch(new Branch<RecipeLeaf>(new RecipeLeaf((ItemStack) o)));
			}
		}
		tree.printStructure();
	}

	private static class DummyContainer extends Container {

		public DummyContainer() {
			addSlotToContainer(new Slot(new InventoryBasic("", false, 1), 0, -100, -100));
		}

		@Override
		public boolean canInteractWith(EntityPlayer playerIn) {
			return false;
		}
	}
}
