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
 * ������ָ���������������塣
 * @author DwArFeng
 * @since 1.8
 */
public class JDesktopPaneS extends JDesktopPane {
	
	private static final long serialVersionUID = 4664112960116299189L;
	/**
	 * ����һ��Ĭ�ϵ�������塣
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
	 * Ϊ���������ӱ༭�����塣
	 * <p> ���֮ǰ����������Ѿ����˱༭��ʵ��������Ӧ�Ĵ�����Ϊ��ǰ���������½����ڡ�
	 * @param title �༭������ı��⡣
	 * @param editable �༭����������ı༭����
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
			CT.trace("����ĳЩԭ�򣬱༭��û����ȷ����");
			if(frame != null){
				frame.dispose();
			}
		}

	}
	
	/**
	 * ʹָ�����ڲ�������ϸ��������Ĵ�С��ʹ�ڲ�����ĳ��ȺͿ�Ⱦ����������������ĳ��ȺͿ�ȡ�
	 * @param frame ָ�����ڲ����塣
	 */
	private void fitSize(JInternalFrame frame){
		if(frame.getHeight() > getHeight()) frame.setSize(frame.getWidth(), getHeight());
		if(frame.getWidth() > getWidth()) frame.setSize(getWidth(),frame.getHeight());
	}
	
	/**
	 * �ͷ����е�ָ�������µı༭����
	 * @param project ָ���Ĺ��̡�
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
	 * �ͷ����еı༭����
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
	 * ������������е����б༭�����塣
	 * @return ���б༭��������ɵļ��ϡ�
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
	 * ������������Ƿ��Ѿ�ӵ����ָ���ı༭����
	 * @param editable ָ���Ŀɱ༭����
	 * @return ���ӵ��ָ���ı༭������������༭�������򷵻�null��
	 */
	private JEditorInternalFrame hasEditor(Editable editable){
		for(JEditorInternalFrame frame: getEditorInternalFrames()){
			if(frame.getEditable().equals(editable)) return frame;
		}
		return null;
	}
}
