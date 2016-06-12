package com.dwarfeng.scheduler.project;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.dwarfeng.func.io.CT;
import com.dwarfeng.scheduler.io.ProjectHelper;
import com.dwarfeng.scheduler.io.Scpath;
import com.dwarfeng.scheduler.typedef.abstruct.AbstractAttachment;

/**
 * XML�����ࡣ
 * <p> ��������빤���е�һ��XML��������������ҷ�װ��XML�Ķ��ַ���
 * @author DwArFeng
 * @since 1.8
 */
public final class XmlAttachment extends AbstractAttachment {
	
	public XmlAttachment(Scpath scpath) {
		super(scpath);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.Attachment#load()
	 */
	@Override
	public void load() throws Exception {
		
		InputStream in = null;
		SAXReader reader = null;
		
		try{
			//��ȡ���
			CT.trace("���ڶ�ȡ�ļ� ��" + getScpath().getPathName() + "\t�߳�������" + Thread.currentThread());
	
			//��ʼ��������
			try{
				in = ProjectHelper.getInputStream(getRootProject(), getScpath());
				reader = new SAXReader();
			}catch(Exception e){
				e.printStackTrace();
				CT.trace("����ĳЩԭ���޷��ɹ��Ĺ�������·��������");
				throw e;
			}
			
			//��ȡ�ļ���
			try{
				setAttachObject(reader.read(in));
			}catch(Exception e){
				e.printStackTrace();
				CT.trace("����ĳЩԭ���޷���ȡ�ļ�");
				throw e;
			}
			
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
		
		OutputStream out = null;
		
		try{
			//�������
			CT.trace("���ڴ����ļ���" + scpath.getPathName() + "\t�߳�������" + Thread.currentThread());
	
			try {
				out = ProjectHelper.getOutputStream(getRootProject(), scpath);
			} catch (Exception e) {
				e.printStackTrace();
				CT.trace("����ĳЩԭ���޷��ɹ����������ļ������");
				throw e;
			}
			
			//д���ļ�
			try {
				getAttachObject().setXMLEncoding("UTF-8"); //XXX ���Կ��ǽ�����DwArFeng_Func���߰��з�װ��ö�١�
				OutputFormat format = OutputFormat.createPrettyPrint();
				XMLWriter writer = new XMLWriter(out, format);
				writer.write(getAttachObject());
			} catch (Exception e) {
				e.printStackTrace();
				CT.trace("����ĳЩԭ���ļ�д��ʧ��");
				throw e;
			}
			CT.trace("�ļ��������");
			
		}finally{
			if(out != null){
				try{
					out.close();
				}catch(IOException e){
					e.printStackTrace();
					CT.trace("����ĳЩԭ���ļ�������޷��ر�");
				}
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.Attachment#createDefaultObject()
	 */
	@Override
	public Document createDefaultObject() {
		Document document =  DocumentHelper.createDocument();
		document.setXMLEncoding("UTF-8");
		return document;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.AbstractAttachment#checkTarget(java.lang.Object)
	 */
	@Override
	protected boolean checkTarget(Object target) {
		return target instanceof Document;
	}

	@Override
	public Document getAttachObject() {
		return (Document) super.getAttachObject();
	}
}
