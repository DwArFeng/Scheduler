package com.dwarfeng.scheduler.gui;

import java.awt.BorderLayout;

import javax.swing.JInternalFrame;

import com.dwarfeng.scheduler.core.RunnerQueue;
import com.dwarfeng.scheduler.typedef.desint.AbstractEditor;
import com.dwarfeng.scheduler.typedef.desint.Editable;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

/**
 * 编辑器框架。
 * <p> 该框架负责容纳编辑器，并可以被放置在主界面的桌面面板中。
 * @author DwArFeng
 * @since 1.8
 */
public final class JEditorInternalFrame extends JInternalFrame {
	
	private static final long serialVersionUID = 8939280630554793866L;
	
	/**指向的编辑器*/
	private AbstractEditor editor;
	
	
	public JEditorInternalFrame() throws Exception {
		this(null,null);
	}

	public JEditorInternalFrame(String title ,Editable editable) throws Exception {
		super(title == null || title == "" ? "未命名" : title);
		//editable不能为null
		if(editable == null) throw new NullPointerException("Editable can't be null");
		//初始化自身变量
		this.editor = editable.getEditor();
		//初始化自身属性
		setClosable(true);
		setIconifiable(true);
		setMaximizable(true);
		setResizable(true);
		setDefaultCloseOperation(JInternalFrame.DO_NOTHING_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		setSize(800,600);
		//设置成员属性
		getContentPane().add(editor,BorderLayout.CENTER);
		setJMenuBar(editor.getMenuBar());
		//注册关闭侦听
		addInternalFrameListener(new InternalFrameAdapter() {
			@Override
			public void internalFrameClosing(InternalFrameEvent e) {
				setVisible(false);
				Runnable runnable = new Runnable() {
					@Override
					public void run() {
						dispose();
					}
				};
				RunnerQueue.invoke(runnable);
			}
		});
	}

	/**
	 * 返回该编辑器窗体所编辑的可编辑对象。
	 * @return 可编辑对象。
	 */
	public Editable getEditable(){
		return this.editor.getEditable();
	}
	
	@Override
	public void dispose(){
		//保存文档
		editor.dispose();
		super.dispose();
	}
}
