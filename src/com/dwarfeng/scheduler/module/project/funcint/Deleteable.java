package com.dwarfeng.scheduler.module.project.funcint;

import javax.swing.tree.TreeNode;

import com.dwarfeng.scheduler.module.project.abstruct.ProjectTreeNode;

/**
 * ��ɾ���ӿڡ�
 * <p> �ýӿ�ָʾ���и��ڹ������е��ļ�����ɾ����
 * <br> ������Ĺ��������Ϊ��������ṩ��<code>delete</code>��ݼ��Ķ��塣
 * @author DwArFeng
 * @since 1.8
 */
public interface Deleteable extends ProjectTreeNode,TreeNode{
	
//	/**
//	 * �����������ɾ���ķ�����
//	 */
//	public void delete();
	/**
	 * ����ɾ��֮ǰ����ȷ���Ƿ�ָ�����䡣
	 * <p> ������ֵΪnullʱ��ʹ�ó������Դ���ȷ����䡣
	 * @return ȷ����䡣
	 */
	public String getConfirmWord();
}