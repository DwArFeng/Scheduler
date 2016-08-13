package com.dwarfeng.scheduler.project;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.EditorKit;

import com.dwarfeng.scheduler.io.Scpath;
import com.dwarfeng.scheduler.typedef.abstruct.AbstractAttachment;

/**
 * �ı�������
 * <p> ����Ϊ�ı������ṩ����󻯵ķ���ʵ�֡�
 * @author DwArFeng
 * @since 1.8
 */
public abstract class TextAttachment<T extends Document,U extends EditorKit> extends AbstractAttachment<T> {

	/**ʹ�õı༭��*/
	protected U kit;
	
	/**
	 * ����һ������ָ������·����ָ���ı��༭�����ı�������
	 * @param scpath ָ���Ĺ���·����
	 * @param kit ָ�����ı�����
	 */
	public TextAttachment(Scpath scpath,U kit) {
		super(scpath);
		if(kit == null) throw new NullPointerException("EditorKit can't be null");
		this.kit = kit;
	}
	
	/**
	 * ���ظö���ı༭����
	 * @return �ö���ı༭����
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