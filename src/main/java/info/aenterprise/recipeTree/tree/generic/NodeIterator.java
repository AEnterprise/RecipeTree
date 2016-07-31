package info.aenterprise.recipeTree.tree.generic;

import java.util.Iterator;
import java.util.List;

/**
 * Copyright (c) 2016, AEnterprise
 * http://www.aenterprise.info/
 */
public class NodeIterator implements Iterator<TreeNode> {
	private final TreeNode topNode;
	private final List<TreeNode> nodes;
	private int count = 0;

	public NodeIterator(TreeNode<?> topNode) {
		this.topNode = topNode;
		nodes = topNode.getAllNodes();
	}

	@Override
	public boolean hasNext() {
		return count < nodes.size();
	}

	@Override
	public TreeNode next() {
		TreeNode treeNode = nodes.get(count);
		count++;
		return treeNode;
	}

	@Override
	public void remove() {
		topNode.removeNode(nodes.get(count - 1));
	}
}
