package info.aenterprise.recipeTree.tree;

import info.aenterprise.recipeTree.tree.generic.NodeData;

import java.util.LinkedList;
import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Copyright (c) 2016, AEnterprise
 * http://www.aenterprise.info/
 */
@SideOnly(Side.CLIENT)
public class ItemStackNodeData extends NodeData<ItemStack>
{
	private List<ItemStack> permutations;

	public ItemStackNodeData(ItemStack stack)
	{
		super(stack);
		if (stack.getMetadata() == OreDictionary.WILDCARD_VALUE) {
			if (stack.getHasSubtypes()) {
				permutations = new LinkedList<>();
				stack.getItem().getSubItems(stack.getItem(), null, permutations);
			} else {
				stack.setItemDamage(0);
			}
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
