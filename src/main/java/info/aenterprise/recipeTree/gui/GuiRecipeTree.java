package info.aenterprise.recipeTree.gui;

import info.aenterprise.recipeTree.tree.ItemStackTreeNode;
import info.aenterprise.recipeTree.tree.generic.NodeData;
import info.aenterprise.recipeTree.tree.generic.TreeNode;
import info.aenterprise.recipeTree.tree.visit.WidthVisitor;
import info.aenterprise.recipeTree.util.Log;
import mezz.jei.api.recipe.IRecipeWrapper;

import java.util.List;
import javax.annotation.Nullable;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
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

	@Nullable
	private TreeNode<ItemStack> root;
	@Nullable
	private TreeNode<ItemStack> selected;
	private int prevMouseX, prevMouseY, xOffset, yOffset;

	private Zoom zoom = Zoom.NORMAL;

	public GuiRecipeTree() {
		super(new DummyContainer());
		this.xSize = 256;
		this.ySize = 222;
		xOffset = 0;
		yOffset = 0;
	}

	public void open() {
		mc = Minecraft.getMinecraft();
		mc.displayGuiScreen(this);
		Log.info("opening");
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		int left = (this.width - this.xSize) / 2;
		int top = (this.height - this.ySize) / 2;
		if (Mouse.isButtonDown(0) && root != null && mouseX > left && mouseX < left + xSize && mouseY > top && mouseY < top + ySize) {
			xOffset += mouseX - prevMouseX;
			yOffset += mouseY - prevMouseY;
			updateTree();
		}
		prevMouseX = mouseX;
		prevMouseY = mouseY;

		int wheel = Mouse.getDWheel();
		if (wheel < 0)
			zoom = zoom.zoomIn();
		if (wheel > 0)
			zoom = zoom.zoomOut();
		Minecraft.getMinecraft().getTextureManager().bindTexture(OVERLAY);
		drawTexturedModalRect(left, top, 0, 0, xSize, ySize);
		startScissor();
		GL11.glPushMatrix();
		GL11.glScaled(zoom.factor, zoom.factor, 1);
		if (root != null) {
			root.drawBackGround(this, left, top, root == selected);
			root.forEach(node -> node.drawBackGround(this, left, top, node == selected));
			root.drawData(this, left, top);
			root.forEach(node -> node.drawData(this, left, top));
		}
		GL11.glPopMatrix();
		cut();
	}

	private void startScissor() {
		int scale = new ScaledResolution(mc).getScaleFactor();
		long guiLeft = Math.round((mc.displayWidth - (xSize * scale)) / 2.0F);
		long guiTop = Math.round((mc.displayHeight + (ySize * scale)) / 2.0F);
		int scissorX = Math.round(guiLeft + 9 * scale - scale);
		int scissorY = Math.round(guiTop - 182 * scale - 9 * scale);
		GL11.glScissor(scissorX, scissorY, 237 * scale, 182 * scale);
		GL11.glEnable(GL11.GL_SCISSOR_TEST);
	}

	private void cut() {
		GL11.glDisable(GL11.GL_SCISSOR_TEST);
	}

	private boolean isClickOnNode(TreeNode<ItemStack> node, int x, int y) {
		int left = (this.width - this.xSize) / 2;
		int top = (this.height - this.ySize) / 2;

		x *= 1 / zoom.factor;
		x -= left;
		y *= 1 / zoom.factor;
		y -= top;
		int nodeX = node.getData().getX();
		int nodeY = node.getData().getY();
		if (x > nodeX && x < nodeX + 20 * (1 / zoom.factor) && y > nodeY && y < nodeY + 20 * (1 / zoom.factor)) {
			selected = node;
			return true;
		}
		return false;
	}

	@Override
	protected void mouseReleased(int mouseX, int mouseY, int state) {
		if (root != null && !isClickOnNode(root, mouseX, mouseY)) {
			for (TreeNode<ItemStack> node : root) {
				if (isClickOnNode(node, mouseX, mouseY))
					break;
			}
		}
	}


	public void recieveRecipe(IRecipeWrapper recipe) {
		if (root == null) {
			root = new ItemStackTreeNode((ItemStack) recipe.getOutputs().get(0));
			for (Object o : recipe.getInputs()) {
				ItemStack stack = null;
				if (o instanceof ItemStack) {
					stack = (ItemStack) o;
				} else if (o instanceof List) {
					stack = (ItemStack) ((List) o).get(0);
				}
				if (stack != null) {

					ItemStackTreeNode treeNode = new ItemStackTreeNode(stack);
					root.addNode(treeNode);
				}
			}
		} else if (selected != null) {
			boolean found = false;
			for (Object o : recipe.getOutputs()) {
				if (o instanceof ItemStack && selected.getData().isMatch((ItemStack)o)) {
					branchOut(recipe.getInputs());
					found = true;
					break;
				}
			}
			if (!found) {
				Log.info("Got a recipe for " + recipe.getOutputs().get(0) + "but expected a recipe for " + selected.getData().getData());
			}
		}
		updateTree();
	}

	private void branchOut(List inputs) {
		TreeNode selection = selected;
		for (Object o : inputs) {
			ItemStack stack = null;
			if (o instanceof ItemStack)
				stack = (ItemStack) o;
			if (o instanceof List)
				stack = (ItemStack) ((List) o).get(0);
			if (stack != null) {
				TreeNode treeNode = new ItemStackTreeNode(stack);
				selection.addNode(treeNode);
			}
		}
	}

	private void updateTree() {
		NodeData data = root.getData();
		data.setPos((int) ((this.width / (2 * zoom.factor) + xOffset)), (int) (30 + yOffset / zoom.factor));
		updateNodes(root);
		//root.printStructure();
	}

	private void updateNodes(TreeNode<?> root) {
		root.postOrder(new WidthVisitor());
		root.updatePositions();
	}

	private enum Zoom {
		MINI(0.5),
		SMALL(0.75),
		NORMAL(1),
		LARGE(2);

		private double factor;

		Zoom(double factor) {
			this.factor = factor;
		}

		public Zoom zoomIn() {
			int lvl = ordinal() - 1;
			if (lvl < 0) {
				return this;
			}
			return values()[lvl];
		}

		public Zoom zoomOut() {
			int lvl = ordinal() + 1;
			if (lvl >= values().length) {
				return this;
			}
			return values()[lvl];
		}
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
