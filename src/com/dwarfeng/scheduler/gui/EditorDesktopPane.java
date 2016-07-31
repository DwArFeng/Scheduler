package com.dwarfeng.scheduler.gui;

import java.awt.BorderLayout;
import java.beans.PropertyVetoException;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import com.dwarfeng.dwarffunction.io.CT;
import com.dwarfeng.scheduler.module.project.Project;
import com.dwarfeng.scheduler.typedef.desint.Editable;
import com.dwarfeng.scheduler.typedef.desint.Editor;

/**
 * 增加了指向主界面的桌面面板。
 * @author DwArFeng
 * @since 1.8
 */
public final class EditorDesktopPane extends JDesktopPane {
	
	/**
	 * 生成一个默认的桌面面板。
	 */
	public EditorDesktopPane() {
		super();
	}
	
	/**
	 * 为桌面面板添加编辑器窗体。
	 * <p> 如果之前桌面面板上已经有了编辑器实例，则将相应的窗口置为最前，而不在新建窗口。
	 * @param editable 编辑器窗体包含的编辑器。
	 * @throws NullPointerException 入口参数为<code>null</code>时抛出的异常。
	 * @throws IllegalArgumentException 入口参数违例时抛出的异常。
	 */
	public void edit(Editable editable){
		
		if(editable == null) throw new NullPointerException("Editable can't be null");
		
		JEditorInternalFrame frame = null;
		
		if((frame = hasEditor(editable)) == null){
			Editor<?> ed = editable.newEditor();
			if(ed == null) throw new IllegalArgumentException("可编辑对象返回编辑器null，该返回值违例，请联系开发人员");
			frame = new JEditorInternalFrame(editable.getEditorTitle(), ed);
			fitSize(frame);
			add(frame);
		}
		try {
			frame.setVisible(true);
			frame.setSelected(true);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * 使指定的内部窗体符合该桌面面板的大小，使内部窗体的长度和宽度均不超过该桌面面板的长度和宽度。
	 * @param comp 指定的内部窗体。
	 */
	private void fitSize(JComponent comp){
		if(comp.getHeight() > getHeight()) comp.setSize(comp.getWidth(), getHeight());
		if(comp.getWidth() > getWidth()) comp.setSize(getWidth(),comp.getHeight());
	}
	
	/**
	 * 关闭某个可编辑对象的编辑窗口。
	 * @param editable 指定的可编辑对象。
	 * @return 是否全部关闭成功，如果至少有一个界面没有关闭成功，则返回<code>false</code>。
	 */
	public boolean disposeEditor(Editable editable){
		
		boolean con = true;
		for(JEditorInternalFrame frame:getEditorInternalFrames()){
			if(frame.getEditable().equals(editable)){
				frame.setVisible(false);
			}
		}
		for(JEditorInternalFrame frame:getEditorInternalFrames()){
			if(frame.getEditable().equals(editable)){
				if(!frame.close()){
					frame.setVisible(true);
					con = false;
				}
			}
		}
		
		return con;
	}
	
	/**
	 * 强制关闭某个可编辑对象的编辑窗口。
	 * @param editable 指定的可编辑对象。
	 */
	public void forceDisposeEditor(Editable editable){
		
		for(JEditorInternalFrame frame:getEditorInternalFrames()){
			if(frame.getEditable().equals(editable)){
				frame.setVisible(false);
			}
		}
		for(JEditorInternalFrame frame:getEditorInternalFrames()){
			if(frame.getEditable().equals(editable)) frame.forceClose();
		}
		
	}
	
	/**
	 * 释放所有的指定工程下的编辑器。
	 * @param project 指定的工程。
	 * @return 是否全部关闭成功，如果至少有一个界面没有关闭成功，则返回<code>false</code>。
	 */
	public boolean disposeEditor(Project project){
		
		boolean con = true;
		
		for(JEditorInternalFrame frame:getEditorInternalFrames()){
			frame.setVisible(false);
		}
		for(JEditorInternalFrame frame:getEditorInternalFrames()){
			if(frame.getEditable().getRootProject() == null || frame.getEditable().getRootProject().equals(project)){
				if(!frame.close()){
					frame.setVisible(true);
					con = false;
				}
			}
		}
		
		return con;
	}
	
	/**
	 * 强行释放所有的指定工程下的编辑器。
	 * @param project 指定的工程。
	 */
	public void forceDisposeEditor(Project project){
		
		for(JEditorInternalFrame frame:getEditorInternalFrames()){
			frame.setVisible(false);
		}
		for(JEditorInternalFrame frame:getEditorInternalFrames()){
			if(frame.getEditable().getRootProject() == null || frame.getEditable().getRootProject().equals(project))
				frame.forceClose();
		}
		
	}
	
	/**
	 * 释放所有的编辑器。
	 * @return 是否全部关闭成功，如果至少有一个界面没有关闭成功，则返回<code>false</code>。
	 */
	public boolean disposeAllEditor(){
		
		boolean con = true;
		
		for(JEditorInternalFrame frame:getEditorInternalFrames()){
			frame.setVisible(false);
		}
		for(JEditorInternalFrame frame:getEditorInternalFrames()){
			if(!frame.close()){
				frame.setVisible(true);
				con = false;
			}
		}
		
		return con;
	}
	
	/**
	 * 强行释放所有的编辑器。
	 */
	public void forceDisposeAllEditor(){
		
		for(JEditorInternalFrame frame:getEditorInternalFrames()){
			frame.setVisible(false);
		}
		for(JEditorInternalFrame frame:getEditorInternalFrames())
			frame.forceClose();
		
	}
	
	/**
	 * 返回桌面面板中的所有编辑器窗体。
	 * @return 所有编辑器窗体组成的集合。
	 */
	private Set<JEditorInternalFrame> getEditorInternalFrames(){
		JInternalFrame[] frames = getAllFrames();
		Set<JEditorInternalFrame> editorFrames = new HashSet<JEditorInternalFrame>();
		for(JInternalFrame frame:frames){
			if(frame instanceof JEditorInternalFrame) editorFrames.add((JEditorInternalFrame) frame);
		}
		return editorFrames;
	}
	
	/**
	 * 返回桌面面板是否已经拥有了指定的编辑对象。
	 * @param editable 指定的可编辑对象。
	 * @return 如果拥有指定的编辑器，返回这个编辑器，否则返回null。
	 */
	private JEditorInternalFrame hasEditor(Editable editable){
		for(JEditorInternalFrame frame: getEditorInternalFrames()){
			if(frame.getEditable().equals(editable)) return frame;
		}
		return null;
	}
}


/**
 * 编辑器框架。
 * <p> 该框架负责容纳编辑器，并可以被放置在主界面的桌面面板中。
 * @author DwArFeng
 * @since 1.8
 */
final class JEditorInternalFrame extends JInternalFrame {
	
	/**指向的编辑器*/
	private final Editor<?> editor;
	
	/**
	 * 
	 * @param title
	 * @param editor
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 */
	public JEditorInternalFrame(String title ,Editor<?> editor) throws NullPointerException,IllegalArgumentException{
		super(title == null || title == "" ? "未命名" : title);
		//editable不能为null
		if(editor == null) throw new NullPointerException("Editable can't be null");
		if(editor.getEditable() == null) throw new IllegalArgumentException("返回可编辑对象值为null，该值违例，请联系开发人员");
		if(editor.getEditPanel() == null) throw new IllegalArgumentException("返回编辑面板对象值为null，该值违例，请联系开发人员");
		//初始化自身变量
		this.editor = editor;
		//初始化自身属性
		setClosable(true);
		setIconifiable(true);
		setMaximizable(true);
		setResizable(true);
		setDefaultCloseOperation(JInternalFrame.DO_NOTHING_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		setSize(800,600);
		//设置成员属性
		getContentPane().add(editor.getEditPanel(),BorderLayout.CENTER);
		setJMenuBar(editor.getMenuBar());
		//注册关闭侦听
		addInternalFrameListener(new InternalFrameAdapter() {
			@Override
			public void internalFrameClosing(InternalFrameEvent e) {
				setVisible(false);
				Runnable runnable = new Runnable() {
					@Override
					public void run() {
						if(!close()) setVisible(true);
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
	
	/**
	 * 关闭界面。
	 * <p> 该方法首先调用编辑器的{@linkplain Editor#stopEdit()}方法，其后根据该方法的返回值进行进一步调度。
	 * <br> 如果{@linkplain Editor#stopEdit()}抛出异常，为了保证程序的流畅运行，在打印异常的同时，会强行关闭
	 * @return 关闭过程是否成功，<code>true</code>表示成功。
	 */
	public boolean close(){
		boolean con = true;
		try{
			con = editor.stopEdit();
		}catch(Exception e){
			CT.trace("编辑器的停止方法抛出异常，该现象违例，请联系开发人员");
			e.printStackTrace();
			forceClose();
			return true;
		}
		if(con) this.dispose();
		return con;
	}
	
	/**
	 * 强行关闭界面。
	 */
	public void forceClose(){
		try{
			this.editor.forceStopEdit();
		}catch(Exception e){
			CT.trace("编辑器的强行停止方法抛出异常，该现象违例，请联系开发人员");
			e.printStackTrace();
		}finally{
			this.dispose();
		}
	}
	
}
