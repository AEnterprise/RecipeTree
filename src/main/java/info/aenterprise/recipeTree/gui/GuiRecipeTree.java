package info.aenterprise.recipeTree.gui;

import info.aenterprise.recipeTree.tree.NodeData;
import info.aenterprise.recipeTree.tree.TreeNode;
import info.aenterprise.recipeTree.util.Log;
import mezz.jei.api.recipe.IRecipeWrapper;

import java.util.ArrayList;
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
	private static final ResourceLocation OVERLAY = new ResourceLocation("recipetree", "textures/gui/gui.png");

	private TreeNode<NodeData> root;
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

		if (root != null) {
			drawTexturedModalRect(root.getData().getX(), root.getData().getY(), 26, 201, 20, 20);
			for (TreeNode<NodeData> node : root) {
				drawTexturedModalRect(node.getData().getX(), node.getData().getY(), 26, 201, 20, 20);
			}
		}
	}

	public void recieveRecipe(IRecipeWrapper recipe) {
		if (root == null) {
			root = new TreeNode<>(new NodeData((ItemStack) recipe.getOutputs().get(0)));
			for (Object o : recipe.getInputs()) {
				ItemStack stack = null;
				if (o instanceof ItemStack) {
					stack = (ItemStack) o;
				} else if (o instanceof List) {
					stack = (ItemStack) ((List) o).get(0);
				}
				if (stack != null) {
					TreeNode<NodeData> treeNode = new TreeNode<>(new NodeData(stack));
					root.addBranch(treeNode);
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
				TreeNode<NodeData> treeNode = new TreeNode<>(new NodeData(stack));
				selection.addBranch(treeNode);
				selected = treeNode;
				expectedOutput = stack;
			}
		}
	}

	private void updateTree() {
		NodeData data = root.getData();
		data.setPos(50, 50);
		updateNodes(root.getSubNodes());
		root.printStructure();
	}

	private void updateNodes(List<TreeNode<NodeData>> list) {
		List<TreeNode<NodeData>> next = new ArrayList<>();
		for (TreeNode<NodeData> node : list) {
			next.addAll(node.getSubNodes());
			NodeData data = node.getData();
			TreeNode<NodeData> parent = node.getParent();
			int x = parent.getData().getX() + ((parent.getSubNodes().indexOf(node) + 1) * (36 * (node.getNumBranches() + 1)));
			x += node.getNumBranches() * 18;
			int y = parent.getData().getY() + 36;
			data.setPos(x, y);
			//data.setPos((parent.getData().getX() - (parent.getSubNodes().size() * (36 * (node.getMostChilds()+1))) / 2) + ((parent.getSubNodes().indexOf(node) + 1) * (36 * (node.getNumBranches()+1))), parent.getData().getY() + 36);
		}
		if (!next.isEmpty())
			updateNodes(next);
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
