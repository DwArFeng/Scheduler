package com.dwarfeng.scheduler.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.dwarfeng.func.io.CT;
import com.dwarfeng.func.io.IOFunc;
import com.dwarfeng.func.util.IDMap;
import com.dwarfeng.scheduler.project.ImprovisedPlant;
import com.dwarfeng.scheduler.project.ImprovisedPlantCol;
import com.dwarfeng.scheduler.project.Note;
import com.dwarfeng.scheduler.project.Notebook;
import com.dwarfeng.scheduler.project.NotebookCol;
import com.dwarfeng.scheduler.project.PlainNote;
import com.dwarfeng.scheduler.project.PlainTextAttachment;
import com.dwarfeng.scheduler.project.Project;
import com.dwarfeng.scheduler.project.RTFNote;
import com.dwarfeng.scheduler.project.RTFTextAttachment;
import com.dwarfeng.scheduler.project.Tag;
import com.dwarfeng.scheduler.project.TagMap;
import com.dwarfeng.scheduler.typedef.exception.UnstructFailedException;
import com.dwarfeng.scheduler.typedef.funcint.SerialParam;


/**
 * ������һЩ��{@linkplain W2SF} ʹ�õĹ���������
 * <p> �ýӿ���Compatible Framework�е�һԱ����Ҫ����ͬ�汾�����ڶ�ȡʱ�ļ��������⡣
 * <p> ��ʹ�ǲ�ͬ�汾�Ķ�ȡ����Ҳ�����д�������ͬ���룬��Щ����Ӧ�ñ��洢��������С�
 * <br> ԭ���ϣ���ȡ���ķ���Ӧ�þ����ܵ�ϸ����Ȼ��ж�������֮�У�������Ҫ��������Ķ�ȡ���е����з����������Ը��ࡣ
 * <br> ���򣺷��������� + ����ʹ����������İ汾.replace('.','_'); ����Ҫ��ע������ȷָ��ʲô����������õ����а汾��
 * @author DwArFeng
 * @since 1.8
 */
final class W2SFCommonFunc{
	
	/*
	 * 
	 * ���·�����ͨ�÷��������������ļ����á�
	 * 
	 * 
	 * 
	 * 
	 */
	
	/**
	 * ���汾��Ϣд�빤�̴浵��
	 * <p> �÷�������汾ͨ�ã����������ڵ�0.0.0�汾δʹ��֮�⡣
	 * @param version ָ���İ汾��
	 * @throws IOException  IOͨ���쳣��
	 */
	static void saveVersionInfo(ZipOutputStream zout,String version) throws IOException{
		Element element = DocumentHelper.createElement("version");
		element.addAttribute("value", version);
		Document document = DocumentHelper.createDocument(element);
		zout.putNextEntry(new ZipEntry("version.xml"));
		XMLWriter writer = new XMLWriter(zout, OutputFormat.createPrettyPrint());
		writer.write(document);
	}
	
	/*
	 * ���·�����ֱ��ʵ�� W2FS �ӿڵķ���
	 * 
	 * �Ǳ���ʵ����ֱ�ӵ��õķ�����
	 * 
	 * 
	 * 
	 * 
	 */
	
	/**
	 * ��ȡ�ļ��ṹ�������ļ��ķ�����
	 * <p> ���ð汾��0.0.0
	 * @param file ָ����ѹ���ļ���
	 * @return ���ɵĹ��̡�
	 * @throws DocumentException XML�ṹ�쳣��
	 * @throws IOException  IOͨ���쳣��
	 */
	static Project loadStruct0_0_0(ZipFile file) throws IOException, UnstructFailedException{
		//�����������Ͷ�ȡ��
		InputStream tagIn = file.getInputStream(file.getEntry("tags.xml"));
		SAXReader tagReader = new SAXReader();
		InputStream notebooksIn = file.getInputStream(file.getEntry("notebooks.xml"));
		SAXReader notebooksReader = new SAXReader();
		//����Ԫ�ر���
		Element tagElement = null;
		Element notebooksElement = null;
		
		//���Խ�������Ԫ��
		Set<UnstructFailedException.FailedType> fs = new HashSet<UnstructFailedException.FailedType>();
		
		try {
			tagElement = (Element) tagReader.read(tagIn).getRootElement();
		} catch (DocumentException e) {
			fs.add(UnstructFailedException.FailedType.TAGS);
		}
		try {
			notebooksElement = notebooksReader.read(notebooksIn).getRootElement();
		} catch (DocumentException e) {
			fs.add(UnstructFailedException.FailedType.NOTEBOOKS);
		}
		
		if(fs.size() > 0) throw new UnstructFailedException(fs);
		
		//�����ļ�
		TagMap tagMap = unconstructTagsXML0_0_0(tagElement);
		NotebookCol notebookCol = unconstructNotebooks0_0_0(notebooksElement);
		return new Project.Productor().tagMap(tagMap).notebookCol(notebookCol).product();
	}
	
