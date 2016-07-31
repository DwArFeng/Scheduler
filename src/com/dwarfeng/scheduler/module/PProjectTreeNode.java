package com.dwarfeng.scheduler.module;

import java.util.Enumeration;
import java.util.Set;

import javax.swing.JLabel;
import javax.swing.tree.TreeNode;

import com.dwarfeng.scheduler.module.project.abstruct.ObjectInProject;
import com.dwarfeng.scheduler.module.project.abstruct.ObjectOutProjectTree;

/**
 * �������ڵ�ӿڡ�
 * <p> ʵ�ָýӿ���ζ�Ÿö������������̵���״�ṹ��һԱ��
 * @author DwArFeng
 * @since 1.8
 */
interface PProjectTreeNode extends ObjectInProject,TreeNode{
	
	/**
	 * ��ָ���Ĺ������ڵ������ָ����λ�á�
	 * @param newChild ָ���Ĺ������ڵ㡣
	 * @param childIndex ָ����λ�á�
	 */
	public void insert(PProjectTreeNode newChild, int childIndex);
	
	/**
	 * ��ָ���Ĺ������ڵ�����ڹ����������λ�á�
	 * @param newChild ָ���Ĺ������ڵ㡣
	 */
	public default void add(PProjectTreeNode newChild){
		insert(newChild,getChildCount());
	}
	
	/**
	 * �Ƴ�ָ�������е��Ӷ���
	 * @param index ָ�������С�
	 */
	public void remove(int index);
	/**
	 * �ӹ������ڵ����Ƴ�ָ�����Ӷ���
	 * @param child ָ�����Ӷ���
	 */
	public void remove(PProjectTreeNode child);
	
	/**
	 * �Ӹ��������Ƴ�����
	 */
	public default void removeFromeParent(){
		PProjectTreeNode obj = getParent();
		if(obj != null){
			obj.remove(this);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see javax.swing.tree.TreeNode#getChildAt(int)
	 */
	@Override
	public PProjectTreeNode getChildAt(int childIndex);
	
	/*
	 * (non-Javadoc)
	 * @see javax.swing.tree.TreeNode#getParent()
	 */
	@Override
	public PProjectTreeNode getParent();
	
	/**
	 * �趨����ĸ�����Ϊָ���ĸ�����
	 * @param newParent ָ���ĸ�����
	 */
	public void setParent(PProjectTreeNode newParent);
	
	/*
	 * (non-Javadoc)
	 * @see javax.swing.tree.TreeNode#children()
	 */
	@Override
	public Enumeration<PProjectTreeNode> children();
	
	/**
	 * �����Դ˽ڵ���������⣬���������������<code>ObjectInProject</code>��
	 * @return �˽ڵ���������⣬���������������<code>ObjectInProject</code>��
	 */
	public Set<ObjectOutProjectTree> getObjectOutProjectTrees();
	
//	/**
//	 * ����ԭ�ȵ���Ⱦ�ı�ǩ��ʽ��������Ⱦ�Լ��Ķ�����ʽ�ı�ǩ��
//	 * @param label ԭ��ʽ�ı�ǩ��
//	 */
//	public void renderLabel(JLabel label);
	
	/**
	 * ���ظù������ڵ����ɵĿ������ڵ㡣
	 * @return ���ɵĿ������ڵ㡣
	 */
	public VShowableTreeNode getShowableTreeNode(SProjectToolKit kit);
}
