package com.dwarfeng.scheduler.typedef.pabstruct;

import java.util.Enumeration;
import java.util.Set;

import javax.swing.JLabel;
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
	 * 将指定的工程树对象插入在工程树的最后位置。
	 * @param newChild 指定的工程树对象。
	 */
	public default void add(ObjectInProjectTree newChild){
		insert(newChild,getChildCount());
	}
	
	/**
	 * 移除指定序列中的子对象。
	 * @param index 指定的序列。
	 */
	public void remove(int index);
	/**
	 * 从工程树对象中移除指定的子对象。
	 * @param child 指定的子对象。
	 */
	public void remove(ObjectInProjectTree child);
	
	/**
	 * 从父对象中移除自身。
	 */
	public default void removeFromeParent(){
		ObjectInProjectTree obj = getParent();
		if(obj != null){
			obj.remove(this);
		}
	}
	
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
	
	/**
	 * 设定自身的父对象为指定的父对象。
	 * @param newParent 指定的父对象。
	 */
	public void setParent(ObjectInProjectTree newParent);
	
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
	public Set<ObjectOutProjectTree> getObjectOutProjectTrees();
	
	/**
	 * 根据原先的渲染的标签样式来补充渲染自己的独特样式的标签。
	 * @param label 原样式的标签。
	 */
	public void renderLabel(JLabel label);
}