	/**
	 * ��ȡ�ļ��ṹ�������ļ��ķ�����
	 * <p> ���ð汾��0.1.0
	 * @param file ָ����ѹ���ļ���
	 * @return ���ɵĹ��̡�
	 * @throws DocumentException XML�ṹ�쳣��
	 * @throws IOException  IOͨ���쳣��
	 */
	static Project loadStruct0_1_0(ZipFile file) throws IOException,UnstructFailedException{
		//�����������Ͷ�ȡ��
		InputStream tagIn = file.getInputStream(file.getEntry("tags.xml"));
		SAXReader tagReader = new SAXReader();
		InputStream notebooksIn = file.getInputStream(file.getEntry("notebooks.xml"));
		SAXReader notebooksReader = new SAXReader();
		//����Ԫ�ر���
		Element tagElement = null;
		Element notebooksElement = null;
		
		//���Խ�������Ԫ��
		Set<UnstructFailedException.FailedType> fs = new HashSet<UnstructFailedException.FailedType>();
		
		try {
			tagElement = (Element) tagReader.read(tagIn).getRootElement();
		} catch (DocumentException e) {
			fs.add(UnstructFailedException.FailedType.TAGS);
		}
		try {
			notebooksElement = notebooksReader.read(notebooksIn).getRootElement();
		} catch (DocumentException e) {
			fs.add(UnstructFailedException.FailedType.NOTEBOOKS);
		}
		
		if(fs.size() > 0) throw new UnstructFailedException(fs);
		
		//�����ļ�
		TagMap tagMap = unconstructTagsXML0_0_0(tagElement);
		NotebookCol notebookCol = unconstructNotebooks0_1_0(notebooksElement);
		return new Project.Productor().tagMap(tagMap).notebookCol(notebookCol).product();
	}
	
	/**
	 * ��ȡ�ļ��ṹ�������ļ��ķ�����
	 * <p> ���ð汾��0.2.0
	 * @param file ָ����ѹ���ļ���
	 * @return ���ɵĹ��̡�
	 * @throws DocumentException XML�ṹ�쳣��
	 * @throws IOException  IOͨ���쳣��
	 */
	static Project loadStruct0_2_0(ZipFile file) throws IOException,UnstructFailedException{
		//�����������Ͷ�ȡ��
		InputStream tagIn = file.getInputStream(file.getEntry("tags.xml"));
		SAXReader tagReader = new SAXReader();
		InputStream notebooksIn = file.getInputStream(file.getEntry("notebooks.xml"));
		SAXReader notebooksReader = new SAXReader();
		InputStream implantsIn = file.getInputStream(file.getEntry("implants.xml"));
		SAXReader implantsReader = new SAXReader();
		//����Ԫ�ر���
		Element tagElement = null;
		Element notebooksElement = null;
		Element implantsElement = null;
		
		//���Խ�������Ԫ��
		Set<UnstructFailedException.FailedType> fs = new HashSet<UnstructFailedException.FailedType>();
		
		try {
			tagElement = (Element) tagReader.read(tagIn).getRootElement();
		} catch (DocumentException e) {
			fs.add(UnstructFailedException.FailedType.TAGS);
		}
		try {
			notebooksElement = notebooksReader.read(notebooksIn).getRootElement();
		} catch (DocumentException e) {
			fs.add(UnstructFailedException.FailedType.NOTEBOOKS);
		}
		try{
			implantsElement = implantsReader.read(implantsIn).getRootElement();
		}catch(DocumentException e){
			fs.add(UnstructFailedException.FailedType.IMPLANTS);
		}
		
		if(fs.size() > 0) throw new UnstructFailedException(fs);
		
		//�����ļ�
		TagMap tagMap = unconstructTagsXML0_0_0(tagElement);
		NotebookCol notebookCol = unconstructNotebooks0_2_0(notebooksElement);
		ImprovisedPlantCol improvisedPlantCol = unconstructImprovisedPlantsXML0_2_0(implantsElement);
		
		return new Project.Productor()
				.tagMap(tagMap)
				.notebookCol(notebookCol)
				.improvisedPlain(improvisedPlantCol)
				.product();
	}

