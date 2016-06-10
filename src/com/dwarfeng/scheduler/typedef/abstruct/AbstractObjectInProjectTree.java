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
