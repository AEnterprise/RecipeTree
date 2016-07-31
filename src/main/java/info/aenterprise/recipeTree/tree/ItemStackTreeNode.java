package info.aenterprise.recipeTree.tree;

import info.aenterprise.recipeTree.tree.generic.TreeNode;
import info.aenterprise.recipeTree.tree.visit.IVisitor;
import net.minecraft.client.gui.Gui;
import net.minecraft.item.ItemStack;

public class ItemStackTreeNode extends TreeNode<ItemStack>
{
    public ItemStackTreeNode(ItemStack data)
    {
        super(new ItemStackNodeData(data));
    }


    public ItemStackTreeNode(ItemStackNodeData data)
    {
        super(data);
    }

    public ItemStackTreeNode(ItemStackNodeData data, TreeNode parent)
    {
        super(data, parent);
    }

    @Override
    public void addLeaf(ItemStack data)
    {
        addNode(new ItemStackTreeNode(data));
    }

    @Override
    public void drawBackGround(Gui gui, int left, int top)
    {
        gui.drawTexturedModalRect(left + data.getX(), top + data.getY(), 74, 230, 20, 20);
    }

    @Override
    public void drawData(Gui gui, int left, int top)
    {
        // TODO: item rendering
    }
}
