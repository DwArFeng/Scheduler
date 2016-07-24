package com.dwarfeng.scheduler.module;

import javax.swing.text.DefaultEditorKit;
import javax.swing.text.PlainDocument;

/**
 * ƽ���ı�������
 * @author DwArFeng
 * @since 1.8
 */
class PlainTextAttachment extends PTextAttachment<PlainDocument,DefaultEditorKit>{

	/**
	 * ����ƽ���ı�������
	 * @param scpath ������·����
	 */
	public PlainTextAttachment(Scpath scpath) {
		super(scpath,new DefaultEditorKit());
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
