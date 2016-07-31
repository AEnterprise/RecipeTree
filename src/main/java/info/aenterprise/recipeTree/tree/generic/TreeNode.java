package info.aenterprise.recipeTree.tree.generic;

import info.aenterprise.recipeTree.tree.visit.IHost;
import info.aenterprise.recipeTree.tree.visit.IVisitor;
import info.aenterprise.recipeTree.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.gui.Gui;

/**
 * Copyright (c) 2016, AEnterprise
 * http://www.aenterprise.info/
 */
public abstract class TreeNode<T> implements Iterable<TreeNode>, IHost
{
	protected NodeData<T> data;
	private List<TreeNode> subNodes;
	private TreeNode parent;


	public TreeNode(NodeData<T> data) {
		this(data, null);
	}

	public TreeNode(NodeData<T> data, TreeNode parent) {
		this.data = data;
		subNodes = new ArrayList<>();
		this.parent = parent;
	}

	public NodeData<T> getData() {
		return data;
	}

	public abstract void addLeaf(T data);

	public abstract void drawBackGround(Gui gui, int left, int top);

	public abstract void drawData(Gui gui, int left, int top);

	public void addNode(TreeNode treeNode) {
		treeNode.parent = this;
		subNodes.add(treeNode);
	}

	public List<TreeNode> getSubNodes() {
		return ImmutableList.copyOf(subNodes);
	}

	public List<TreeNode> getAllNodes() {
		List<TreeNode> list = new ArrayList<>();
		for (TreeNode<?> treeNode : subNodes) {
			list.add(treeNode);
			list.addAll(treeNode.getAllNodes());
		}
		return list;
	}

	public void removeNode(TreeNode treeNode) {
		if (subNodes.contains(treeNode)) {
			subNodes.remove(treeNode);
		} else {
			for (TreeNode subTreeNode : subNodes) {
				subTreeNode.removeNode(treeNode);
			}
		}
	}

	public void printStructure() {
		printStructure("", true);
	}

	private void printStructure(String prefix, boolean isTail) {
		Log.info(prefix + (parent == null ? "" : isTail ? "└── " : "├── ") + "Leaf: " + data.toString() + ", Sub-branches: " + getNumNodes() + ", layer: " + getLayer());
		for (int i = 0; i < subNodes.size() - 1; i++) {
			subNodes.get(i).printStructure(prefix + (isTail ? "    " : "│   "), false);
		}
		if (subNodes.size() > 0) {
			subNodes.get(subNodes.size() - 1).printStructure(prefix + (isTail ? "    " : "│   "), true);
		}
	}

	public int getNumNodes() {
		int branches = subNodes.size();
		for (TreeNode treeNode : subNodes) {
			branches += treeNode.getNumNodes();
		}
		return branches;
	}

	public boolean isLeaf()
	{
		return subNodes.isEmpty();
	}

	@Override
	public void postOrder(IVisitor visitor)
	{
		getSubNodes().forEach(subNode -> subNode.postOrder(visitor));
		visitor.visit(this);
	}

	public void updatePositions()
	{
		for (int i = 0; i < subNodes.size(); i++)
		{
			TreeNode<?> subNode = subNodes.get(i);
			subNode.getData().setPos(getData().getX() + subNode.getData().getWidth() / 2 - getData().getWidth() / 2 + (40 * i), getData().getY() + 36);
			subNode.updatePositions();
		}
	}

	@Override
	public String toString() {
		return "Branch{data=" + data + "}";
	}

	@Override
	public Iterator<TreeNode> iterator() {
		return new NodeIterator(this);
	}

	public int getLayer() {
		if (parent == null)
			return 0;
		return parent.getLayer() + 1;
	}

	public TreeNode getParent() {
		return parent;
	}
}