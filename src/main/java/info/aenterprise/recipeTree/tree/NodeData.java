package info.aenterprise.recipeTree.tree;

import net.minecraft.item.ItemStack;

/**
 * Copyright (c) 2016, AEnterprise
 * http://www.aenterprise.info/
 */
public class NodeData {
	private ItemStack stack;
	private int x, y;

	public NodeData(ItemStack stack) {
		this.stack = stack;
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

	@Override
	public String toString() {
		return "Stack: " + stack.toString() + ", X: " + x + ", Y: " + y;
	}

}
