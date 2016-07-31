package info.aenterprise.recipeTree.tree.visit;

import info.aenterprise.recipeTree.tree.generic.TreeNode;

public class WidthVisitor implements IVisitor
{
    @Override
    public void visit(TreeNode<?> node)
    {
        if (!node.isLeaf())
            node.getData().setWidth(node.getSubNodes().stream()
                    .mapToInt(subNode -> subNode.getData().getWidth()).sum());
    }
}
