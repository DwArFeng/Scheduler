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
 * ������ָ���������������塣
 * @author DwArFeng
 * @since 1.8
 */
public final class EditorDesktopPane extends JDesktopPane {
	
	/**
	 * ����һ��Ĭ�ϵ�������塣
	 */
	public EditorDesktopPane() {
		super();
	}
	
	/**
	 * Ϊ���������ӱ༭�����塣
	 * <p> ���֮ǰ����������Ѿ����˱༭��ʵ��������Ӧ�Ĵ�����Ϊ��ǰ���������½����ڡ�
	 * @param editable �༭����������ı༭����
	 * @throws NullPointerException ��ڲ���Ϊ<code>null</code>ʱ�׳����쳣��
	 * @throws IllegalArgumentException ��ڲ���Υ��ʱ�׳����쳣��
	 */
	public void edit(Editable editable){
		
		if(editable == null) throw new NullPointerException("Editable can't be null");
		
		JEditorInternalFrame frame = null;
		
		if((frame = hasEditor(editable)) == null){
			Editor<?> ed = editable.newEditor();
			if(ed == null) throw new IllegalArgumentException("�ɱ༭���󷵻ر༭��null���÷���ֵΥ��������ϵ������Ա");
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
	 * ʹָ�����ڲ�������ϸ��������Ĵ�С��ʹ�ڲ�����ĳ��ȺͿ�Ⱦ����������������ĳ��ȺͿ�ȡ�
	 * @param comp ָ�����ڲ����塣
	 */
	private void fitSize(JComponent comp){
		if(comp.getHeight() > getHeight()) comp.setSize(comp.getWidth(), getHeight());
		if(comp.getWidth() > getWidth()) comp.setSize(getWidth(),comp.getHeight());
	}
	
	/**
	 * �ر�ĳ���ɱ༭����ı༭���ڡ�
	 * @param editable ָ���Ŀɱ༭����
	 * @return �Ƿ�ȫ���رճɹ������������һ������û�йرճɹ����򷵻�<code>false</code>��
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
	 * ǿ�ƹر�ĳ���ɱ༭����ı༭���ڡ�
	 * @param editable ָ���Ŀɱ༭����
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
	 * �ͷ����е�ָ�������µı༭����
	 * @param project ָ���Ĺ��̡�
	 * @return �Ƿ�ȫ���رճɹ������������һ������û�йرճɹ����򷵻�<code>false</code>��
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
	 * ǿ���ͷ����е�ָ�������µı༭����
	 * @param project ָ���Ĺ��̡�
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
	 * �ͷ����еı༭����
	 * @return �Ƿ�ȫ���رճɹ������������һ������û�йرճɹ����򷵻�<code>false</code>��
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
	 * ǿ���ͷ����еı༭����
	 */
	public void forceDisposeAllEditor(){
		
		for(JEditorInternalFrame frame:getEditorInternalFrames()){
			frame.setVisible(false);
		}
		for(JEditorInternalFrame frame:getEditorInternalFrames())
			frame.forceClose();
		
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


/**
 * �༭����ܡ�
 * <p> �ÿ�ܸ������ɱ༭���������Ա����������������������С�
 * @author DwArFeng
 * @since 1.8
 */
final class JEditorInternalFrame extends JInternalFrame {
	
	/**ָ��ı༭��*/
	private final Editor<?> editor;
	
	/**
	 * 
	 * @param title
	 * @param editor
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 */
	public JEditorInternalFrame(String title ,Editor<?> editor) throws NullPointerException,IllegalArgumentException{
		super(title == null || title == "" ? "δ����" : title);
		//editable����Ϊnull
		if(editor == null) throw new NullPointerException("Editable can't be null");
		if(editor.getEditable() == null) throw new IllegalArgumentException("���ؿɱ༭����ֵΪnull����ֵΥ��������ϵ������Ա");
		if(editor.getEditPanel() == null) throw new IllegalArgumentException("���ر༭������ֵΪnull����ֵΥ��������ϵ������Ա");
		//��ʼ���������
		this.editor = editor;
		//��ʼ����������
		setClosable(true);
		setIconifiable(true);
		setMaximizable(true);
		setResizable(true);
		setDefaultCloseOperation(JInternalFrame.DO_NOTHING_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		setSize(800,600);
		//���ó�Ա����
		getContentPane().add(editor.getEditPanel(),BorderLayout.CENTER);
		setJMenuBar(editor.getMenuBar());
		//ע��ر�����
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
	 * ���ظñ༭���������༭�Ŀɱ༭����
	 * @return �ɱ༭����
	 */
	public Editable getEditable(){
		return this.editor.getEditable();
	}
	
	/**
	 * �رս��档
	 * <p> �÷������ȵ��ñ༭����{@linkplain Editor#stopEdit()}�����������ݸ÷����ķ���ֵ���н�һ�����ȡ�
	 * <br> ���{@linkplain Editor#stopEdit()}�׳��쳣��Ϊ�˱�֤������������У��ڴ�ӡ�쳣��ͬʱ����ǿ�йر�
	 * @return �رչ����Ƿ�ɹ���<code>true</code>��ʾ�ɹ���
	 */
	public boolean close(){
		boolean con = true;
		try{
			con = editor.stopEdit();
		}catch(Exception e){
			CT.trace("�༭����ֹͣ�����׳��쳣��������Υ��������ϵ������Ա");
			e.printStackTrace();
			forceClose();
			return true;
		}
		if(con) this.dispose();
		return con;
	}
	
	/**
	 * ǿ�йرս��档
	 */
	public void forceClose(){
		try{
			this.editor.forceStopEdit();
		}catch(Exception e){
			CT.trace("�༭����ǿ��ֹͣ�����׳��쳣��������Υ��������ϵ������Ա");
			e.printStackTrace();
		}finally{
			this.dispose();
		}
	}
	
}
