package com.dwarfeng.scheduler.gui;

import java.awt.BorderLayout;

import javax.swing.JInternalFrame;

import com.dwarfeng.scheduler.core.RunnerQueue;
import com.dwarfeng.scheduler.typedef.desint.AbstractEditor;
import com.dwarfeng.scheduler.typedef.desint.Editable;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

/**
 * �༭����ܡ�
 * <p> �ÿ�ܸ������ɱ༭���������Ա����������������������С�
 * @author DwArFeng
 * @since 1.8
 */
public final class JEditorInternalFrame extends JInternalFrame {
	
	private static final long serialVersionUID = 8939280630554793866L;
	
	/**ָ��ı༭��*/
	private AbstractEditor editor;
	
	
	public JEditorInternalFrame() throws Exception {
		this(null,null);
	}

	public JEditorInternalFrame(String title ,Editable editable) throws Exception {
		super(title == null || title == "" ? "δ����" : title);
		//editable����Ϊnull
		if(editable == null) throw new NullPointerException("Editable can't be null");
		//��ʼ���������
		this.editor = editable.getEditor();
		//��ʼ����������
		setClosable(true);
		setIconifiable(true);
		setMaximizable(true);
		setResizable(true);
		setDefaultCloseOperation(JInternalFrame.DO_NOTHING_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		setSize(800,600);
		//���ó�Ա����
		getContentPane().add(editor,BorderLayout.CENTER);
		setJMenuBar(editor.getMenuBar());
		//ע��ر�����
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
	 * ���ظñ༭���������༭�Ŀɱ༭����
	 * @return �ɱ༭����
	 */
	public Editable getEditable(){
		return this.editor.getEditable();
	}
	
	@Override
	public void dispose(){
		//�����ĵ�
		editor.dispose();
		super.dispose();
	}
}
