package com.dwarfeng.scheduler.gui;

import java.awt.Component;
import java.beans.PropertyVetoException;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

import com.dwarfeng.func.io.CT;
import com.dwarfeng.scheduler.project.Project;
import com.dwarfeng.scheduler.typedef.desint.Editable;

/**
 * 增加了指向主界面的桌面面板。
 * @author DwArFeng
 * @since 1.8
 */
public class JDesktopPaneS extends JDesktopPane {
	
	private static final long serialVersionUID = 4664112960116299189L;
	/**
	 * 生成一个默认的桌面面板。
	 */
	public JDesktopPaneS() {
		super();
	}
	/*
	 * (non-Javadoc)
	 * @see java.awt.Container#add(java.awt.Component)
	 */
	@Override
	public Component add(Component comp){
		if(!(comp instanceof JInternalFrame)){
			return super.add(comp);
		}else{
			JInternalFrame frame = (JInternalFrame) comp;
			fitSize(frame);
			return super.add(frame);
		}
	}
	
	/**
	 * 为桌面面板添加编辑器窗体。
	 * <p> 如果之前桌面面板上已经有了编辑器实例，则将相应的窗口置为最前，而不在新建窗口。
	 * @param title 编辑器窗体的标题。
	 * @param editable 编辑器窗体包含的编辑器。
	 */
	public void edit(Editable editable) {
		JEditorInternalFrame frame = null;
		try{
			if((frame = hasEditor(editable)) == null){
				frame = new JEditorInternalFrame(editable.getEditorTitle(), editable);
				frame.setVisible(true);
				add(frame);
			}
			try {
				frame.setSelected(true);
			} catch (PropertyVetoException e) {
				e.printStackTrace();
			}
		}catch(Exception e){
			e.printStackTrace();
			CT.trace("由于某些原因，编辑器没有正确生成");
			if(frame != null){
				frame.dispose();
			}
		}

	}
	
	/**
	 * 使指定的内部窗体符合该桌面面板的大小，使内部窗体的长度和宽度均不超过该桌面面板的长度和宽度。
	 * @param frame 指定的内部窗体。
	 */
	private void fitSize(JInternalFrame frame){
		if(frame.getHeight() > getHeight()) frame.setSize(frame.getWidth(), getHeight());
		if(frame.getWidth() > getWidth()) frame.setSize(getWidth(),frame.getHeight());
	}
	
	/**
	 * 释放所有的指定工程下的编辑器。
	 * @param project 指定的工程。
	 */
	public void disposeEditor(Project project){
		for(JEditorInternalFrame frame:getEditorInternalFrames()){
			frame.setVisible(false);
		}
		for(JEditorInternalFrame frame:getEditorInternalFrames()){
			if(frame.getEditable().getRootProject() == null || frame.getEditable().getRootProject().equals(project))
				frame.dispose();
		}
	}
	/**
	 * 释放所有的编辑器。
	 */
	public void disposeAllEditor(){
		for(JEditorInternalFrame frame:getEditorInternalFrames()){
			frame.setVisible(false);
		}
		for(JEditorInternalFrame frame:getEditorInternalFrames()){
			frame.dispose();
		}
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
