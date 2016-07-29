package info.aenterprise.recipeTree.tree;

import info.aenterprise.recipeTree.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (c) 2016, AEnterprise
 * http://www.aenterprise.info/
 */
public class Branch<T> {
	public T leaf;
	public List<Branch<T>> subBranches;
	public Branch<T> parent;

	public Branch(T leaf) {
		this(leaf, null);
	}

	public Branch(T leaf, Branch<T> parent) {
		this.leaf = leaf;
		subBranches = new ArrayList<>();
		this.parent = parent;
	}

	public void addBranch(Branch<T> branch) {
		subBranches.add(branch);
	}

	public List<Branch<T>> getAllBranches() {
		ArrayList<Branch<T>> list = new ArrayList<>();
		for (Branch<T> branch : subBranches) {
			list.add(branch);
			list.addAll(branch.getAllBranches());
		}
		return list;
	}

	public void removeBranch(Branch<T> branch) {
		if (subBranches.contains(branch)) {
			subBranches.remove(branch);
		} else {
			for (Branch<T> subBranch : subBranches) {
				subBranch.removeBranch(branch);
			}
		}
	}

	public void printStructure(boolean isRoot) {
		printStructure("", isRoot, true);
	}

	private void printStructure(String prefix, boolean isRoot, boolean isTail) {
		Log.info(prefix + (isRoot ? "" : isTail ? "└── " : "├── ") + leaf.toString());
		for (int i = 0; i < subBranches.size() - 1; i++) {
			subBranches.get(i).printStructure(prefix + (isTail ? "    " : "│   "), false, false);
		}
		if (subBranches.size() > 0) {
			subBranches.get(subBranches.size() - 1).printStructure(prefix + (isTail ? "    " : "│   "), false, true);
		}
	}

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Branch{");
        sb.append("leaf=").append(leaf);
        sb.append('}');
        return sb.toString();
    }
}
