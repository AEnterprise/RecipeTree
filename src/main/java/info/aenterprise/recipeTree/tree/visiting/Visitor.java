package info.aenterprise.recipeTree.tree.visiting;

import info.aenterprise.recipeTree.tree.TreeNode;

/**
 * Copyright (c) 2016, AEnterprise
 * http://www.aenterprise.info/
 */
public interface Visitor<T> {

	void visit(TreeNode<T> host);
}
