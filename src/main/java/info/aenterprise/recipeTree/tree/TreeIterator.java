package info.aenterprise.recipeTree.tree;

import java.util.Iterator;
import java.util.List;

/**
 * Copyright (c) 2016, AEnterprise
 * http://www.aenterprise.info/
 */
public class TreeIterator<T> implements Iterator<Branch<T>> {
	private final Tree<T> tree;
	private final List<Branch<T>> branches;
	private int count = 0;

	public TreeIterator(Tree<T> tree) {
		this.tree = tree;
		branches = tree.getAllBranches();
	}

	@Override
	public boolean hasNext() {
		return count < branches.size();
	}

	@Override
	public Branch<T> next() {
		Branch<T> branch = branches.get(count);
		count++;
		return branch;
	}

	@Override
	public void remove() {
		tree.removeBranch(branches.get(count - 1));
	}
}
