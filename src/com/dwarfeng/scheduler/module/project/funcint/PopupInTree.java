package com.dwarfeng.scheduler.module.project.funcint;

import javax.swing.JPopupMenu;

import com.dwarfeng.scheduler.module.project.abstruct.ProjectTreeNode;

/**
 * ʾ�����ܹ���������Ĺ��������һ��ɵ���һ���˵���
 * <p> ֻҪʵ������ӿڣ�����ʵ�֣���������Ĺ������˵�������������Ⱦ��ǩ�ϵ���Ҽ������ɵ���ָ���Ĳ˵���
 * @author DwArFeng
 * @since 1.8
 */
public interface PopupInTree extends ProjectTreeNode{
	
	public JPopupMenu getJPopupMenu();
}
