package com.dwarfeng.scheduler.typedef.desint;

import javax.swing.JComponent;
import javax.swing.JMenuBar;


/**
 * 编辑接口。
 * @author DwArFeng
 * @since 1.8
 */
public interface Editor<T extends Editable> {
	
	/**
	 * 获取其中的可编辑对象,请保证该方法不会返回null。
	 * @return 可编辑对象。
	 */
	public T getEditable();
	
	/**
	 * 返回编辑界面，请保证该方法不会返回null。
	 * @return 编辑器的界面。
	 */
	public JComponent getEditPanel();
	
	/**
	 * 返回编辑器的菜单栏。该方法返回null代表编辑器中没有菜单。
	 * @return 编辑器的菜单栏。
	 */
	public JMenuBar getMenuBar();
	
	/**
	 * 停止编辑动作。<p>
	 * 该方法在编辑器的容器关闭时被调用，由于停止编辑并保存（如果需要）时可能会出现异常，这时有可能需要终止停止编辑的过程。
	 * <br> 抛出异常也意味着终止停止过程。
	 * <br> 该方法不允许抛出任何异常，包括进行时异常。
	 * @return 是否终止停止过程，<code>false</code>表示终止停止过程。
	 */
	public boolean stopEdit();
	
	/**
	 * 强行停止编辑。<p>
	 * 该方法在编辑器强行停止（如对应的可编辑对象被删除时，其编辑器会强行停止）时被调用。
	 * <br>该方法不应该抛出任何异常，并且不建议在这个方法中进行保存等动作，建议该方法最好不实现任何动作。
	 */
	public void forceStopEdit();
	
}
