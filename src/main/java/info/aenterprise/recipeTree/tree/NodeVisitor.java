package info.aenterprise.recipeTree.tree;

import info.aenterprise.recipeTree.util.Log;

/**
 * Copyright (c) 2016, AEnterprise
 * http://www.aenterprise.info/
 */
public class NodeVisitor implements Visitor {
	@Override
	public void visit(Host host) {
		Log.info("visiting host in wrong function");
	}


	public void visit(TreeNode<NodeData> node) {
		int width = 40;
		Log.info("visiting host in correct function");
		for (TreeNode<NodeData> subnode : node.getSubNodes()) {
			subnode.invite(this);
			width += subnode.getData().getWidth();
		}
		node.getData().setWidth(width);

	}
}
