package com.dwarfeng.scheduler.project;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;
import javax.swing.text.Document;
import javax.swing.tree.MutableTreeNode;

import com.dwarfeng.func.gui.JMenuItemAction;
import com.dwarfeng.scheduler.core.Scheduler;
import com.dwarfeng.scheduler.gui.JNTDSettingDialog;
import com.dwarfeng.scheduler.gui.JProjectTree;
import com.dwarfeng.scheduler.typedef.abstruct.AbstractSerialParamableTreeNode;
import com.dwarfeng.scheduler.typedef.abstruct.ObjectInProject;
import com.dwarfeng.scheduler.typedef.abstruct.ObjectInProjectTree;
import com.dwarfeng.scheduler.typedef.abstruct.SerialParamable;
import com.dwarfeng.scheduler.typedef.desint.AbstractEditor;
import com.dwarfeng.scheduler.typedef.desint.Editable;
import com.dwarfeng.scheduler.typedef.funcint.Deleteable;
import com.dwarfeng.scheduler.typedef.funcint.Moveable;
import com.dwarfeng.scheduler.typedef.funcint.PopupInTree;

public abstract class Note extends AbstractSerialParamableTreeNode 
implements Editable,PopupInTree,Moveable,Deleteable{

	private static final long serialVersionUID = 8454528838313738006L;
	
	/**�ı�����*/
	protected TextAttachment attachment;
	/**�Ƿ������Զ�����*/
	private boolean lineWrap;
	/**����ָ���Լ��ı༭��*/
	protected AbstractEditor editor;
	
	/**
	 * ����һ��Ĭ�ϵıʼǣ�����ָ���ĸ�����
	 * @param attachment ָ�����ı�������
	 */
	public Note(TextAttachment attachment) {
		this(attachment,null,null,null);
	}
	/**
	 * 
	 * @param attachment
	 * @param name
	 * @param describe
	 * @param tagIdList
	 */
	public Note(TextAttachment attachment,String name,String describe,List<Integer> tagIdList) {
		this(attachment,true,name,describe,tagIdList);
	}
	/**
	 * 
	 * @param attachment
	 * @param lineWarp
	 * @param name
	 * @param describe
	 * @param tagIdList
	 */
	public Note(TextAttachment attachment,boolean lineWarp,String name,String describe,List<Integer> tagIdList) {
		//���ø��๹�췽��
		super(false,name,describe,tagIdList);
		//���ø���
		this.attachment = attachment == null ? 
				createDefaultTextAttachment() : attachment;
		this.attachment.setRootProject(getRootProject());
		//�����Ƿ��Զ�����
		this.setLineWarp(lineWarp);
	}

	/**
	 * �����ı�������
	 * @return �ı�������
	 */
	public TextAttachment getTextAttachment(){
		return attachment;
	}
	
	@Override
	public String getEditorTitle(){
		return (String) getParam(SerialParamable.NAME);
	}
	
	@Override
	public AbstractEditor getEditor() throws Exception {
		if(editor == null) editor = createEditor();
		return editor;
	}

	@Override
	public void firedEditorClose() {
		editor = null;
	}

	@Override
	public void load() throws Exception {
		attachment.load();		
	}

	/**
	 * ���ظñʼǵ��ĵ���
	 * <p> ���Ա�֤�÷�����Զ������nullֵ��
	 * @return ���ظñʼǵ��ĵ���
	 */
	public Document getDocument() {
		return attachment.getAttachObject();
	}

	public void setDocument(Document document) {
			attachment.setAttachObject(document);
	}

	@Override
	public void save() throws Exception {
		attachment.save();		
	}

	@Override
	public void release() {
		attachment.release();		
	}
	
	@Override
	public void setParent(MutableTreeNode newParent){
		super.setParent(newParent);
		if(newParent instanceof ObjectInProject){
			attachment.setRootProject((ObjectInProject) newParent);
		}
	}
	
	/**
	 * ���ö���û��ָ��ı༭��ʱ���÷����������ɱ༭����
	 * @throws Exception ���ɱ༭��ʱ�����쳣��
	 */
	protected abstract AbstractEditor createEditor() throws Exception;
	
	protected abstract TextAttachment createDefaultTextAttachment();
	
	/**
	 * �Ƿ��Զ����С�
	 * @return �ı��Ƿ��Զ����С�
	 */
	public boolean isLineWrap() {
		return lineWrap;
	}
	/**
	 * �����ı��Ƿ��Զ����С�
	 * @param lineWrap �ı��Ƿ��Զ����С�
	 */
	public void setLineWarp(boolean lineWrap) {
		this.lineWrap = lineWrap;
	}

	@Override
	public JPopupMenu getJPopupMenu(JProjectTree jProjectTree){
		JPopupMenu popup = new JPopupMenu();
		popup.add(new JMenuItemAction(
				null,																										//XXX 	��Ҫ�������ͼ��
				"��������",
				"���ıʼǵ�NTD����",
				KeyStroke.getKeyStroke(KeyEvent.VK_C,0),
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						JNTDSettingDialog dialog = new JNTDSettingDialog(
								Scheduler.getInstance().getGui(),
								"�ʼ������޸�",
								(String)getParam(RTFNote.NAME),
								(String)getParam(RTFNote.DESCRIBE)
						);
						dialog.setVisible(true);
						String name = dialog.getName();
						String describe = dialog.getDescribe();
						//XXX																										������Ҫ��ӱ�ǩ��ȥ
						//�ж��Ƿ�����ȡ����
						if(name == null) return;
						setParam(RTFNote.NAME, name);
						setParam(RTFNote.DESCRIBE, describe);
						//���¹�����������ģ��
						jProjectTree.repaintTreeModel();
						//չ�����ñʼǱ�
						jProjectTree.expandPath(Note.this);
					}
				}
		));
		popup.add(new JMenuItemAction(
				null,																										//XXX 	��Ҫ�������ͼ��
				"ɾ��",
				"���ɻָ���ɾ����ǰ�ıʼ�",
				KeyStroke.getKeyStroke(KeyEvent.VK_D,0),
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						ObjectInProjectTree parent = getParent();
						Scheduler.getInstance().requestDelete(Note.this);
						//���¹�����������ģ��
						jProjectTree.repaintTreeModel();
						//չ�����ñʼǱ�
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
	public void delete(){
		removeFromParent();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.funcint.Deleteable#getConfirmWord()
	 */
	@Override
	public String getConfirmWord(){
		return 
				"����ɾ����" + (String)getParam(RTFNote.NAME) + "\n"
				+ "��ǰ��������ɾ�����ĵ����ò������ɻָ�";
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.ObjectInProjectTree#getOtherObjectInProjects()
	 */
	@Override
	public Set<ObjectInProject> getOtherObjectInProjects() {
		Set<ObjectInProject> set = new HashSet<ObjectInProject>();
		set.add(attachment);
		return set;
	}
}
