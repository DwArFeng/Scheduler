package com.dwarfeng.scheduler.module;

import javax.swing.JLabel;
import javax.swing.tree.TreeNode;

import com.dwarfeng.scheduler.module.project.abstruct.ProjectTreeNode;

/**
 * 可显示树节点。
 * <p> 用于显示在视图上的树节点。
 * <br> 该树节点由{@linkplain ProjectTreeNode}节点生成，他们不想{@linkplain ProjectTreeNode}那样可以被编辑，但是
 * 它们可以返回相应的显示模型以供显示。这种中间节点保证了视图的正常显示，又阻止了显示层直接对数据层修改的可能。
 * @author DwArFeng
 * @since 1.8
 */
public interface VShowableTreeNode extends TreeNode {

	/*
	 * (non-Javadoc)
	 * @see javax.swing.tree.TreeNode#getChildAt(int)
	 */
	@Override
	public VShowableTreeNode getChildAt(int index);
	
	/*
	 * (non-Javadoc)
	 * @see javax.swing.tree.TreeNode#getParent()
	 */
	@Override
	public VShowableTreeNode getParent();
	
	/**
	 * 返回节点对应的标签。
	 * <p> 该标签被直接显示在程序界面的工程树之上。
	 * @return 节点对应的标签。
	 */
	public JLabel getLabel();
	
}
