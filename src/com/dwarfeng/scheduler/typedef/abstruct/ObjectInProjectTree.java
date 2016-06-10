package com.dwarfeng.scheduler.typedef.abstruct;

import java.util.Enumeration;
import java.util.Set;

import javax.swing.tree.TreeNode;

/**
 * 工程树对象接口。
 * <p> 实现该接口意味着该对象是整个工程的树状结构的一员。
 * @author DwArFeng
 * @since 1.8
 */
public interface ObjectInProjectTree extends ObjectInProject,TreeNode{
	
	/**
	 * 将指定的工程树对象插入在指定的位置。
	 * @param newChild 指定的工程树对象。
	 * @param childIndex 指定的位置。
	 */
	public void insert(ObjectInProjectTree newChild, int childIndex);
	
	/**
	 * 从工程树对象中移除指定的子对象。
	 * @param child 指定的子对象。
	 */
	public void remove(ObjectInProjectTree child);
	
	/*
	 * (non-Javadoc)
	 * @see javax.swing.tree.TreeNode#getChildAt(int)
	 */
	@Override
	public ObjectInProjectTree getChildAt(int childIndex);
	
	/*
	 * (non-Javadoc)
	 * @see javax.swing.tree.TreeNode#getParent()
	 */
	@Override
	public ObjectInProjectTree getParent();
	
	/*
	 * (non-Javadoc)
	 * @see javax.swing.tree.TreeNode#children()
	 */
	@Override
	public Enumeration<ObjectInProjectTree> children();
	
	/**
	 * 返回以此节点除自身以外，还额外包含的所有<code>ObjectInProject</code>。
	 * @return 此节点除自身以外，还额外包含的所有<code>ObjectInProject</code>。
	 */
	public Set<ObjectInProject> getOtherObjectInProjects();
}
