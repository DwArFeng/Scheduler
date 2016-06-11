package com.dwarfeng.scheduler.project;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.text.Document;
import javax.swing.text.EditorKit;

import com.dwarfeng.func.io.CT;
import com.dwarfeng.scheduler.io.ProjectHelper;
import com.dwarfeng.scheduler.io.Scpath;
import com.dwarfeng.scheduler.typedef.abstruct.AbstractAttachment;

/**
 * �ı�������
 * <p> ����Ϊ�ı������ṩ����󻯵ķ���ʵ�֡�
 * @author DwArFeng
 * @since 1.8
 */
public abstract class TextAttachment extends AbstractAttachment {

	/**ʹ�õı༭��*/
	protected EditorKit kit;
	
	/**
	 * ����һ������ָ������·����ָ���ı��༭�����ı�������
	 * @param scpath ָ���Ĺ���·����
	 * @param kit ָ�����ı�����
	 */
	public TextAttachment(Scpath scpath,EditorKit kit) {
		super(scpath);
		if(kit == null) throw new NullPointerException("EditorKit can't be null");
		this.kit = kit;
	}
	
	/**
	 * ���ظö���ı༭����
	 * @return �ö���ı༭����
	 */
	public EditorKit getEditorKit(){
		return kit;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.Attachment#load()
	 */
	@Override
	public void load() throws Exception {
		
		InputStream in = null;
		
		try{
			//��ȡ���
			CT.trace("���ڶ�ȡ�ļ� ��" + getScpath().getPathName() + "\t�߳�������" + Thread.currentThread());
			
			//��ʼ����������
			try {
				in = ProjectHelper.getInputStream(getRootProject(), getScpath());
			} catch (Exception e) {
				e.printStackTrace();
				CT.trace("����ĳЩԭ���޷��ɹ��Ĺ�������·��������");
				throw e;
			}
			
			//��ȡ�ļ���
			try {
				Document document = createDefaultObject();
				getEditorKit().read(in, document, 0);
				setAttachObject(document);
			} catch (Exception e) {
				e.printStackTrace();
				CT.trace("����ĳЩԭ���޷���ȡ�ļ�");
				throw e;
			}
			
			CT.trace("�ļ���ȡ���");
			
		}catch(Exception e){
			setAttachObject(createDefaultObject());
			throw e;
		}finally{
			if(in != null){
				try{
					in.close();
				}catch(IOException e){
					e.printStackTrace();
					CT.trace("����ĳЩԭ���ļ�������޷��ر�");
				}
			}
		}		
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.Attachment#save()
	 */
	@Override
	public void save() throws Exception {
		
		OutputStream wout = null;
		
		try{
			//�������
			CT.trace("���ڴ����ļ���" + scpath.getPathName() + "\t�߳�������" + Thread.currentThread());
			
			//��ʼ�������
			try {
				wout = ProjectHelper.getOutputStream(getRootProject(), scpath);
			} catch (Exception e) {
				e.printStackTrace();
				CT.trace("����ĳЩԭ���޷��ɹ����������ļ������");
				throw e;
			}
			
			//д���ļ�
			try {
				getEditorKit().write(wout, getAttachObject(), 0, getAttachObject().getLength());
			} catch (Exception e) {
				e.printStackTrace();
				CT.trace("����ĳЩԭ���ļ�д��ʧ��");
				throw e;
			}
			CT.trace("�ļ��������");
			
		}finally{
			if(wout != null){
				try{
					wout.close();
				}catch(IOException e){
					e.printStackTrace();
					CT.trace("����ĳЩԭ���ļ�������޷��ر�");
				}
			}
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.AbstractAttachment#getAttachObject()
	 */
	@Override
	public Document getAttachObject() {
		return (Document) super.getAttachObject();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.Attachment#createDefaultObject()
	 */
	@Override
	public abstract Document createDefaultObject();

}
