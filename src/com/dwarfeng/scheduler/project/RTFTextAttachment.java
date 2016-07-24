package com.dwarfeng.scheduler.project;

import javax.swing.text.StyledDocument;
import javax.swing.text.rtf.RTFEditorKit;

import com.dwarfeng.scheduler.core.module.Scpath;

/**
 * XXX 如果之后额外添加了HTML文本的话，该类变为一个抽象类。
 * 富格式文本附件。
 * @author DwArFeng
 * @since 1.8
 */
public class RTFTextAttachment extends TextAttachment<StyledDocument,RTFEditorKit>{

	/**
	 * 生成一个富文本附件。
	 * @param scpath 指定的工程路径。
	 */
	public RTFTextAttachment(Scpath scpath) {
		super(scpath,new RTFEditorKit());
	}
	
	@Override
	public StyledDocument createDefaultObject() {
		return (StyledDocument) getEditorKit().createDefaultDocument();
	}

}
