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

	public void addBranch(T leaf) {
		addBranch(new Branch<T>(leaf));
	}

	public void addBranch(Branch<T> branch) {
		root.addBranch(branch);
	}

	public void printStructure() {
		root.printStructure(true);
	}

	public List<T> getAllLeafs() {
		List<T> list = new ArrayList<T>();
		for (Branch<T> branch : this) {
			list.add(branch.leaf);
		}
		return list;
	}
}
