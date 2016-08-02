package com.dwarfeng.scheduler.module.project.abstruct;

import java.util.Enumeration;
import java.util.Set;

import javax.swing.tree.TreeNode;

import com.dwarfeng.scheduler.module.VShowableTreeNode;

/**
 * �������ڵ�ӿڡ�
 * <p> ʵ�ָýӿ���ζ�Ÿö������������̵���״�ṹ��һԱ��
 * @author DwArFeng
 * @since 1.8
 */
public interface ProjectTreeNode extends ObjectInProject,TreeNode{
	
	/**
	 * ��ָ���Ĺ������ڵ������ָ����λ�á�
	 * @param newChild ָ���Ĺ������ڵ㡣
	 * @param childIndex ָ����λ�á�
	 */
	public void insert(ProjectTreeNode newChild, int childIndex);
	
	/**
	 * ��ָ���Ĺ������ڵ�����ڹ����������λ�á�
	 * @param newChild ָ���Ĺ������ڵ㡣
	 */
	public default void add(ProjectTreeNode newChild){
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
	public void remove(ProjectTreeNode child);
	
	/**
	 * �Ӹ��������Ƴ�����
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
	 * �趨����ĸ�����Ϊָ���ĸ�����
	 * @param newParent ָ���ĸ�����
	 */
	public void setParent(ProjectTreeNode newParent);
	
	/*
	 * (non-Javadoc)
	 * @see javax.swing.tree.TreeNode#children()
	 */
	@Override
	public Enumeration<ProjectTreeNode> children();
	
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
