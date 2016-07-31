package info.aenterprise.recipeTree.tree.visit;

import info.aenterprise.recipeTree.tree.generic.TreeNode;

public interface IVisitor
{
    void visit(TreeNode<?> node);
}
