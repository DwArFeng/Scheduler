package com.dwarfeng.scheduler.tools;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

import com.dwarfeng.dwarffunction.gui.JMenuItemAction;
import com.dwarfeng.scheduler.core.Scheduler133;
import com.dwarfeng.scheduler.module.SProjectOperationHelper;
import com.dwarfeng.scheduler.module.project.funcint.Deleteable;
import com.dwarfeng.scheduler.module.project.funcint.SerialParamSetable;
import com.dwarfeng.scheduler.typedef.desint.Editable;

/**
 * 提供众多通用的右键菜单按钮的工厂类。
 * @author DwArFeng
 * @since 1.8
 */
public final class PopupMenuActions {
	
	/**
	 * 新建一个编辑按钮动作。
	 * @param describe 鼠标长久停留时显示的描述。
	 * @param context 有关编辑的上下文。
	 * @return 编辑按钮动作。
	 */
	public static Action newEditItem(String describe,Editable context){
		if(context == null) throw new NullPointerException("Context can't be null");
		
		return new JMenuItemAction.Productor()
				.icon(new ImageIcon(Scheduler133.class.getResource("/resource/menu/edit.png")))
				.name("编辑")
				.description(describe)
				.keyStorke(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0))
				.listener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						SProjectOperationHelper.edit(context);
					}
				})
				.product();
	}
	
	/**
	 * 创建一个更改属性按钮动作。
	 * @param describe 鼠标长久停留时显示的描述。
	 * @param context 有关属性设置的上下文。
	 * @return 更改属性按钮动作。
	 */
	public static Action newParamSetItem(String describe,SerialParamSetable context){
		if(context == null) throw new NullPointerException("Context can't be null");
		
		return new JMenuItemAction.Productor()
				.icon(new ImageIcon(Scheduler133.class.getResource("/resource/menu/paramPanel.png")))
				.name("更改属性")
				.description(describe)
				.keyStorke(KeyStroke.getKeyStroke(KeyEvent.VK_F2,0))
				.listener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						SProjectOperationHelper.setSerialParam(context);
					}
				})
				.product();
	}
	
	/**
	 * 创建一个删除对象的按钮动作。
	 * @param describe 鼠标长久停留时显示的描述。
	 * @param context 有关删除对象的上下文。
	 * @return 删除对象按钮动作。
	 */
	public static Action newDeleteItem(String describe,Deleteable context){
		if(context == null) throw new NullPointerException("Context can't be null");
		
		return new JMenuItemAction.Productor()
				.icon(new ImageIcon(Scheduler133.class.getResource("/resource/menu/deleteFile.png")))
				.name("删除")
				.description("不可恢复地删除当前的笔记")
				.keyStorke(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE,0))
				.listener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						SProjectOperationHelper.requestDelete(context);
					}
				})
				.product();
	}
	
	/**
	 *  创建一个新建对象的按钮动作。
	 * @param describe 鼠标长久停留时显示的描述。
	 * @param context 有关新建动作的上下文。
	 * @return 新建对象按钮动作。
	 */
	public static Action newNewItem(String describe,ActionListener context){
		if(context == null) throw new NullPointerException("Context can't be null");

		return new JMenuItemAction.Productor()
				.icon(new ImageIcon(Scheduler133.class.getResource("/resource/menu/new.png")))
				.name("新建")
				.description(describe)
				.keyStorke(KeyStroke.getKeyStroke(KeyEvent.VK_N,0))
				.listener(context)
				.product();
	}
	
	private PopupMenuActions(){
		//Do nothing
	}
}
