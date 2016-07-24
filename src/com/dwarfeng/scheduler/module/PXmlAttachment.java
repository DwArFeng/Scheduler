package com.dwarfeng.scheduler.module;

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

/**
 * XML�����ࡣ
 * <p> ��������빤���е�һ��XML��������������ҷ�װ��XML�Ķ��ַ���
 * @author DwArFeng
 * @since 1.8
 */
final class PXmlAttachment extends PAbstractAttachment<Document> {
	
	public PXmlAttachment(Scpath scpath) {
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
		obj.setXMLEncoding("UTF-8"); //XXX ���Կ��ǽ�����DwArFeng_Func���߰��з�װ��ö�١�
		OutputFormat format = OutputFormat.createPrettyPrint();
		XMLWriter writer = new XMLWriter(out, format);
		writer.write(obj);
	}
}
