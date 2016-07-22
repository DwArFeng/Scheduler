package com.dwarfeng.scheduler.typedef.pabstruct;

import java.util.Enumeration;
import java.util.Set;

import javax.swing.JLabel;
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
	 * ��ָ���Ĺ�������������ڹ����������λ�á�
	 * @param newChild ָ���Ĺ���������
	 */
	public default void add(ObjectInProjectTree newChild){
		insert(newChild,getChildCount());
	}
	
	/**
	 * �Ƴ�ָ�������е��Ӷ���
	 * @param index ָ�������С�
	 */
	public void remove(int index);
	/**
	 * �ӹ������������Ƴ�ָ�����Ӷ���
	 * @param child ָ�����Ӷ���
	 */
	public void remove(ObjectInProjectTree child);
	
	/**
	 * �Ӹ��������Ƴ�����
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
	 * �趨����ĸ�����Ϊָ���ĸ�����
	 * @param newParent ָ���ĸ�����
	 */
	public void setParent(ObjectInProjectTree newParent);
	
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
	public Set<ObjectOutProjectTree> getObjectOutProjectTrees();
	
	/**
	 * ����ԭ�ȵ���Ⱦ�ı�ǩ��ʽ��������Ⱦ�Լ��Ķ�����ʽ�ı�ǩ��
	 * @param label ԭ��ʽ�ı�ǩ��
	 */
	public void renderLabel(JLabel label);
}
