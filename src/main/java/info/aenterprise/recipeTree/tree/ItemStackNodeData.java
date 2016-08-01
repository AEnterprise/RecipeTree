package info.aenterprise.recipeTree.tree;

import info.aenterprise.recipeTree.tree.generic.NodeData;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Copyright (c) 2016, AEnterprise
 * http://www.aenterprise.info/
 */
public class ItemStackNodeData extends NodeData<ItemStack>
{
	public ItemStackNodeData(ItemStack stack)
	{
		super(stack);
	}

	@Override
	public String toString() {
		return "Stack: " + data.toString() + ", X: " + getX() + ", Y: " + getY();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ItemStackNodeData){
			ItemStackNodeData other = (ItemStackNodeData) obj;
			Item otherStack = other.getData().getItem();
			Item thisStack = this.getData().getItem();
			return otherStack == thisStack;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return getData().getItem().hashCode();
	}
}
