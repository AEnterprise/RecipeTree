package info.aenterprise.recipeTree.tree;

import info.aenterprise.recipeTree.tree.generic.NodeData;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.LinkedList;
import java.util.List;

/**
 * Copyright (c) 2016, AEnterprise
 * http://www.aenterprise.info/
 */
public class ItemStackNodeData extends NodeData<ItemStack>
{
	private List<ItemStack> permutations;

	public ItemStackNodeData(ItemStack stack)
	{
		super(stack);
		if (stack.getMetadata() == OreDictionary.WILDCARD_VALUE && stack.getItem().getHasSubtypes())
		{
			permutations = new LinkedList<>();
			stack.getItem().getSubItems(stack.getItem(), null, permutations);
		}
	}

	@Override
	public String toString() {
		return "Stack: " + data.toString() + ", X: " + getX() + ", Y: " + getY();
	}

	@Override
	public ItemStack getData()
	{
		if (permutations == null || permutations.isEmpty())
			return super.getData();

		int s = (int)(System.currentTimeMillis() / 1000);
		return permutations.get(s / 2 % permutations.size());
	}

	@Override
	public boolean isMatch(ItemStack other)
	{
		return OreDictionary.itemMatches(super.getData(), other, false);
	}
}
