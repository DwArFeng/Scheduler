package com.dwarfeng.scheduler.module;

import javax.swing.JLabel;
import javax.swing.tree.TreeNode;

import com.dwarfeng.scheduler.module.project.abstruct.ProjectTreeNode;

/**
 * ����ʾ���ڵ㡣
 * <p> ������ʾ����ͼ�ϵ����ڵ㡣
 * <br> �����ڵ���{@linkplain ProjectTreeNode}�ڵ����ɣ����ǲ���{@linkplain ProjectTreeNode}�������Ա��༭������
 * ���ǿ��Է�����Ӧ����ʾģ���Թ���ʾ�������м�ڵ㱣֤����ͼ��������ʾ������ֹ����ʾ��ֱ�Ӷ����ݲ��޸ĵĿ��ܡ�
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
	 * ���ؽڵ��Ӧ�ı�ǩ��
	 * <p> �ñ�ǩ��ֱ����ʾ�ڳ������Ĺ�����֮�ϡ�
	 * @return �ڵ��Ӧ�ı�ǩ��
	 */
	public JLabel getLabel();
	
}
