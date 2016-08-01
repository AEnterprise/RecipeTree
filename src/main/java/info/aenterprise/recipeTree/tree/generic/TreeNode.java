package info.aenterprise.recipeTree.tree.generic;

import com.google.common.collect.ImmutableList;
import info.aenterprise.recipeTree.tree.visit.IHost;
import info.aenterprise.recipeTree.tree.visit.IVisitor;
import info.aenterprise.recipeTree.util.Log;
import net.minecraft.client.gui.GuiScreen;

import java.util.*;

/**
 * Copyright (c) 2016, AEnterprise
 * http://www.aenterprise.info/
 */
public abstract class TreeNode<T> implements Iterable<TreeNode>, IHost
{
	protected NodeData<T> data;
	private Map<TreeNode,Integer> subNodes;
	private TreeNode parent;


	public TreeNode(NodeData<T> data) {
		this(data, null);
	}

	public TreeNode(NodeData<T> data, TreeNode parent) {
		this.data = data;
		subNodes = new HashMap<>();
		this.parent = parent;
	}

	public NodeData<T> getData() {
		return data;
	}

	public abstract void addLeaf(T data);

	public abstract void drawBackGround(GuiScreen gui, int left, int top, boolean selected);

	public abstract void drawData(GuiScreen gui);

	public void addNode(TreeNode treeNode) {
		treeNode.parent = this;
		if (subNodes.containsKey(treeNode)){
			int amount = subNodes.get(treeNode) + 1;
			subNodes.put(treeNode, amount);
		} else {
			subNodes.put(treeNode, 1);
		}
	}

	public List<TreeNode> getSubNodes() {
		return ImmutableList.copyOf(subNodes.keySet());
	}

	public List<TreeNode> getAllNodes() {
		List<TreeNode> list = new ArrayList<>();
		for (TreeNode<?> treeNode : subNodes.keySet()) {
			list.add(treeNode);
			list.addAll(treeNode.getAllNodes());
		}
		return list;
	}

	public void removeNode(TreeNode treeNode) {
		if (subNodes.keySet().contains(treeNode)) {
			subNodes.remove(treeNode);
		} else {
			for (TreeNode subTreeNode : subNodes.keySet()) {
				subTreeNode.removeNode(treeNode);
			}
		}
	}

	public void printStructure() {
		printStructure("", true);
	}

	private void printStructure(String prefix, boolean isTail) {
		Log.info(prefix + (parent == null ? "" : isTail ? "└── " : "├── ") + "Leaf: " + data.toString() + ", Sub-branches: " + getNumNodes() + ", layer: " + getLayer() + ", width: " + data.getWidth());
		List<TreeNode> nodes = new ArrayList<>(subNodes.keySet());
		for (int i = 0; i < subNodes.size() - 1; i++) {
			nodes.get(i).printStructure(prefix + (isTail ? "    " : "│   "), false);
		}
		if (subNodes.size() > 0) {
			nodes.get(subNodes.size() - 1).printStructure(prefix + (isTail ? "    " : "│   "), true);
		}
	}

	public int getNumNodes() {
		int branches = subNodes.size();
		for (TreeNode treeNode : subNodes.keySet()) {
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
		int x = getData().getX() - getData().getWidth() / 2;
		List<TreeNode> nodes = new ArrayList<>(subNodes.keySet());
		for (int i = 0; i < nodes.size(); i++)
		{
			TreeNode<?> subNode = nodes.get(i);
			x += subNode.getData().getWidth() / 2;
			subNode.getData().setPos(x, getData().getY() + 36);
			x += subNode.getData().getWidth() / 2;
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
