package com.dwarfeng.scheduler.project;

import java.io.File;
import java.util.List;

import javax.swing.text.StyledDocument;
import javax.swing.text.StyledEditorKit;

import com.dwarfeng.scheduler.gui.RTFNoteEditor;
import com.dwarfeng.scheduler.io.ProjectHelper;
import com.dwarfeng.scheduler.typedef.desint.AbstractEditor;

/**
 * �ʼ��ࡣ
 * @author DwArFeng
 * @since 1.8
 */
public class RTFNote extends Note{

	private static final long serialVersionUID = -7224771850819327044L;
	
	public RTFNote(StyledTextAttachment attachment){
		this(attachment,null,null,null);
	}
	/**
	 * ����һ���µıʼǱ���
	 * @param name ָ�������֡�
	 * @param describe ָ����������
	 * @param tagIdList ָ����Tag�б�
	 * @param scpath ָ�����ĵ��洢·����
	 */
	public RTFNote(StyledTextAttachment attachment,String name,String describe,List<Integer> tagIdList){
		super(attachment,name,describe,tagIdList);
	}
	/**
	 * ����һ���µıʼǱ���
	 * @param name ָ�������֡�
	 * @param describe ָ����������
	 * @param tagIdList ָ����Tag�б�
	 * @param scpath ָ�����ĵ��洢·����
	 */
	public RTFNote(StyledTextAttachment attachment,boolean lineWarp,String name,String describe,List<Integer> tagIdList){
		super(attachment,lineWarp,name,describe,tagIdList);
	}
	
	@Override
	public StyledTextAttachment getTextAttachment(){
		return (StyledTextAttachment) super.getTextAttachment();
	}
	
	@Override
	public StyledDocument getDocument() {
		return (StyledDocument) super.getDocument();
	}
	
	@Override
	protected AbstractEditor createEditor() throws Exception {
		return new RTFNoteEditor(this);
	}
	
	/**
	 * ���رʼǱ��е��ĵ�ʹ�õı༭����
	 * @return
	 */
	public StyledEditorKit getEditorKit(){
		return getTextAttachment().getEditorKit();
	}
	@Override
	protected StyledTextAttachment createDefaultTextAttachment() {
		return new StyledTextAttachment(ProjectHelper.genSchedulerURL(getRootProject(), "docs"+File.separator, ".rtf"));
	}
	
}
