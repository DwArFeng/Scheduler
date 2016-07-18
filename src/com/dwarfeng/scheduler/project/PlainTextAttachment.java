package com.dwarfeng.scheduler.project;

import javax.swing.text.DefaultEditorKit;
import javax.swing.text.PlainDocument;

import com.dwarfeng.scheduler.io.Scpath;

/**
 * ƽ���ı�������
 * @author DwArFeng
 * @since 1.8
 */
public class PlainTextAttachment extends TextAttachment{

	/**
	 * ����ƽ���ı�������
	 * @param scpath ������·����
	 */
	public PlainTextAttachment(Scpath scpath) {
		super(scpath,new DefaultEditorKit());
	}

	@Override
	public PlainDocument getAttachObject() {
		return (PlainDocument) super.getAttachObject();
	}
	
	@Override
	protected boolean checkTarget(Object target) {
		return target instanceof PlainDocument;
	}

	@Override
	public DefaultEditorKit getEditorKit(){
		return (DefaultEditorKit) super.getEditorKit();
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.Attachment#createDefaultObject()
	 */
	@Override
	public PlainDocument createDefaultObject() {
		return (PlainDocument) getEditorKit().createDefaultDocument();
	}
}