	/**
	 * ��ָ��Zip�������������ļ��Ľṹ��Ϣ��
	 * <p> ���õİ汾��0.0.0
	 * @param project ָ���Ĺ����ļ���
	 * @param zout ָ�����������
	 * @throws IOException ͨ���쳣��
	 */
	static void saveStruct0_0_0(Project project, ZipOutputStream zout) throws IOException {
		//д��汾����
		saveVersionInfo(zout, "0.0.0");
		//����XML��Ϣ
		Document tagsXML = DocumentHelper.createDocument(constructTagsXML0_0_0(project.getTagMap()));
		Document notesbooksXML = null;
		try {
			notesbooksXML = DocumentHelper.createDocument(constructNotebooksXML0_0_0(
					project.getChildFromType(NotebookCol.class)
			));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tagsXML.setXMLEncoding("UTF-8");
		notesbooksXML.setXMLEncoding("UTF-8");
		OutputFormat format = OutputFormat.createPrettyPrint();
		
		//��XML�ļ�д��ѹ���ļ��������
		zout.putNextEntry(new ZipEntry("tags.xml"));
		XMLWriter writer = new XMLWriter(zout, format);
		writer.write(tagsXML);
		zout.putNextEntry(new ZipEntry("notebooks.xml"));
		writer.write(notesbooksXML);
	}
	
	/**
	 * ��ָ��Zip�������������ļ��Ľṹ��Ϣ��
	 * <p> ���õİ汾��0.1.0
	 * @param project ָ���Ĺ����ļ���
	 * @param zout ָ�����������
	 * @throws IOException ͨ���쳣��
	 */
	static void saveStruct0_1_0(Project project, ZipOutputStream zout) throws IOException {
		//д��汾����
		saveVersionInfo(zout, "0.1.0");
		//����XML��Ϣ
		Document tagsXML = DocumentHelper.createDocument(constructTagsXML0_0_0(project.getTagMap()));
		Document notesbooksXML = null;
		try {
			notesbooksXML = DocumentHelper.createDocument(constructNotebooksXML0_1_0(
					project.getChildFromType(NotebookCol.class)
			));
		} catch (ClassNotFoundException e) {
			//��Ϊ�����к���NotebookCol�����Բ�������쳣��
		}
		tagsXML.setXMLEncoding("UTF-8");
		notesbooksXML.setXMLEncoding("UTF-8");
		OutputFormat format = OutputFormat.createPrettyPrint();
		
		//��XML�ļ�д��ѹ���ļ��������
		zout.putNextEntry(new ZipEntry("tags.xml"));
		XMLWriter writer = new XMLWriter(zout, format);
		writer.write(tagsXML);
		zout.putNextEntry(new ZipEntry("notebooks.xml"));
		writer.write(notesbooksXML);
	}
	
	/**
	 * ��ָ��Zip�������������ļ��Ľṹ��Ϣ��
	 * <p> ���õİ汾��0.2.0
	 * @param project ָ���Ĺ����ļ���
	 * @param zout ָ�����������
	 * @throws IOException ͨ���쳣��
	 */
	static void saveStruct0_2_0(Project project, ZipOutputStream zout) throws IOException {
		//д��汾����
		saveVersionInfo(zout, "0.2.0");
		//����XML��Ϣ
		Document tagsXML = DocumentHelper.createDocument(constructTagsXML0_0_0(project.getTagMap()));
		Document notesbooksXML = null;
		Document implantsXML = null;
		try {
			notesbooksXML = DocumentHelper.createDocument(constructNotebooksXML0_2_0(
					project.getChildFromType(NotebookCol.class)
			));
			implantsXML = DocumentHelper.createDocument(constructImprovisedPlantsXML0_2_0(
					project.getChildFromType(ImprovisedPlantCol.class)));
			
		} catch (ClassNotFoundException e) {
			//��Ϊ�����к���NotebookCol�����Բ�������쳣��
		}
		tagsXML.setXMLEncoding("UTF-8");
		notesbooksXML.setXMLEncoding("UTF-8");
		implantsXML.setXMLEncoding("UTF-8");
		OutputFormat format = OutputFormat.createPrettyPrint();
		
		//��XML�ļ�д��ѹ���ļ��������
		XMLWriter writer = new XMLWriter(zout, format);
		
		zout.putNextEntry(new ZipEntry("tags.xml"));
		writer.write(tagsXML);
		zout.putNextEntry(new ZipEntry("notebooks.xml"));
		writer.write(notesbooksXML);
		zout.putNextEntry(new ZipEntry("implants.xml"));
		writer.write(implantsXML);
	}

	/**
	 * ��ָ���Ĺ���·���н�ѹ�ļ���
	 * <p> ���ð汾��0.0.0��0.1.0 , 0.2.0
	 * @param project ������Ĺ��̡�
	 * @param file �������Zip�ļ���
	 * @param scpath ָ���Ĺ���·����
	 * @throws ZipException Zip�ļ��쳣��
	 * @throws IOException IOͨ���쳣��
	 */
	static void unzipFile0_0_0(Project project, ZipFile file, Scpath scpath) throws IOException {
		
		OutputStream wout = null;
		try{
			//���ɹ���·���������
			wout = ProjectIoHelper.getOutputStreamWC(project, scpath,true);
			
			//����Zip��������
			Scpath thisVersionScpath = getScpathThisVersion0_0_0(scpath);
			String str = thisVersionScpath.getPathName();
			str = str.replace(File.separatorChar, '/');
			InputStream zin = file.getInputStream(file.getEntry(str));
			
			//�����������ݴ��ݵ������
			IOFunc.trans(zin, wout, 4096);
			
		}finally{
			if(wout != null){
				try{
					wout.close();
				}catch(Exception e){
					e.printStackTrace();
					CT.trace("����ĳЩԭ�򣬹���·�������û�гɹ��ı��ر�");
				}
			}
		}
		
	}

	/**
	 * ��ָ����ѹ���ļ��������ѹ������·���ļ���
	 * <p> ʹ�õİ汾��0.0.0��0.1.0 , 0.2.0
	 * @param project ��صĹ��̡�
	 * @param zout ָ����zip�������
	 * @param scpath ָ���Ĺ���·����
	 * @throws IOException  ͨ���쳣��
	 */
	static void zipFile0_0_0(Project project, Scpath scpath, ZipOutputStream zout) throws IOException {
		
		InputStream in = null;
		try{
			in = ProjectIoHelper.getInputStreamWC(project, scpath);
			Scpath thisVersionScpath = getScpathThisVersion0_0_0(scpath);
			String str = thisVersionScpath.getPathName();
			str = str.replace(File.separatorChar, '/');
			zout.putNextEntry(new ZipEntry(str));
			IOFunc.trans(in, zout, 4096);
			zout.flush();
			
		}finally{
			if(in != null){
				try{
					in.close();
				}catch(IOException e){
					e.printStackTrace();
					CT.trace("����ĳЩԭ��������û�������ر�");
				}
			}
		}
		
	}
	
	/**
	 * ��ָ����Scpathת��Ϊ���°汾��Scpath��
	 * <p> ���ð汾��0.0.0��0.1.0 , 0.2.0
	 * @param scpath ָ����Scpath��
	 * @return ת��������°汾��Scpath��
	 */
	static Scpath getScpathLastVersion0_0_0(Scpath scpath) {
		return scpath;
	}
	
	/**
	 * �����°汾��Scpathת��Ϊָ����Scpath��
	 * <p>���ð汾��0.0.0 , 0.1.0 , 0.2.0
	 * @param scpath ���°汾��Scpath��
	 * @return ָ���汾��Scpath��
	 */
	static Scpath getScpathThisVersion0_0_0(Scpath scpath) {
		return scpath;
	}
	
	
	
	
	
	
	
	/*
	 * ���·��������Tags�ķ���
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	
	
	/**
	 * ��tagXML�ļ��н���IDMap��Ϣ��
	 * <p> ���õİ汾�� 0.0.0��0.1.0
	 * @param element XML�ı���
	 * @return ������TagIDӳ����Ϣ��
	 */
	static TagMap unconstructTagsXML0_0_0(Element element){
		IDMap<Tag> map = new IDMap<Tag>();
		@SuppressWarnings("unchecked")
		List<Node> tagNodes = element.selectNodes("/tags/tag");
		Iterator<Node> iterator = tagNodes.iterator();
		while(iterator.hasNext()){
			Element tagElement = (Element) iterator.next();
			int id;
			String name;
			String describe;
			id = new Integer(tagElement.attribute("id").getValue());
			Element nameElement = (Element) tagElement.selectNodes("name").get(0);
			name = nameElement.getText();
			Element describeElement = (Element) tagElement.selectNodes("describe").get(0);
			describe = describeElement.getText();
			Tag tag = new Tag(name,describe);
			map.forcePut(id, tag);
		}
		return new TagMap(map);
	 }
	
	/**
	 * ��ID-��ǩӳ����й���Tag��XML��Ϣ��
	 * <p> ���õİ汾��0.0.0��0.1.0
	 * @param tagMap ָ����ID-��ǩӳ���
	 * @return �����XML��Ϣ��
	 */
	static Element constructTagsXML0_0_0(TagMap tagMap){
		int[] ids = tagMap.getTagIds();
		Element tags = DocumentHelper.createElement("tags");
		for(int id:ids){
			Element tagElement = DocumentHelper.createElement("tag");
			Element nameElement = DocumentHelper.createElement("name");
			Element describeElement = DocumentHelper.createElement("describe");
			nameElement.addCDATA(tagMap.getTag(id).getName());
			describeElement.addCDATA(tagMap.getTag(id).getDescribe());
			tagElement.addAttribute("id", ""+id);
			tagElement.add(nameElement);
			tagElement.add(describeElement);
			tags.add(tagElement);
		}
		return tags;
	}
	
	 /*
	  * ���·��������Notebooks�ġ�
	  * 
	  * 
	  * 
	  * 
	  */
	
	/**
	 * ��ָ����XML�ĵ��н����ʼǱ���
	 * <p> ���õİ汾��0.0.0
	 * @param element ָ����XML�ĵ���
	 * @return �������ıʼǱ���
	 */
	static NotebookCol unconstructNotebooks0_0_0(Element element){
		List<Notebook> notebooks = new Vector<Notebook>();
		@SuppressWarnings("unchecked")
		List<Node> notebookNodes = element.selectNodes("/notebooks/notebook");
		Iterator<Node> iterator = notebookNodes.iterator();
		while(iterator.hasNext()){
			Element notebookElement = (Element) iterator.next();
			notebooks.add(unconstructNotebook0_0_0(notebookElement));
		}
		return new NotebookCol.Productor().notebooks(notebooks).product();
	}
	
	/**
	 * ��ָ����XML�ĵ��н����ʼǱ���
	 * <p> ���õİ汾��0.1.0
	 * @param element ָ����XML�ĵ���
	 * @return �������ıʼǱ���
	 */
	static NotebookCol unconstructNotebooks0_1_0(Element element){
		List<Notebook> notebooks = new Vector<Notebook>();
		@SuppressWarnings("unchecked")
		List<Node> notebookNodes = element.selectNodes("/notebooks/notebook");
		Iterator<Node> iterator = notebookNodes.iterator();
		while(iterator.hasNext()){
			Element notebookElement = (Element) iterator.next();
			notebooks.add(unconstructNotebook0_1_0(notebookElement));
		}
		return new NotebookCol.Productor().notebooks(notebooks).product();
	}
	
	/**
	 * ��ָ����XML�ĵ��н����ʼǱ���
	 * <p> ���õİ汾��0.2.0
	 * @param element ָ����XML�ĵ���
	 * @return �������ıʼǱ���
	 */
	static NotebookCol unconstructNotebooks0_2_0(Element element){
		List<Notebook> notebooks = new ArrayList<Notebook>();
		@SuppressWarnings("unchecked")
		List<Node> notebookNodes = element.selectNodes("/notebooks/notebook");
		Iterator<Node> iterator = notebookNodes.iterator();
		while(iterator.hasNext()){
			Element notebookElement = (Element) iterator.next();
			notebooks.add(unconstructNotebook0_2_0(notebookElement));
		}
		return new NotebookCol.Productor().notebooks(notebooks).product();
	}
	
	/**
	 * �ӱʼǱ������й���Notebooks��XML��Ϣ��
	 * <p>���õİ汾��0.0.0
	 * @param notebooks ָ���ıʼǱ����ϡ�
	 * @return �����XML��Ϣ��
	 */
	static Element constructNotebooksXML0_0_0(NotebookCol notebooks){
		Element element = DocumentHelper.createElement("notebooks");
		for(int i = 0 ; i < notebooks.getChildCount() ; i ++){
			Notebook notebook = (Notebook) notebooks.getChildAt(i);
			element.add(constructNotebookXML0_0_0(notebook));
		}
		return element;
	}

	/**
	 * �ӱʼǱ������й���Notebooks��XML��Ϣ��
	 * <p>���õİ汾��0.1.0
	 * @param notebooks ָ���ıʼǱ����ϡ�
	 * @return �����XML��Ϣ��
	 */
	static Element constructNotebooksXML0_1_0(NotebookCol notebooks){
		Element element = DocumentHelper.createElement("notebooks");
		for(int i = 0 ; i < notebooks.getChildCount() ; i ++){
			Notebook notebook = (Notebook) notebooks.getChildAt(i);
			element.add(constructNotebookXML0_1_0(notebook));
		}
		return element;
	}
	
	/**
	 * �ӱʼǱ������й���Notebooks��XML��Ϣ��
	 * <p>���õİ汾��0.2.0
	 * @param notebooks ָ���ıʼǱ����ϡ�
	 * @return �����XML��Ϣ��
	 */
	static Element constructNotebooksXML0_2_0(NotebookCol notebooks){
		Element element = DocumentHelper.createElement("notebooks");
		for(int i = 0 ; i < notebooks.getChildCount() ; i ++){
			Notebook notebook = (Notebook) notebooks.getChildAt(i);
			element.add(constructNotebookXML0_2_0(notebook));
		}
		return element;
	}
	/*
	 * ���·��������Notebook�ġ�
	 * 
	 * 
	 * 
	 */
	
	/**
	 * ��ָ����XMLԪ���н���Notebook����
	 * <p> ���õİ汾��0.0.0
	 * @param element ָ����XMLԪ�ء�
	 * @return ������Notebook����
	 */
	static Notebook unconstructNotebook0_0_0 (Element element){
		String name;
		String describe;
		List<Integer> ids = new Vector<Integer>();
		List<Note<?,?>> notes = new Vector<Note<?,?>>();
		
		
		Element nameElement = (Element) element.selectNodes("name").get(0);
		name = nameElement.getText();
		Element describeElement = (Element) element.selectNodes("describe").get(0);
		describe = describeElement.getText();
		List<?> tagElements = element.selectNodes("tag");
		Iterator<?> iterator = tagElements.iterator();
		while (iterator.hasNext()) {
			Element tagElement = (Element) iterator.next();
			ids.add(new Integer(tagElement.attribute("id").getValue()));
		}
		List<?> noteList = element.selectNodes("note");
		Iterator<?> iterator2 = noteList.iterator();
		while(iterator2.hasNext()){
			Element noteElement = (Element) iterator2.next();
			notes.add(unconstructNote0_0_0(noteElement));
		}
		
		SerialParam sp = new SerialParam.Productor()
				.name(name)
				.describe(describe)
				.tagIds(ids)
				.product();
		return new Notebook.Productor().notes(notes).serialParam(sp).product();
	}
	
	/**
	 * ��ָ����XMLԪ���н���Notebook����
	 * <p> ���õİ汾��0.1.0
	 * @param element ָ����XMLԪ�ء�
	 * @return ������Notebook����
	 */
	static Notebook unconstructNotebook0_1_0 (Element element){
		String name;
		String describe;
		List<Integer> ids = new Vector<Integer>();
		List<Note<?,?>> notes = new Vector<Note<?,?>>();
		
		
		Element nameElement = (Element) element.selectNodes("name").get(0);
		name = nameElement.getText();
		Element describeElement = (Element) element.selectNodes("describe").get(0);
		describe = describeElement.getText();
		List<?> tagElements = element.selectNodes("tag");
		Iterator<?> iterator = tagElements.iterator();
		while (iterator.hasNext()) {
			Element tagElement = (Element) iterator.next();
			ids.add(new Integer(tagElement.attribute("id").getValue()));
		}
		List<?> noteList = element.selectNodes("note");
		Iterator<?> iterator2 = noteList.iterator();
		while(iterator2.hasNext()){
			Element noteElement = (Element) iterator2.next();
			notes.add(unconstructNote0_1_0(noteElement));
		}
		
		SerialParam sp = new SerialParam.Productor()
				.name(name)
				.describe(describe)
				.tagIds(ids)
				.product();
		return new Notebook.Productor().notes(notes).serialParam(sp).product();
	}
	
	/**
	 * ��ָ����XMLԪ���н���Notebook����
	 * <p> ���õİ汾��0.2.0
	 * @param element ָ����XMLԪ�ء�
	 * @return ������Notebook����
	 */
	static Notebook unconstructNotebook0_2_0 (Element element){

		List<Note<?,?>> notes = new ArrayList<Note<?,?>>();
		SerialParam sp = new SerialParam.Productor().product();
		
		Element serialParamElement = (Element) element.selectNodes("serp").get(0);
		sp = unconstructSerialParamXML0_2_0(serialParamElement);
		
		for(Object obj : element.selectNodes("note")){
			Element noteElement = (Element) obj;
			notes.add(unconstructNote0_2_0(noteElement));
		}
		
		return new Notebook.Productor().notes(notes).serialParam(sp).product();
	}

	/**
	 * �ӱʼǱ��й����й�Notebook��XML��Ϣ��
	 * <p> ���õİ汾��0.0.0
	 * @param notebook ָ���ıʼǱ���
	 * @return �����XML��Ϣ��
	 */
	static Element constructNotebookXML0_0_0(Notebook notebook) {
		Element notebookElement = DocumentHelper.createElement("notebook");
		Element notebookNameElement = DocumentHelper.createElement("name");
		Element notebookDescribeElement = DocumentHelper.createElement("describe");
		notebookNameElement.addCDATA(notebook.getSerialParam().getName());
		notebookDescribeElement.addCDATA(notebook.getSerialParam().getDescribe());
		notebookElement.add(notebookNameElement);
		notebookElement.add(notebookDescribeElement);
		for(Integer id:notebook.getSerialParam().getTagIds()){
			Element notebookTagElement = DocumentHelper.createElement("tag");
			notebookTagElement.add(DocumentHelper.createAttribute(notebookTagElement, "id", id.intValue() + ""));
			notebookElement.add(notebookTagElement);
		}
		for(int o = 0 ; o<notebook.getChildCount() ; o ++){
			RTFNote note = (RTFNote) notebook.getChildAt(o);
			notebookElement.add(constructNoteXML0_0_0(note));
		}
		return notebookElement;
	}
	
	/**
	 * �ӱʼǱ��й����й�Notebook��XML��Ϣ��
	 * <p> ���õİ汾��0.1.0
	 * @param notebook ָ���ıʼǱ���
	 * @return �����XML��Ϣ��
	 */
	static Element constructNotebookXML0_1_0(Notebook notebook) {
		Element notebookElement = DocumentHelper.createElement("notebook");
		Element notebookNameElement = DocumentHelper.createElement("name");
		Element notebookDescribeElement = DocumentHelper.createElement("describe");
		notebookNameElement.addCDATA(notebook.getSerialParam().getName());
		notebookDescribeElement.addCDATA(notebook.getSerialParam().getDescribe());
		notebookElement.add(notebookNameElement);
		notebookElement.add(notebookDescribeElement);
		for(Integer id:notebook.getSerialParam().getTagIds()){
			Element notebookTagElement = DocumentHelper.createElement("tag");
			notebookTagElement.add(DocumentHelper.createAttribute(notebookTagElement, "id", id.intValue() + ""));
			notebookElement.add(notebookTagElement);
		}
		for(int o = 0 ; o<notebook.getChildCount() ; o ++){
			Note<?,?> note = (Note<?,?>) notebook.getChildAt(o);
			notebookElement.add(constructNoteXML0_1_0(note));
		}
		return notebookElement;
	}
	
	/**
	 * �ӱʼǱ��й����й�Notebook��XML��Ϣ��
	 * <p> ���õİ汾��0.2.0
	 * @param notebook ָ���ıʼǱ���
	 * @return �����XML��Ϣ��
	 */
	static Element constructNotebookXML0_2_0(Notebook notebook) {
		Element notebookElement = DocumentHelper.createElement("notebook");
		notebookElement.add(constructSerialParamXML0_2_0(notebook.getSerialParam()));
		for(int o = 0 ; o<notebook.getChildCount() ; o ++){
			Note<?,?> note = (Note<?,?>) notebook.getChildAt(o);
			notebookElement.add(constructNoteXML0_2_0(note));
		}
		return notebookElement;
	}
	
	/*
	 * ���·��������note�ġ�
	 * 
	 * 
	 * 
	 */

	/**
	 * ��ָ����XMLԪ���н���Note����
	 * <p> ���õİ汾��0.0.0
	 * @param element ָ����XMLԪ�ء�
	 * @return �����Ķ���
	 */
	static RTFNote unconstructNote0_0_0(Element element){
		String name;
		String describe;
		List<Integer> ids = new Vector<Integer>();
		RTFTextAttachment attachment;
		
		
		Element nameElement = (Element) element.selectNodes("name").get(0);
		name = nameElement.getText();
		Element describeElement = (Element) element.selectNodes("describe").get(0);
		describe = describeElement.getText();
		List<?> tagElements = element.selectNodes("tag");
		Iterator<?> iterator = tagElements.iterator();
		while (iterator.hasNext()) {
			Element tagElement = (Element) iterator.next();
			ids.add(new Integer(tagElement.attribute("id").getValue()));
		}
		Element docElement = (Element) element.selectNodes("doc").get(0);
		attachment = new RTFTextAttachment(getScpathLastVersion0_0_0( new Scpath(docElement.getText())));
		
		SerialParam sp = new SerialParam.Productor()
				.name(name)
				.describe(describe)
				.tagIds(ids)
				.product();
		return new RTFNote.Productor(attachment).serialParam(sp).product();
	}
	
	/**
	 * ��ָ����XMLԪ���н���Note����
	 * <p> ���õİ汾��0.1.0
	 * @param element ָ����XMLԪ�ء�
	 * @return �����Ķ���
	 */
	static Note<?,?> unconstructNote0_1_0(Element element){
		String type;
		String name;
		String describe;
		boolean lineWarp;
		List<Integer> ids = new Vector<Integer>();
		
		Element nameElement = (Element) element.selectNodes("name").get(0);
		name = nameElement.getText();
		Element describeElement = (Element) element.selectNodes("describe").get(0);
		describe = describeElement.getText();
		List<?> tagElements = element.selectNodes("tag");
		Iterator<?> iterator = tagElements.iterator();
		while (iterator.hasNext()) {
			Element tagElement = (Element) iterator.next();
			ids.add(new Integer(tagElement.attribute("id").getValue()));
		}
		Element docElement = (Element) element.selectNodes("att").get(0);
		
		type = element.attribute("type").getValue();
		
		lineWarp = new Boolean(element.attribute("linewarp").getValue()).booleanValue();
		
		SerialParam sp = new SerialParam.Productor()
				.name(name)
				.describe(describe)
				.tagIds(ids)
				.product();
		
		switch (type) {
			case "txt":
				PlainTextAttachment attachment0 = new PlainTextAttachment(getScpathLastVersion0_0_0( new Scpath(docElement.getText())));
				return new PlainNote.Productor(attachment0).lineWrap(lineWarp).serialParam(sp).product();
			default:
				RTFTextAttachment attachment1 = new RTFTextAttachment(getScpathLastVersion0_0_0( new Scpath(docElement.getText())));
				return new RTFNote.Productor(attachment1).lineWrap(lineWarp).serialParam(sp).product();
		}
	}
	
	/**
	 * ��ָ����XMLԪ���н���Note����
	 * <p> ���õİ汾��0.2.0
	 * @param element ָ����XMLԪ�ء�
	 * @return �����Ķ���
	 */
	static Note<?,?> unconstructNote0_2_0(Element element){
		String type;
		boolean lineWrap;
		SerialParam sp = new SerialParam.Productor().product();
		
		Element docElement = (Element) element.selectNodes("att").get(0);
		Element serialParamElement = (Element) element.selectNodes("serp").get(0);
		
		type = element.attribute("type").getValue();
		sp = unconstructSerialParamXML0_2_0(serialParamElement);
		lineWrap = new Boolean(element.attribute("linewrap").getValue()).booleanValue();
		
		
		switch (type) {
			case "txt":
				PlainTextAttachment attachment0 = new PlainTextAttachment(getScpathLastVersion0_0_0( new Scpath(docElement.getText())));
				return new PlainNote.Productor(attachment0).lineWrap(lineWrap).serialParam(sp).product();
			default:
				RTFTextAttachment attachment1 = new RTFTextAttachment(getScpathLastVersion0_0_0( new Scpath(docElement.getText())));
				return new RTFNote.Productor(attachment1).lineWrap(lineWrap).serialParam(sp).product();
		}
	}

	/**
	 * �ӱʼ��й�����Ӧ��Note��XML��Ϣ��
	 * <p> ���õİ汾��0.0.0
	 * @param note ָ���ıʼǶ���
	 * @return �����XML��Ϣ��
	 */
	static Element constructNoteXML0_0_0(RTFNote note) {
		Element noteElement = DocumentHelper.createElement("note");
		Element noteNameElement = DocumentHelper.createElement("name");
		Element noteDescribeElement = DocumentHelper.createElement("describe");
		noteNameElement.addCDATA(note.getSerialParam().getName());
		noteDescribeElement.addCDATA(note.getSerialParam().getDescribe());
		noteElement.add(noteNameElement);
		noteElement.add(noteDescribeElement);
		for(Integer id:note.getSerialParam().getTagIds()){
			Element noteTagElement = DocumentHelper.createElement("tag");
			noteTagElement.add(DocumentHelper.createAttribute(noteTagElement, "id", id.intValue() + ""));
			noteElement.add(noteTagElement);
		}
		Element noteDocElement = DocumentHelper.createElement("doc");
		noteDocElement.addCDATA((getScpathThisVersion0_0_0(note.getTextAttachment().getScpath()).getPathName()));
		noteElement.add(noteDocElement);
		return noteElement;
	}
	
	/**
	 * �ӱʼ��й�����Ӧ��Note��XML��Ϣ��
	 * <p> ���õİ汾��0.1.0
	 * @param note ָ���ıʼǶ���
	 * @return �����XML��Ϣ��
	 */
	static Element constructNoteXML0_1_0(Note<?,?> note) {
		
		Element noteElement = DocumentHelper.createElement("note");
		Element noteNameElement = DocumentHelper.createElement("name");
		Element noteDescribeElement = DocumentHelper.createElement("describe");
		if(note instanceof PlainNote){
			noteElement.addAttribute("type", "txt");
		}else{
			noteElement.addAttribute("type", "rtf");
		}
		noteElement.addAttribute("linewarp", note.isLineWrap() + "");
		noteNameElement.addCDATA(note.getSerialParam().getName());
		noteDescribeElement.addCDATA(note.getSerialParam().getDescribe());
		noteElement.add(noteNameElement);
		noteElement.add(noteDescribeElement);
		for(Integer id:note.getSerialParam().getTagIds()){
			Element noteTagElement = DocumentHelper.createElement("tag");
			noteTagElement.add(DocumentHelper.createAttribute(noteTagElement, "id", id.intValue() + ""));
			noteElement.add(noteTagElement);
		}
		Element noteDocElement = DocumentHelper.createElement("att");
		noteDocElement.addCDATA((getScpathThisVersion0_0_0(note.getTextAttachment().getScpath()).getPathName()));
		noteElement.add(noteDocElement);
		return noteElement;
	}
	
	/**
	 * �ӱʼ��й�����Ӧ��Note��XML��Ϣ��
	 * <p> ���õİ汾��0.2.0
	 * @param note ָ���ıʼǶ���
	 * @return �����XML��Ϣ��
	 */
	static Element constructNoteXML0_2_0(Note<?,?> note) {
		
		Element noteElement = DocumentHelper.createElement("note");
		if(note instanceof PlainNote){
			noteElement.addAttribute("type", "txt");
		}else{
			noteElement.addAttribute("type", "rtf");
		}
		noteElement.addAttribute("linewrap", note.isLineWrap() + "");
		
		noteElement.add(constructSerialParamXML0_2_0(note.getSerialParam()));
		Element noteDocElement = DocumentHelper.createElement("att");
		noteDocElement.addCDATA((getScpathThisVersion0_0_0(note.getTextAttachment().getScpath()).getPathName()));
		noteElement.add(noteDocElement);
		return noteElement;
	}
	
	/**
	 * ��ָ����XMLԪ���н�����Ӧ���������ԡ�
	 * <p> ���õİ汾��0.2.0
	 * @param element ָ����XMLԪ�ء�
	 * @return �����Ķ���
	 */
	static SerialParam unconstructSerialParamXML0_2_0(Element element){
		Element nameElement = (Element) element.selectNodes("name").get(0);
		String name = nameElement.getText();
		
		Element describeElement = (Element) element.selectNodes("describe").get(0);
		String describe = describeElement.getText();
		
		List<?> tagElements = element.selectNodes("tag");
		ArrayList<Integer> ids = new ArrayList<Integer>();
		for(Object obj : tagElements){
			Element tagElement = (Element) obj;
			ids.add(new Integer(tagElement.attribute("id").getValue()));
		}
		
		return new SerialParam.Productor().name(name).describe(describe).tagIds(ids).product();
	}
	
	/**
	 * ������Ӧ���������Զ���
	 * <p> ���ð汾��0.2.0
	 * @param serialParam ָ�����������Զ���
	 * @return �����XML��Ϣ��
	 */
	static Element constructSerialParamXML0_2_0(SerialParam serialParam){
		Element rootElement = DocumentHelper.createElement("serp");
		
		Element nameElement = DocumentHelper.createElement("name");
		nameElement.addCDATA(serialParam.getName());
		rootElement.add(nameElement);
		
		Element describeElement = DocumentHelper.createElement("describe");
		describeElement.addCDATA(serialParam.getDescribe());
		rootElement.add(describeElement);
		
		for(Integer id : serialParam.getTagIds()){
			Element tagElement = DocumentHelper.createElement("tag");
			tagElement.add(DocumentHelper.createAttribute(tagElement, "id", id.intValue() + ""));
			rootElement.add(tagElement);
		}
		
		return rootElement;
	}
	
	/**
	 * ��ָ����XMLԪ���н�����Ӧ�ļ��˼ƻ����ϡ�
	 * <p> ���õİ汾��0.2.0
	 * @param element ָ����XMLԪ�ء�
	 * @return �����ļ��˼ƻ����϶���
	 */
	static ImprovisedPlantCol unconstructImprovisedPlantsXML0_2_0(Element element){

		List<ImprovisedPlant> improvisedPlants = new ArrayList<ImprovisedPlant>();
		
		for(Object obj : element.selectNodes("impl")){
			Element noteElement = (Element) obj;
			improvisedPlants.add(unconstructImprovisedPlantXML0_2_0(noteElement));
		}
		
		return new ImprovisedPlantCol.Productor().improvisedPlains(improvisedPlants).product();
	}

	/**
	 * �Ӽ��˼ƻ������й�����Ӧ�ļ��˼ƻ����ϵ�XML��Ϣ��
	 * <p> ���õİ汾��0.2.0
	 * @param improvisedPlantCol ָ���ļ��˼ƻ����ϡ�
	 * @return �����XML��Ϣ��
	 */
	static Element constructImprovisedPlantsXML0_2_0(ImprovisedPlantCol improvisedPlantCol){
		Element notebookElement = DocumentHelper.createElement("implants");
		for(int o = 0 ; o<improvisedPlantCol.getChildCount() ; o ++){
			ImprovisedPlant improvisedPlant = (ImprovisedPlant) improvisedPlantCol.getChildAt(o);
			notebookElement.add(constructImprovisedPlantXML0_2_0(improvisedPlant));
		}
		return notebookElement;
	}
	
	/**
	 * ��ָ����XMLԪ���н�����Ӧ�ļ��˼ƻ���
	 * <p> ���õİ汾��0.2.0
	 * @param element ָ����XMLԪ�ء�
	 * @return �����ļ��˼ƻ�����
	 */
	static ImprovisedPlant unconstructImprovisedPlantXML0_2_0(Element element){
		boolean lineWrap;
		SerialParam sp = new SerialParam.Productor().product();
		
		Element docElement = (Element) element.selectNodes("att").get(0);
		Element serialParamElement = (Element) element.selectNodes("serp").get(0);
		
		sp = unconstructSerialParamXML0_2_0(serialParamElement);
		lineWrap = new Boolean(element.attribute("linewrap").getValue()).booleanValue();
		
		return new ImprovisedPlant
				.Productor(new PlainTextAttachment(getScpathLastVersion0_0_0( new Scpath(docElement.getText()))))
				.linWrap(lineWrap)
				.serialParam(sp)
				.product();
	}
	
	/**
	 * �Ӽ��˼ƻ��й�����Ӧ�ļ��˼ƻ���XML��Ϣ��
	 * <p> ���õİ汾��0.2.0
	 * @param improvisedPlant ָ���ļ��˼ƻ���
	 * @return �����XML��Ϣ��
	 */
	static Element constructImprovisedPlantXML0_2_0(ImprovisedPlant improvisedPlant){
		Element noteElement = DocumentHelper.createElement("impl");
		
		noteElement.addAttribute("linewrap", improvisedPlant.isLineWrap() + "");
		
		noteElement.add(constructSerialParamXML0_2_0(improvisedPlant.getSerialParam()));
		Element noteDocElement = DocumentHelper.createElement("att");
		noteDocElement.addCDATA((getScpathThisVersion0_0_0(improvisedPlant.getTextAttachment().getScpath()).getPathName()));
		noteElement.add(noteDocElement);
		return noteElement;
	}
	
	private W2SFCommonFunc(){
		//��ֹ�ⲿʵ����
	}
}
