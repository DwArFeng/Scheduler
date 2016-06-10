package com.dwarfeng.scheduler.project;

import java.io.File;
import java.util.List;

import javax.swing.text.DefaultEditorKit;
import javax.swing.text.PlainDocument;

import com.dwarfeng.scheduler.gui.PlainNoteEditor;
import com.dwarfeng.scheduler.io.ProjectHelper;
import com.dwarfeng.scheduler.typedef.desint.AbstractEditor;

/**
 * 纯文本笔记。
 * <p> 以TXT为基础的笔记，不包含文本格式，适用于大量的单一性的文本记录。
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
	 * 返回笔记本中的文档使用的编辑包。
	 * @return
	 */
	public DefaultEditorKit getEditorKit(){
		return (DefaultEditorKit) getTextAttachment().getEditorKit();
	}
	
}
