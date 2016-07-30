package info.aenterprise.recipeTree.tree;

import info.aenterprise.recipeTree.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.google.common.collect.ImmutableList;

/**
 * Copyright (c) 2016, AEnterprise
 * http://www.aenterprise.info/
 */
public class TreeNode<T> implements Iterable<TreeNode<T>> {
	private T data;
	private List<TreeNode<T>> subNodes;
	private TreeNode<T> parent;


	public TreeNode(T data) {
		this(data, null);
	}

	public TreeNode(T data, TreeNode<T> parent) {
		this.data = data;
		subNodes = new ArrayList<>();
		this.parent = parent;
	}

	public T getData() {
		return data;
	}

	public void addBranchWithLeaf(T leaf) {
		addBranch(new TreeNode<T>(leaf));
	}

	public void addBranch(TreeNode<T> treeNode) {
		treeNode.parent = this;
		subNodes.add(treeNode);
	}

	public List<TreeNode<T>> getSubNodes() {
		return ImmutableList.copyOf(subNodes);
	}

	public List<TreeNode<T>> getAllBranches() {
		ArrayList<TreeNode<T>> list = new ArrayList<>();
		for (TreeNode<T> treeNode : subNodes) {
			list.add(treeNode);
			list.addAll(treeNode.getAllBranches());
		}
		return list;
	}

	public void removeBranch(TreeNode<T> treeNode) {
		if (subNodes.contains(treeNode)) {
			subNodes.remove(treeNode);
		} else {
			for (TreeNode<T> subTreeNode : subNodes) {
				subTreeNode.removeBranch(treeNode);
			}
		}
	}

	public void printStructure() {
		printStructure("", true);
	}

	private void printStructure(String prefix, boolean isTail) {
		Log.info(prefix + (parent == null ? "" : isTail ? "└── " : "├── ") + "Leaf: " + data.toString() + ", Sub-branches: " + getNumBranches() + ", layer: " + getLayer());
		for (int i = 0; i < subNodes.size() - 1; i++) {
			subNodes.get(i).printStructure(prefix + (isTail ? "    " : "│   "), false);
		}
		if (subNodes.size() > 0) {
			subNodes.get(subNodes.size() - 1).printStructure(prefix + (isTail ? "    " : "│   "), true);
		}
	}

	public int getNumBranches() {
		int branches = subNodes.size();
		for (TreeNode treeNode : subNodes) {
			branches += treeNode.getNumBranches();
		}
		return branches;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("Branch{");
		sb.append("data=").append(data);
		sb.append('}');
		return sb.toString();
	}

	@Override
	public Iterator<TreeNode<T>> iterator() {
		return new NodeIterator<>(this);
	}

	public int getLayer() {
		if (parent == null)
			return 0;
		return parent.getLayer() + 1;
	}

	public TreeNode<T> getParent() {
		return parent;
	}
}
