package com.dwarfeng.scheduler.typedef.funcint;

import javax.swing.JPopupMenu;

import com.dwarfeng.scheduler.gui.JProjectTree;
import com.dwarfeng.scheduler.typedef.abstruct.ObjectInProject;

/**
 * ʾ�����ܹ��ڹ��������һ��ɵ���һ���˵���
 * <p> ֻҪʵ������ӿڣ�����ʵ�֣���������Ĺ������˵�������������Ⱦ��ǩ�ϵ���Ҽ������ɵ���ָ���Ĳ˵���
 * @author DwArFeng
 * @since 1.8
 */
public interface PopupInTree extends ObjectInProject{
	
	public JPopupMenu getJPopupMenu(JProjectTree jProjectTree);
}
