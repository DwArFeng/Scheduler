package com.dwarfeng.scheduler.typedef.desint;

import javax.swing.JDesktopPane;

import com.dwarfeng.scheduler.module.project.abstruct.ProjectTreeNode;

/**
 * �ɱ༭�ӿڡ�
 * <p>ֻҪ���ܱ༭�Ķ��󣬶�Ӧ��ʵ������ӿڡ�
 * <br>��ν�ı༭����ָ�ڳ���������({@linkplain JDesktopPane})������һ����Ӧ�ı༭���ڣ�
 * �������������������ݣ��������ԣ��ĸ��ģ���Ϊ�������總���ı������ڿɱ༭�Ķ���
 * 
 * @author DwArFeng
 * @since 1.8
 */
public interface Editable extends ProjectTreeNode{
	
	/**
	 * ���ر༭�����������Ĵ��ڵı��⡣
	 * @return �����ı���
	 */
	public String getEditorTitle();
	
	/**
	 * ���ɱ༭���������ʵ����
	 * @return ���ɵı༭����ʵ����
	 */
	public Editor<?> newEditor();
	
}
