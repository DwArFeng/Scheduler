package com.dwarfeng.scheduler.project;

import java.awt.Font;
import java.io.File;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.text.StyledDocument;
import javax.swing.text.StyledEditorKit;

import com.dwarfeng.scheduler.core.Scheduler;
import com.dwarfeng.scheduler.gui.RTFNoteEditor;
import com.dwarfeng.scheduler.io.ProjectHelper;
import com.dwarfeng.scheduler.typedef.desint.AbstractEditor;

/**
 * 笔记类。
 * @author DwArFeng
 * @since 1.8
 */
public class RTFNote extends Note{

	private static final long serialVersionUID = 6949891040861112230L;

	public RTFNote(StyledTextAttachment attachment){
		this(attachment,null,null,null);
	}
	/**
	 * 生成一个新的笔记本。
	 * @param name 指定的名字。
	 * @param describe 指定的描述。
	 * @param tagIdList 指定的Tag列表。
	 * @param scpath 指定的文档存储路径。
	 */
	public RTFNote(StyledTextAttachment attachment,String name,String describe,List<Integer> tagIdList){
		super(attachment,name,describe,tagIdList);
	}
	/**
	 * 生成一个新的笔记本。
	 * @param name 指定的名字。
	 * @param describe 指定的描述。
	 * @param tagIdList 指定的Tag列表。
	 * @param scpath 指定的文档存储路径。
	 */
	public RTFNote(StyledTextAttachment attachment,boolean lineWarp,String name,String describe,List<Integer> tagIdList){
		super(attachment,lineWarp,name,describe,tagIdList);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.project.Note#getTextAttachment()
	 */
	@Override
	public StyledTextAttachment getTextAttachment(){
		return (StyledTextAttachment) super.getTextAttachment();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.project.Note#getDocument()
	 */
	@Override
	public StyledDocument getDocument() {
		return (StyledDocument) super.getDocument();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.project.Note#createEditor()
	 */
	@Override
	protected AbstractEditor createEditor() throws Exception {
		return new RTFNoteEditor(this);
	}
	
	/**
	 * 返回笔记本中的文档使用的编辑包。
	 * @return
	 */
	public StyledEditorKit getEditorKit(){
		return getTextAttachment().getEditorKit();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.project.Note#createDefaultTextAttachment()
	 */
	@Override
	protected StyledTextAttachment createDefaultTextAttachment() {
		return new StyledTextAttachment(ProjectHelper.genSchedulerURL(getRootProject(), "docs"+File.separator, ".rtf"));
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.ObjectInProjectTree#renderLabel(javax.swing.JLabel)
	 */
	@Override
	public void renderLabel(JLabel label) {
		label.setIconTextGap(8);		
		label.setIcon(new ImageIcon(Scheduler.class.getResource("/resource/tree/rtfNote.png")));
		label.setFont(new Font("SansSerif", Font.PLAIN, 12));
		label.setText((String) (getParam(Note.NAME)));
	}
	
}
