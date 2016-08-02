package com.dwarfeng.scheduler.typedef.desint;

import javax.swing.JDesktopPane;

import com.dwarfeng.scheduler.module.project.abstruct.ProjectTreeNode;

/**
 * 可编辑接口。
 * <p>只要是能编辑的对象，都应该实现这个接口。
 * <br>所谓的编辑，是指在程序的主面板({@linkplain JDesktopPane})中生成一个相应的编辑窗口，
 * 对这个对象进行主体内容（不是属性）的更改，多为附件，如附件文本就属于可编辑的对象。
 * 
 * @author DwArFeng
 * @since 1.8
 */
public interface Editable extends ProjectTreeNode{
	
	/**
	 * 返回编辑器界面所属的窗口的标题。
	 * @return 标题文本。
	 */
	public String getEditorTitle();
	
	/**
	 * 生成编辑器对象的新实例。
	 * @return 生成的编辑器新实例。
	 */
	public Editor<?> newEditor();
	
}
