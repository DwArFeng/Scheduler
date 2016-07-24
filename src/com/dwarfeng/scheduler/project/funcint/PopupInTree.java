package com.dwarfeng.scheduler.project.funcint;

import javax.swing.JPopupMenu;

import com.dwarfeng.scheduler.module.PProjectTreeNode;

/**
 * 示意着能够在主界面的工程树中右击可弹出一个菜单。
 * <p> 只要实现这个接口，就能实现：在主界面的工程树菜单中在其对象的渲染标签上点击右键，即可弹出指定的菜单。
 * @author DwArFeng
 * @since 1.8
 */
public interface PopupInTree extends PProjectTreeNode{
	
	public JPopupMenu getJPopupMenu();
}
