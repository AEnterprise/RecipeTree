package info.aenterprise.recipeTree.gui;

import info.aenterprise.recipeTree.tree.NodeData;
import info.aenterprise.recipeTree.tree.TreeNode;
import info.aenterprise.recipeTree.util.Log;
import mezz.jei.api.recipe.IRecipeWrapper;

import java.util.List;
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

	private TreeNode<NodeData> tree;
	private TreeNode<NodeData> selected = null;
	private ItemStack expectedOutput = null;

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
			tree = new TreeNode<>(new NodeData((ItemStack) recipe.getOutputs().get(0)));
			for (Object o : recipe.getInputs()) {
				ItemStack stack = null;
				if (o instanceof ItemStack) {
					stack = (ItemStack) o;
				} else if (o instanceof List) {
					stack = (ItemStack) ((List) o).get(0);
				}
				if (stack != null) {
					TreeNode<NodeData> treeNode = new TreeNode<>(new NodeData(stack));
					tree.addBranch(treeNode);
					selected = treeNode;
					expectedOutput = stack;
				}
			}
		} else {
			boolean found = false;
			for (Object o : recipe.getOutputs()) {
				if (o instanceof ItemStack && ((ItemStack) o).isItemEqual(expectedOutput)) {
					branchOut(recipe.getInputs());
					found = true;
					break;
				}
			}
			if (!found) {
				Log.info("Got a recipe for " + recipe.getOutputs().get(0) + "but expected a recipe for " + expectedOutput);
			}
		}
		updateTree();
	}

	private void branchOut(List inputs) {
		TreeNode<NodeData> selection = selected;
		for (Object o : inputs) {
			ItemStack stack = null;
			if (o instanceof ItemStack)
				stack = (ItemStack) o;
			if (o instanceof List)
				stack = (ItemStack) ((List) o).get(0);
			if (stack != null) {
				TreeNode<NodeData> treeNode = new TreeNode<NodeData>(new NodeData(stack));
				selection.addBranch(treeNode);
				selected = treeNode;
				expectedOutput = stack;
			}
		}
	}

	private void updateTree() {
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
