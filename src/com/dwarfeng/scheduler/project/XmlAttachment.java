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
 * XML附件类。
 * <p> 该类可以与工程中的一个XML附件相关联，并且封装有XML的多种方法
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
			//读取语句
			CT.trace("正在读取文件 ：" + getScpath().getPathName() + "\t线程描述：" + Thread.currentThread());
	
			//初始化输入流
			try{
				in = ProjectHelper.getInputStream(getRootProject(), getScpath());
				reader = new SAXReader();
			}catch(Exception e){
				e.printStackTrace();
				CT.trace("由于某些原因，无法成功的构建工作路径输入流");
				throw e;
			}
			
			//读取文件。
			try{
				setAttachObject(reader.read(in));
			}catch(Exception e){
				e.printStackTrace();
				CT.trace("由于某些原因，无法读取文件");
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
					CT.trace("由于某些原因，文件输出流无法关闭");
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
			//输出方法
			CT.trace("正在存入文件：" + scpath.getPathName() + "\t线程描述：" + Thread.currentThread());
	
			try {
				out = ProjectHelper.getOutputStream(getRootProject(), scpath);
			} catch (Exception e) {
				e.printStackTrace();
				CT.trace("由于某些原因，无法成功构建工作文件输出流");
				throw e;
			}
			
			//写出文件
			try {
				getAttachObject().setXMLEncoding("UTF-8"); //XXX 可以考虑将其在DwArFeng_Func工具包中封装成枚举。
				OutputFormat format = OutputFormat.createPrettyPrint();
				XMLWriter writer = new XMLWriter(out, format);
				writer.write(getAttachObject());
			} catch (Exception e) {
				e.printStackTrace();
				CT.trace("由于某些原因，文件写入失败");
				throw e;
			}
			CT.trace("文件存入完毕");
			
		}finally{
			if(out != null){
				try{
					out.close();
				}catch(IOException e){
					e.printStackTrace();
					CT.trace("由于某些原因，文件输出流无法关闭");
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
