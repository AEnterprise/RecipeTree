package info.aenterprise.recipeTree.tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Copyright (c) 2016, AEnterprise
 * http://www.aenterprise.info/
 */
public class Tree<T> implements Iterable<Branch<T>> {
	public Branch<T> root;

	public Tree(T root) {
		this.root = new Branch<>(root);
	}

	@Override
	public Iterator<Branch<T>> iterator() {
		return new TreeIterator<>(this);
	}

	public List<Branch<T>> getAllBranches() {
		ArrayList<Branch<T>> list = new ArrayList<>();
		list.add(root);
		list.addAll(root.getAllBranches());
		return list;
	}

	public void removeBranch(Branch<T> branch) {
		root.removeBranch(branch);
	}
}
