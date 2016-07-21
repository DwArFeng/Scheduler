package com.dwarfeng.scheduler.project;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.dwarfeng.scheduler.io.Scpath;
import com.dwarfeng.scheduler.typedef.abstruct.AbstractAttachment;

/**
 * XML附件类。
 * <p> 该类可以与工程中的一个XML附件相关联，并且封装有XML的多种方法
 * @author DwArFeng
 * @since 1.8
 */
public final class XmlAttachment extends AbstractAttachment<Document> {
	
	public XmlAttachment(Scpath scpath) {
		super(scpath);
	}


	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.Attachment#createDefaultObject()
	 */
	@Override
	public Document createDefaultObject() {
		Document document =  DocumentHelper.createDocument();
		document.setXMLEncoding("UTF-8");
		Element element = DocumentHelper.createElement("default");
		document.setRootElement(element);
		return document;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.AbstractAttachment#loadAttachment(java.io.InputStream)
	 */
	@Override
	protected Document loadAttachment(InputStream in) throws DocumentException{
		SAXReader reader = new SAXReader();
		return reader.read(in);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.AbstractAttachment#saveAttachment(java.io.OutputStream)
	 */
	@Override
	protected void saveAttachment(OutputStream out,Document obj) throws IOException{
		obj.setXMLEncoding("UTF-8"); //XXX 可以考虑将其在DwArFeng_Func工具包中封装成枚举。
		OutputFormat format = OutputFormat.createPrettyPrint();
		XMLWriter writer = new XMLWriter(out, format);
		writer.write(obj);
	}
}
