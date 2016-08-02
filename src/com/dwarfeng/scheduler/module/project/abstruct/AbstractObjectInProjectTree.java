package com.dwarfeng.scheduler.module.project.abstruct;

import java.util.Enumeration;
import java.util.Vector;

import javax.swing.tree.TreeNode;

import com.dwarfeng.scheduler.module.project.Project;

/**
 * ���󹤳�������ӿ��ࡣ
 * <p> �������̶���ʵ���� {@linkplain ProjectTreeNode}�ӿڡ�
 * @author DwArFeng
 * @since 1.8
 */
public abstract class AbstractObjectInProjectTree implements ProjectTreeNode{
	
    /** true if the node is able to have children */
	protected boolean allowsChildren;
    /** array of children, may be null if this node has no children */
	protected final Vector<ProjectTreeNode> children;
    /** this node's parent, or null if this node has no parent */
	protected ProjectTreeNode parent;

	/**
	 * ����һ���µĳ��󹤳�������ӿڡ�
	 * @param allowsChildren ָʾ�ýӿ��Ƿ�����ӵ���ӽڵ㡣
	 */
	public AbstractObjectInProjectTree(boolean allowsChildren){
		this.allowsChildren = allowsChildren;
		children = new Vector<ProjectTreeNode>();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.ObjectInProject#getRootProject()
	 */
	@Override
	public Project getRootProject(){
		//����Լ����ǹ����ļ����򷵻��Լ�
		if(this instanceof Project) return (Project) this;
		//����Լ����ǣ������Լ��ĸ���
		TreeNode tn = getParent();
		if(tn instanceof Project) return (Project) tn;
		//����Լ�����Project�����Ҹ�����null����û�������Ĺ����ļ���
		if(tn == null) return null;
		//���Լ����ǹ����ļ���ǰ���¸��ײ����ڸ��࣬��û�������Ĺ����ļ���
		if(!(tn instanceof AbstractObjectInProjectTree)) return null;
		//����Լ����ǹ����ļ������Ҹ����Ǹ��࣬�򷵻ظ��׵Ĺ��̸����ݹ顣
		return ((AbstractObjectInProjectTree) tn).getRootProject();
	}
	
	/*
	 * (non-Javadoc)
	 * @see javax.swing.tree.DefaultMutableTreeNode#getParent()
	 */
	@Override
	public ProjectTreeNode getParent(){
        return parent;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.ObjectInProjectTree#children()
	 */
	@Override
	public Enumeration<ProjectTreeNode> children(){
		return children.elements();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.ObjectInProjectTree#insert(com.dwarfeng.scheduler.typedef.abstruct.ObjectInProjectTree, int)
	 */
	@Override
	public void insert(ProjectTreeNode newChild, int childIndex){
		
        if (!allowsChildren) {
            throw new IllegalStateException("node does not allow children");
        } else if (newChild == null) {
            throw new IllegalArgumentException("new child is null");
        } else if (isNodeAncestor(newChild)) {
            throw new IllegalArgumentException("new child is an ancestor");
        }
        
        if(!canInsert(newChild)) throw new IllegalArgumentException("Bad child : can't allowed to trans in");

            ProjectTreeNode oldParent = newChild.getParent();

            if (oldParent != null) {
                oldParent.remove(newChild);
            }
            newChild.setParent(this);
            children.insertElementAt(newChild, childIndex);
	}
	
	/**
	 * ����һ�������������Ƿ��ܹ���������
	 * @param newChild ָ���Ĺ���������
	 * @return �Ƿ��ܹ���������
	 */
    protected abstract boolean canInsert(ProjectTreeNode newChild);

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
	public ProjectTreeNode getChildAt(int childIndex){
        return children.elementAt(childIndex);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.ObjectInProjectTree#remove(com.dwarfeng.scheduler.typedef.abstruct.ObjectInProjectTree)
	 */
	@Override
	public void remove(ProjectTreeNode child) {
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
    public boolean isNodeChild(ProjectTreeNode aNode) {
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
        
        //���node������ObjectInTreeNode �򲻿����ǹ������е�Ԫ�ء�
        if(!(node instanceof ProjectTreeNode)){
        	return -1;
        }
        
        ProjectTreeNode obj = (ProjectTreeNode) node;

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
        ProjectTreeNode child = getChildAt(index);
        children.removeElementAt(index);
        child.setParent(null);
    }

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.ObjectInProjectTree#setParent(com.dwarfeng.scheduler.typedef.abstruct.ObjectInProjectTree)
	 */
	@Override
	public void setParent(ProjectTreeNode newParent) {
		parent = newParent;
	}
	
}