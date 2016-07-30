package info.aenterprise.recipeTree.tree;

import net.minecraft.item.ItemStack;

/**
 * Copyright (c) 2016, AEnterprise
 * http://www.aenterprise.info/
 */
public class NodeData {
	private ItemStack stack;
	private int x, y, width;

	public NodeData(ItemStack stack) {
		this.stack = stack;
		width = 40;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setPos(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	@Override
	public String toString() {
		return "Stack: " + stack.toString() + ", X: " + x + ", Y: " + y;
	}

}
