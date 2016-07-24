package com.dwarfeng.scheduler.project;

import javax.swing.text.DefaultEditorKit;
import javax.swing.text.PlainDocument;

import com.dwarfeng.scheduler.core.module.Scpath;

/**
 * 平面文本附件。
 * @author DwArFeng
 * @since 1.8
 */
public class PlainTextAttachment extends TextAttachment<PlainDocument,DefaultEditorKit>{

	/**
	 * 生成平面文本附件。
	 * @param scpath 附件的路径。
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
