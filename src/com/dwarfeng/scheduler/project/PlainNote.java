package com.dwarfeng.scheduler.project;

import java.io.File;
import java.util.List;

import javax.swing.text.DefaultEditorKit;
import javax.swing.text.PlainDocument;

import com.dwarfeng.scheduler.gui.PlainNoteEditor;
import com.dwarfeng.scheduler.io.ProjectHelper;
import com.dwarfeng.scheduler.typedef.desint.AbstractEditor;

/**
 * ���ı��ʼǡ�
 * <p> ��TXTΪ�����ıʼǣ��������ı���ʽ�������ڴ����ĵ�һ�Ե��ı���¼��
 * @author DwArFeng
 * @since 1.8
 */
public final class PlainNote extends Note {
	
	private static final long serialVersionUID = -7271300020601903537L;

	public PlainNote(PlainTextAttachment attachment) {
		super(attachment);
		// TODO Auto-generated constructor stub
	}

	public PlainNote(PlainTextAttachment attachment, String name, String describe,List<Integer> tagIdList) {
		super(attachment, name, describe, tagIdList);
		// TODO Auto-generated constructor stub
	}
	
	public PlainNote(PlainTextAttachment attachment,boolean lineWarp, String name, String describe,List<Integer> tagIdList) {
		super(attachment,lineWarp,name, describe, tagIdList);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public PlainTextAttachment getTextAttachment(){
		return (PlainTextAttachment) super.getTextAttachment();
	}
	
	@Override
	protected AbstractEditor createEditor() {
		return new  PlainNoteEditor(this);
	}

	@Override
	protected TextAttachment createDefaultTextAttachment() {
		return new PlainTextAttachment(ProjectHelper.genSchedulerURL(getRootProject(), "docs" + File.separator,	 ".txt"));
	}
	
	@Override
	public PlainDocument getDocument() {
		return (PlainDocument) super.getDocument();
	}
	
	/**
	 * ���رʼǱ��е��ĵ�ʹ�õı༭����
	 * @return
	 */
	public DefaultEditorKit getEditorKit(){
		return (DefaultEditorKit) getTextAttachment().getEditorKit();
	}
	
}
