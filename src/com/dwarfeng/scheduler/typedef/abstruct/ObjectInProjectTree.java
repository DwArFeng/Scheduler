package com.dwarfeng.scheduler.typedef.abstruct;

import java.util.Enumeration;
import java.util.Set;

import javax.swing.tree.TreeNode;

/**
 * ����������ӿڡ�
 * <p> ʵ�ָýӿ���ζ�Ÿö������������̵���״�ṹ��һԱ��
 * @author DwArFeng
 * @since 1.8
 */
public interface ObjectInProjectTree extends ObjectInProject,TreeNode{
	
	/**
	 * ��ָ���Ĺ��������������ָ����λ�á�
	 * @param newChild ָ���Ĺ���������
	 * @param childIndex ָ����λ�á�
	 */
	public void insert(ObjectInProjectTree newChild, int childIndex);
	
	/**
	 * �ӹ������������Ƴ�ָ�����Ӷ���
	 * @param child ָ�����Ӷ���
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
	 * �����Դ˽ڵ���������⣬���������������<code>ObjectInProject</code>��
	 * @return �˽ڵ���������⣬���������������<code>ObjectInProject</code>��
	 */
	public Set<ObjectInProject> getOtherObjectInProjects();
}
