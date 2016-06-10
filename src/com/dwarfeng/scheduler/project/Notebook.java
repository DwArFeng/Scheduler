package com.dwarfeng.scheduler.project;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.List;
import java.util.Set;

import javax.swing.JMenu;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;
import javax.swing.tree.MutableTreeNode;

import com.dwarfeng.func.gui.JMenuItemAction;
import com.dwarfeng.scheduler.core.Scheduler;
import com.dwarfeng.scheduler.gui.JNTDSettingDialog;
import com.dwarfeng.scheduler.gui.JProjectTree;
import com.dwarfeng.scheduler.io.ProjectHelper;
import com.dwarfeng.scheduler.typedef.abstruct.AbstractSerialParamableTreeNode;
import com.dwarfeng.scheduler.typedef.abstruct.ObjectInProject;
import com.dwarfeng.scheduler.typedef.abstruct.ObjectInProjectTree;
import com.dwarfeng.scheduler.typedef.funcint.Deleteable;
import com.dwarfeng.scheduler.typedef.funcint.Moveable;
import com.dwarfeng.scheduler.typedef.funcint.PopupInTree;

/**
 * �ʼǱ���
 * @author DwArFeng
 * @since 1.8
 */
public class Notebook extends AbstractSerialParamableTreeNode 
	implements PopupInTree,Deleteable,Moveable{
	
	private static final long serialVersionUID = 6523774284338795747L;
	
	/**
	 * ����һ��Ĭ�ϵıʼǱ���
	 */
	public Notebook(){
		this(null,null,null,null);
	}
	/**
	 * 
	 * @param name
	 * @param describe
	 * @param tagIdList
	 * @param notes
	 */
	public Notebook(String name,String describe,List<Integer> tagIdList,List<Note> notes){
		//���ø��๹�췽��
		super(true,name,describe,tagIdList);
		//Ϊ������ӱʼǱ�
		if(notes != null){
			for(Note note:notes){
				if(note != null) add(note);
			}
		}
	}
	@Override
	public void insert(MutableTreeNode newChild, int childIndex) {
		//ֻ�ܲ���ʼ�
		if(!(newChild instanceof Note)) throw new IllegalArgumentException("New child must instance of Note");
		super.insert(newChild, childIndex);
	}
	
	
	@Override
	public JPopupMenu getJPopupMenu(JProjectTree jProjectTree) {
		JPopupMenu popup = new JPopupMenu();
		JMenu notebookTreePopupNew = new JMenu("�½�");
		notebookTreePopupNew.add(new JMenuItemAction(
				null,																										//XXX 	��Ҫ�������ͼ��
				"���ı��ʼ�", 
				"�½�һ�����ı���TXT���ʼ�", 
				KeyStroke.getKeyStroke(KeyEvent.VK_T,0),
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						//�����ƺ�������������塣
						JNTDSettingDialog dialog = new JNTDSettingDialog(Scheduler.getInstance().getGui(),"�´��ı��ʼ�");
						dialog.setVisible(true);
						//��ȡ���ƣ���ͨ���ж������Ƿ�Ϊnull���ж��Ƿ�����ȡ������
						String name = dialog.getName();
						if(name == null) return;
						String describe = dialog.getDescribe();
						//�����µıʼǲ����ò���
						PlainNote note = new PlainNote(new PlainTextAttachment(ProjectHelper.genSchedulerURL(getRootProject(), "docs" + File.separator, ".txt")));
						note.setParam(RTFNote.NAME, name);
						note.setParam(RTFNote.DESCRIBE, describe);
						//�򹤳����е�ָ���ʼǱ���Ӵ˱ʼ�
						add(note);
						//���»��ƹ���������ģ��
						jProjectTree.repaintTreeModel();
						//չ�����½��ıʼ�
						jProjectTree.expandPath(note);
					}
				}
		));
		notebookTreePopupNew.add(new JMenuItemAction(
				null,																										//XXX 	��Ҫ�������ͼ��
				"���ı��ʼ�", 
				"�½�һ�����ı���RTF���ʼ�", 
				KeyStroke.getKeyStroke(KeyEvent.VK_R,0),
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						//�����ƺ�������������塣
						JNTDSettingDialog dialog = new JNTDSettingDialog(Scheduler.getInstance().getGui(),"�¸��ı��ʼ�");
						dialog.setVisible(true);
						//��ȡ���ƣ���ͨ���ж������Ƿ�Ϊnull���ж��Ƿ�����ȡ������
						String name = dialog.getName();
						if(name == null) return;
						String describe = dialog.getDescribe();
						//�����µıʼǲ����ò���
						RTFNote note = new RTFNote(new StyledTextAttachment(ProjectHelper.genSchedulerURL(getRootProject(), "docs" + File.separator, ".rtf")));
						note.setParam(RTFNote.NAME, name);
						note.setParam(RTFNote.DESCRIBE, describe);
						//�򹤳����е�ָ���ʼǱ���Ӵ˱ʼ�
						add(note);
						//���»��ƹ���������ģ��
						jProjectTree.repaintTreeModel();
						//չ�����½��ıʼ�
						jProjectTree.expandPath(note);
					}
				}
		));
		
		popup = new JPopupMenu("�ʼǱ�");
		
		popup.add(notebookTreePopupNew);
		popup.add(new JMenuItemAction(
				null,																										//XXX 	��Ҫ�������ͼ��
				"��������",
				"���ıʼǱ���NTD����",
				KeyStroke.getKeyStroke(KeyEvent.VK_C,0),
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						JNTDSettingDialog dialog = new JNTDSettingDialog(
								Scheduler.getInstance().getGui(),
								"�ʼ������޸�",
								(String)getParam(Notebook.NAME),
								(String)getParam(Notebook.DESCRIBE)
						);
						dialog.setVisible(true);
						String name = dialog.getName();
						String describe = dialog.getDescribe();
						//XXX																										������Ҫ��ӱ�ǩ��ȥ
						//�ж��Ƿ�����ȡ����
						if(name == null) return;
						setParam(Notebook.NAME, name);
						setParam(Notebook.DESCRIBE, describe);
						//���»��ƹ���������ģ��
						jProjectTree.repaintTreeModel();
						//չ�����½��ıʼǱ�
						jProjectTree.expandPath(Notebook.this);
					}
				}
		));
		popup.add(new JMenuItemAction(
				null,																										//XXX 	��Ҫ�������ͼ��
				"ɾ��",
				"���ɻָ���ɾ����ǰ�ıʼǱ�",
				KeyStroke.getKeyStroke(KeyEvent.VK_D,0),
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						ObjectInProjectTree parent = getParent();
						Scheduler.getInstance().requestDelete(Notebook.this);
						jProjectTree.repaintTreeModel();
						//չ�����½��ıʼǱ�
						jProjectTree.expandPath(parent);
					}
				}
		));
		
		return popup;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.funcint.Deleteable#delete()
	 */
	@Override
	public void delete() {
		removeFromParent();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.funcint.Deleteable#getConfirmWord()
	 */
	@Override
	public String getConfirmWord() {
		return
				"����ɾ����" + (String)getParam(Notebook.NAME) + "\n"
				+ "��ǰ��������ɾ���ñʼǱ����ò������ɻָ�";
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.ObjectInProjectTree#getOtherObjectInProjects()
	 */
	@Override
	public Set<ObjectInProject> getOtherObjectInProjects() {
		return null;
	}
	
}
