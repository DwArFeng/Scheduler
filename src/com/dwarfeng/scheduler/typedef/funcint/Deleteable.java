package com.dwarfeng.scheduler.typedef.funcint;

import javax.swing.tree.TreeNode;

import com.dwarfeng.scheduler.typedef.pabstruct.ObjectInProjectTree;

/**
 * 可删除接口。
 * <p> 该接口指示着有个在工程树中的文件可以删除。
 * <br> 主界面的工程树面板为这类对象提供了<code>delete</code>快捷键的定义。
 * @author DwArFeng
 * @since 1.8
 */
public interface Deleteable extends ObjectInProjectTree,TreeNode{
	
//	/**
//	 * 对象进行自我删除的方法。
//	 */
//	public void delete();
	/**
	 * 返回删除之前用于确认是否恢复的语句。
	 * <p> 当返回值为null时，使用程序中自带的确认语句。
	 * @return 确认语句。
	 */
	public String getConfirmWord();
}
