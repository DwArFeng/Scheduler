package com.dwarfeng.scheduler.typedef.abstruct;

import java.util.Enumeration;
import java.util.Vector;

import javax.swing.tree.TreeNode;

import com.dwarfeng.scheduler.project.Project;

/**
 * 抽象工程树对象接口类。
 * <p> 此类最大程度上实现了 {@linkplain ObjectInProjectTree}接口。
 * @author DwArFeng
 * @since 1.8
 */
public abstract class AbstractObjectInProjectTree implements ObjectInProjectTree{
	
    /** true if the node is able to have children */
	protected boolean allowsChildren;
    /** array of children, may be null if this node has no children */
	protected final Vector<ObjectInProjectTree> children;
    /** this node's parent, or null if this node has no parent */
	protected ObjectInProjectTree parent;

	/**
	 * 生成一个新的抽象工程树对象接口。
	 * @param allowsChildren 指示该接口是否允许拥有子节点。
	 */
	public AbstractObjectInProjectTree(boolean allowsChildren){
		this.allowsChildren = allowsChildren;
		children = new Vector<ObjectInProjectTree>();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.ObjectInProject#getRootProject()
	 */
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
	 * @see javax.swing.tree.DefaultMutableTreeNode#getParent()
	 */
	@Override
	public ObjectInProjectTree getParent(){
        return parent;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.ObjectInProjectTree#children()
	 */
	@Override
	public Enumeration<ObjectInProjectTree> children(){
		return children.elements();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.ObjectInProjectTree#insert(com.dwarfeng.scheduler.typedef.abstruct.ObjectInProjectTree, int)
	 */
	@Override
	public void insert(ObjectInProjectTree newChild, int childIndex){
		
        if (!allowsChildren) {
            throw new IllegalStateException("node does not allow children");
        } else if (newChild == null) {
            throw new IllegalArgumentException("new child is null");
        } else if (isNodeAncestor(newChild)) {
            throw new IllegalArgumentException("new child is an ancestor");
        }
        
        if(!canInsert(newChild)) throw new IllegalArgumentException("Bad child : can't allowed to trans in");

            ObjectInProjectTree oldParent = newChild.getParent();

            if (oldParent != null) {
                oldParent.remove(newChild);
            }
            newChild.setParent(this);
            children.insertElementAt(newChild, childIndex);
	}
	
	/**
	 * 测试一个工程树对象是否能够被传进。
	 * @param newChild 指定的工程树对象。
	 * @return 是否能够被传进。
	 */
    protected abstract boolean canInsert(ObjectInProjectTree newChild);

	/**
     * Returns true if <code>anotherNode</code> is an ancestor of this node
     * -- if it is this node, this node's parent, or an ancestor of this
     * node's parent.  (Note that a node is considered an ancestor of itself.)
     * If <code>anotherNode</code> is null, this method returns false.  This
     * operation is at worst O(h) where h is the distance from the root to
     * this node.
     *
     * @param   anotherNode     node to test as an ancestor of this node
     * @return  true if this node is a descendant of <code>anotherNode</code>
     */
    public boolean isNodeAncestor(TreeNode anotherNode) {
        if (anotherNode == null) {
            return false;
        }

        TreeNode ancestor = this;

        do {
            if (ancestor == anotherNode) {
                return true;
            }
        } while((ancestor = ancestor.getParent()) != null);

        return false;
    }
    
	/*
	 * (non-Javadoc)
	 * @see javax.swing.tree.DefaultMutableTreeNode#getChildAt(int)
	 */
	@Override
	public ObjectInProjectTree getChildAt(int childIndex){
        return children.elementAt(childIndex);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.ObjectInProjectTree#remove(com.dwarfeng.scheduler.typedef.abstruct.ObjectInProjectTree)
	 */
	@Override
	public void remove(ObjectInProjectTree child) {
        if (child == null) {
            throw new IllegalArgumentException("argument is null");
        }

        if (!isNodeChild(child)) {
            throw new IllegalArgumentException("argument is not a child");
        }
        remove(getIndex(child));       // linear search
    }
	
    /**
     * Returns true if <code>aNode</code> is a child of this node.  If
     * <code>aNode</code> is null, this method returns false.
     *
     * @return  true if <code>aNode</code> is a child of this node; false if
     *                  <code>aNode</code> is null
     */
    public boolean isNodeChild(ObjectInProjectTree aNode) {
        boolean retval;

        if (aNode == null) {
            retval = false;
        } else {
            if (getChildCount() == 0) {
                retval = false;
            } else {
                retval = (aNode.getParent() == this);
            }
        }

        return retval;
    }
    
    /*
     * (non-Javadoc)
     * @see javax.swing.tree.TreeNode#getChildCount()
     */
	@Override
	public int getChildCount() {
		return children.size();
    }

	/*
	 * (non-Javadoc)
	 * @see javax.swing.tree.TreeNode#getIndex(javax.swing.tree.TreeNode)
	 */
	@Override
	public int getIndex(TreeNode node) {
        if (node == null) {
            throw new IllegalArgumentException("argument is null");
        }
        
        //如果node不属于ObjectInTreeNode 则不可能是工程树中的元素。
        if(!(node instanceof ObjectInProjectTree)){
        	return -1;
        }
        
        ObjectInProjectTree obj = (ObjectInProjectTree) node;

        if (!isNodeChild(obj)) {
            return -1;
        }
        return children.indexOf(obj);        // linear search
    }

	/*
	 * (non-Javadoc)
	 * @see javax.swing.tree.TreeNode#getAllowsChildren()
	 */
	@Override
	public boolean getAllowsChildren() {
		 return allowsChildren;
	}

	/*
	 * (non-Javadoc)
	 * @see javax.swing.tree.TreeNode#isLeaf()
	 */
	@Override
	public boolean isLeaf() {
        return (getChildCount() == 0);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.ObjectInProjectTree#remove(int)
	 */
	@Override
	public void remove(int index) {
        ObjectInProjectTree child = getChildAt(index);
        children.removeElementAt(index);
        child.setParent(null);
    }

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.ObjectInProjectTree#setParent(com.dwarfeng.scheduler.typedef.abstruct.ObjectInProjectTree)
	 */
	@Override
	public void setParent(ObjectInProjectTree newParent) {
		parent = newParent;
	}
	
}
