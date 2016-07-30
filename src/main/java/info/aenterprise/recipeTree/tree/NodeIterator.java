package info.aenterprise.recipeTree.tree;

import java.util.Iterator;
import java.util.List;

/**
 * Copyright (c) 2016, AEnterprise
 * http://www.aenterprise.info/
 */
public class NodeIterator<T> implements Iterator<TreeNode<T>> {
	private final TreeNode<T> topNode;
	private final List<TreeNode<T>> nodes;
	private int count = 0;

	public NodeIterator(TreeNode<T> topNode) {
		this.topNode = topNode;
		nodes = topNode.getAllNodes();
	}

	@Override
	public boolean hasNext() {
		return count < nodes.size();
	}

	@Override
	public TreeNode<T> next() {
		TreeNode<T> treeNode = nodes.get(count);
		count++;
		return treeNode;
	}

	@Override
	public void remove() {
		topNode.removeNode(nodes.get(count - 1));
	}
}
