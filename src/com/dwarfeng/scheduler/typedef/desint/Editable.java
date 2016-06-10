package com.dwarfeng.scheduler.typedef.desint;

import javax.swing.JDesktopPane;

import com.dwarfeng.scheduler.typedef.abstruct.ObjectInProject;

/**
 * 可编辑接口。
 * <p>只要是能编辑的对象，都应该实现这个接口。
 * <br>所谓的编辑，是指在程序的主面板({@linkplain JDesktopPane})中生成一个相应的编辑窗口，
 * 对这个对象进行主体内容（不是属性）的更改，多为附件，如附件文本就属于可编辑的对象。
 * 
 * @author DwArFeng
 * @since 1.8
 */
public interface Editable extends ObjectInProject{
	
	/**
	 * 返回编辑器界面所属的窗口的标题。
	 * @return 标题文本。
	 */
	public String getEditorTitle();
	/**
	 * 返回适用于该可编辑对象的编辑界面。
	 * @return 编辑界面。
	 * @throws Exception 获得编辑器时发生异常。
	 */
	public AbstractEditor getEditor() throws Exception ;
	
	/**
	 * 当指编辑器关闭时，由编辑器通知可编辑对象的方法。
	 */
	public void firedEditorClose();
	
	/**
	 * 加载需要编辑的对象。
	 * @throws Exception 出现异常。
	 */
	public void load() throws Exception;
	
	/**
	 * 保存需要编辑的对象。
	 * @throws Exception 出现异常。
	 */
	public void save() throws Exception;
	
	/**
	 * 释放内存。
	 */
	public void release();
	
//	/**
//	 * 返回这个编辑对象一共可以返回多少个提供编辑的对象。
//	 * @return 可供编辑的对象的数量。
//	 */
//	public int getTotleTarget();
//	
//	/**
//	 * 返回在指定位置的可编辑的对象。
//	 * @param index 指定的位置。
//	 * @return 可供编辑的对象。
//	 * @throws IndexOutOfBoundsException 位置超界时抛出的异常。
//	 */
//	public Object getTarget(int index) throws IndexOutOfBoundsException;
	
}
