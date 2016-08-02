package com.dwarfeng.scheduler.module.project;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.EditorKit;

import com.dwarfeng.scheduler.module.Scpath;
import com.dwarfeng.scheduler.module.project.abstruct.AbstractAttachment;

/**
 * 文本附件。
 * <p> 该类为文本附件提供了最大化的方法实现。
 * @author DwArFeng
 * @since 1.8
 */
abstract class PTextAttachment<T extends Document,U extends EditorKit> extends PAbstractAttachment<T> {

	/**使用的编辑包*/
	protected U kit;
	
	/**
	 * 生成一个具有指定工程路径，指定文本编辑包的文本附件。
	 * @param scpath 指定的工程路径。
	 * @param kit 指定的文本包。
	 */
	public PTextAttachment(Scpath scpath,U kit) {
		super(scpath);
		if(kit == null) throw new NullPointerException("EditorKit can't be null");
		this.kit = kit;
	}
	
	/**
	 * 返回该对象的编辑包。
	 * @return 该对象的编辑包。
	 */
	public U getEditorKit(){
		return kit;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.Attachment#createDefaultObject()
	 */
	@Override
	public abstract T createDefaultObject();
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.AbstractAttachment#loadAttachment(java.io.InputStream)
	 */
	@Override
	protected T loadAttachment(InputStream in) throws IOException, BadLocationException{
		T document = createDefaultObject();
		getEditorKit().read(in, document,0);
		return document;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.AbstractAttachment#saveAttachment(java.io.OutputStream)
	 */
	@Override
	protected void saveAttachment(OutputStream out,T obj) throws IOException, BadLocationException{
		getEditorKit().write(out, obj, 0, obj.getLength());
	}

}
