package info.aenterprise.recipeTree.tree;

import net.minecraft.item.ItemStack;

/**
 * Copyright (c) 2016, AEnterprise
 * http://www.aenterprise.info/
 */
public class Leaf {
	private ItemStack stack;

	public Leaf(ItemStack stack) {
		this.stack = stack;
	}

	@Override
	public String toString() {
		return stack.toString();
	}
}
