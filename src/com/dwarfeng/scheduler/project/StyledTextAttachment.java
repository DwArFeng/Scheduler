package com.dwarfeng.scheduler.project;

import javax.swing.text.StyledDocument;
import javax.swing.text.rtf.RTFEditorKit;

import com.dwarfeng.scheduler.io.Scpath;
import com.dwarfeng.scheduler.typedef.abstruct.Attachment;

/**
 * XXX ���֮����������HTML�ı��Ļ��������Ϊһ�������ࡣ
 * ����ʽ�ı�������
 * @author DwArFeng
 * @since 1.8
 */
public class StyledTextAttachment extends TextAttachment implements Attachment{

	/**
	 * 
	 * @param scpath
	 */
	public StyledTextAttachment(Scpath scpath) {
		super(scpath,new RTFEditorKit());
	}
	
	@Override
	public StyledDocument getAttachObject() {
		return (StyledDocument) super.getAttachObject();
	}

	@Override
	protected boolean checkTarget(Object target) {
		return target instanceof StyledDocument;
	}
	
	@Override
	public RTFEditorKit getEditorKit(){
		return (RTFEditorKit) super.getEditorKit();
	}

	@Override
	public StyledDocument createDefaultObject() {
		return (StyledDocument) getEditorKit().createDefaultDocument();
	}

}
