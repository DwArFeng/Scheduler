package com.dwarfeng.scheduler.typedef.funcint;

import javax.swing.JPopupMenu;

import com.dwarfeng.scheduler.typedef.abstruct.ObjectInProjectTree;

/**
 * ʾ�����ܹ���������Ĺ��������һ��ɵ���һ���˵���
 * <p> ֻҪʵ������ӿڣ�����ʵ�֣���������Ĺ������˵�������������Ⱦ��ǩ�ϵ���Ҽ������ɵ���ָ���Ĳ˵���
 * @author DwArFeng
 * @since 1.8
 */
public interface PopupInTree extends ObjectInProjectTree{
	
	public JPopupMenu getJPopupMenu();
}
