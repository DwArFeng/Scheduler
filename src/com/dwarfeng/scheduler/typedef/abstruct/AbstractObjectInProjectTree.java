package com.dwarfeng.scheduler.typedef.abstruct;

import java.util.Enumeration;
import java.util.Vector;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

import com.dwarfeng.scheduler.project.Project;

public abstract class AbstractObjectInProjectTree extends DefaultMutableTreeNode implements ObjectInProjectTree{

	private static final long serialVersionUID = -5132830759460781354L;

	/**
	 * 
	 * @param allowsChildren
	 */
	public AbstractObjectInProjectTree(boolean allowsChildren){
		super(null,allowsChildren);
	}
	
	@Override
	public Project getRootProject(){
		//如果自己就是工程文件，则返回自己
		if(this instanceof Project) return (Project) this;
		//如果自己不是，返回自己的父亲
		TreeNode tn = getParent();
		if(tn instanceof Project) return (Project) tn;
		//如果自己不是Project，而且父亲是null，则没有所属的工程文件。
		if(tn == null) return null;
		//在自己不是工程文件的前提下父亲不属于该类，则没有所属的工程文件。
		if(!(tn instanceof AbstractObjectInProjectTree)) return null;
		//如果自己不是工程文件，而且父亲是该类，则返回父亲的工程根，递归。
		return ((AbstractObjectInProjectTree) tn).getRootProject();
	}
	
	/*
	 * (non-Javadoc)
	 * @see javax.swing.tree.DefaultMutableTreeNode#setParent(javax.swing.tree.MutableTreeNode)
	 */
	@Override
	public void setParent(MutableTreeNode newParent){
		super.setParent(newParent);
	}
	
	/*
	 * (non-Javadoc)
	 * @see javax.swing.tree.DefaultMutableTreeNode#getParent()
	 */
	@Override
	public ObjectInProjectTree getParent(){
		return (ObjectInProjectTree) super.getParent();
	}
	
	/*
	 * (non-Javadoc)
	 * @see javax.swing.tree.DefaultMutableTreeNode#children()
	 */
	@Override
	public Enumeration<ObjectInProjectTree> children(){
		Vector<ObjectInProjectTree> vector = new Vector<ObjectInProjectTree>();
		for(
				Enumeration<?> enu = super.children();
				enu.hasMoreElements();
				//no expression
		){
			vector.add((ObjectInProjectTree) enu.nextElement());
		}
		return vector.elements();
	}
	
	/*
	 * (non-Javadoc)
	 * @see javax.swing.tree.DefaultMutableTreeNode#insert(javax.swing.tree.MutableTreeNode, int)
	 */
	@Override
	public void insert(MutableTreeNode newChild, int childIndex){
		if(!(newChild instanceof ObjectInProjectTree))
			throw new ClassCastException("Can't cast newChild to ObjectInProject");
		super.insert(newChild, childIndex);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.ObjectInProjectTree#insert(com.dwarfeng.scheduler.typedef.abstruct.ObjectInProjectTree, int)
	 */
	@Override
	public void insert(ObjectInProjectTree newChild, int childIndex){
		insert((MutableTreeNode)newChild, childIndex);
	}
	
	/*
	 * (non-Javadoc)
	 * @see javax.swing.tree.DefaultMutableTreeNode#getChildAt(int)
	 */
	@Override
	public ObjectInProjectTree getChildAt(int childIndex){
		return (ObjectInProjectTree) super.getChildAt(childIndex);
	}

	@Override
	public void remove(ObjectInProjectTree child) {
		remove((MutableTreeNode)child);
	}
}
