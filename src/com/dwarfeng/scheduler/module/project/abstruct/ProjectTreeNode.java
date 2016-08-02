package com.dwarfeng.scheduler.module.project.abstruct;

import java.util.Enumeration;
import java.util.Set;

import javax.swing.tree.TreeNode;

import com.dwarfeng.scheduler.module.VShowableTreeNode;

/**
 * 工程树节点接口。
 * <p> 实现该接口意味着该对象是整个工程的树状结构的一员。
 * @author DwArFeng
 * @since 1.8
 */
public interface ProjectTreeNode extends ObjectInProject,TreeNode{
	
	/**
	 * 将指定的工程树节点插入在指定的位置。
	 * @param newChild 指定的工程树节点。
	 * @param childIndex 指定的位置。
	 */
	public void insert(ProjectTreeNode newChild, int childIndex);
	
	/**
	 * 将指定的工程树节点插入在工程树的最后位置。
	 * @param newChild 指定的工程树节点。
	 */
	public default void add(ProjectTreeNode newChild){
		insert(newChild,getChildCount());
	}
	
	/**
	 * 移除指定序列中的子对象。
	 * @param index 指定的序列。
	 */
	public void remove(int index);
	/**
	 * 从工程树节点中移除指定的子对象。
	 * @param child 指定的子对象。
	 */
	public void remove(ProjectTreeNode child);
	
	/**
	 * 从父对象中移除自身。
	 */
	public default void removeFromeParent(){
		ProjectTreeNode obj = getParent();
		if(obj != null){
			obj.remove(this);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see javax.swing.tree.TreeNode#getChildAt(int)
	 */
	@Override
	public ProjectTreeNode getChildAt(int childIndex);
	
	/*
	 * (non-Javadoc)
	 * @see javax.swing.tree.TreeNode#getParent()
	 */
	@Override
	public ProjectTreeNode getParent();
	
	/**
	 * 设定自身的父对象为指定的父对象。
	 * @param newParent 指定的父对象。
	 */
	public void setParent(ProjectTreeNode newParent);
	
	/*
	 * (non-Javadoc)
	 * @see javax.swing.tree.TreeNode#children()
	 */
	@Override
	public Enumeration<ProjectTreeNode> children();
	
	/**
	 * 返回以此节点除自身以外，还额外包含的所有<code>ObjectInProject</code>。
	 * @return 此节点除自身以外，还额外包含的所有<code>ObjectInProject</code>。
	 */
	public Set<ObjectOutProjectTree> getObjectOutProjectTrees();
	
//	/**
//	 * 根据原先的渲染的标签样式来补充渲染自己的独特样式的标签。
//	 * @param label 原样式的标签。
//	 */
//	public void renderLabel(JLabel label);
	
	/**
	 * 返回该工程树节点生成的可视树节点。
	 * @return 生成的可视树节点。
	 */
	public VShowableTreeNode getShowableTreeNode(SProjectToolKit kit);
}
