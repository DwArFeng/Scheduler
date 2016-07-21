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
 * 定义了一些供{@linkplain W2SF} 使用的公共方法。
 * <p> 该接口是Compatible Framework中的一员，主要负责不同版本保存于读取时的兼容性问题。
 * <p> 即使是不同版本的读取器，也可能有大量的相同代码，这些代码应该被存储在这个类中。
 * <br> 原则上，读取器的方法应该尽可能的细化，然后卸载这个类之中，最终需要做到具体的读取器中的所有方法均调用自该类。
 * <br> 规则：方法的名称 + 最早使用这个方法的版本.replace('.','_'); 而且要在注释中明确指出什么这个方法适用的所有版本。
 * @author DwArFeng
 * @since 1.8
 */
final class W2SFCommonFunc{
	
	/*
	 * 
	 * 以下方法是通用方法，对于所有文件适用。
	 * 
	 * 
	 * 
	 * 
	 */
	
	/**
	 * 将版本信息写入工程存档。
	 * <p> 该方法任意版本通用，除了最早期的0.0.0版本未使用之外。
	 * @param version 指定的版本。
	 * @throws IOException  IO通信异常。
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
	 * 以下方法是直接实现 W2FS 接口的方法
	 * 
	 * 是被其实现类直接调用的方法。
	 * 
	 * 
	 * 
	 * 
	 */
	
	/**
	 * 读取文件结构并生成文件的方法。
	 * <p> 适用版本：0.0.0
	 * @param file 指定的压缩文件。
	 * @return 生成的工程。
	 * @throws DocumentException XML结构异常。
	 * @throws IOException  IO通信异常。
	 */
	static Project loadStruct0_0_0(ZipFile file) throws IOException, UnstructFailedException{
		//构造输入流和读取器
		InputStream tagIn = file.getInputStream(file.getEntry("tags.xml"));
		SAXReader tagReader = new SAXReader();
		InputStream notebooksIn = file.getInputStream(file.getEntry("notebooks.xml"));
		SAXReader notebooksReader = new SAXReader();
		//声明元素变量
		Element tagElement = null;
		Element notebooksElement = null;
		
		//尝试解析所有元素
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
		
		//解析文件
		TagMap tagMap = unconstructTagsXML0_0_0(tagElement);
		NotebookCol notebookCol = unconstructNotebooks0_0_0(notebooksElement);
		return new Project.Productor().tagMap(tagMap).notebookCol(notebookCol).product();
	}
	
	/**
	 * 读取文件结构并生成文件的方法。
	 * <p> 适用版本：0.1.0
	 * @param file 指定的压缩文件。
	 * @return 生成的工程。
	 * @throws DocumentException XML结构异常。
	 * @throws IOException  IO通信异常。
	 */
	static Project loadStruct0_1_0(ZipFile file) throws IOException,UnstructFailedException{
		//构造输入流和读取器
		InputStream tagIn = file.getInputStream(file.getEntry("tags.xml"));
		SAXReader tagReader = new SAXReader();
		InputStream notebooksIn = file.getInputStream(file.getEntry("notebooks.xml"));
		SAXReader notebooksReader = new SAXReader();
		//声明元素变量
		Element tagElement = null;
		Element notebooksElement = null;
		
		//尝试解析所有元素
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
		
		//解析文件
		TagMap tagMap = unconstructTagsXML0_0_0(tagElement);
		NotebookCol notebookCol = unconstructNotebooks0_1_0(notebooksElement);
		return new Project.Productor().tagMap(tagMap).notebookCol(notebookCol).product();
	}
	
	/**
	 * 读取文件结构并生成文件的方法。
	 * <p> 适用版本：0.2.0
	 * @param file 指定的压缩文件。
	 * @return 生成的工程。
	 * @throws DocumentException XML结构异常。
	 * @throws IOException  IO通信异常。
	 */
	static Project loadStruct0_2_0(ZipFile file) throws IOException,UnstructFailedException{
		//构造输入流和读取器
		InputStream tagIn = file.getInputStream(file.getEntry("tags.xml"));
		SAXReader tagReader = new SAXReader();
		InputStream notebooksIn = file.getInputStream(file.getEntry("notebooks.xml"));
		SAXReader notebooksReader = new SAXReader();
		InputStream implantsIn = file.getInputStream(file.getEntry("implants.xml"));
		SAXReader implantsReader = new SAXReader();
		//声明元素变量
		Element tagElement = null;
		Element notebooksElement = null;
		Element implantsElement = null;
		
		//尝试解析所有元素
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
		
		//解析文件
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
	 * 向指定Zip输出流输出工程文件的结构信息。
	 * <p> 适用的版本：0.0.0
	 * @param project 指定的工程文件。
	 * @param zout 指定的输出流。
	 * @throws IOException 通信异常。
	 */
	static void saveStruct0_0_0(Project project, ZipOutputStream zout) throws IOException {
		//写入版本名称
		saveVersionInfo(zout, "0.0.0");
		//构造XML信息
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
		
		//将XML文件写入压缩文件输出流。
		zout.putNextEntry(new ZipEntry("tags.xml"));
		XMLWriter writer = new XMLWriter(zout, format);
		writer.write(tagsXML);
		zout.putNextEntry(new ZipEntry("notebooks.xml"));
		writer.write(notesbooksXML);
	}
	
	/**
	 * 向指定Zip输出流输出工程文件的结构信息。
	 * <p> 适用的版本：0.1.0
	 * @param project 指定的工程文件。
	 * @param zout 指定的输出流。
	 * @throws IOException 通信异常。
	 */
	static void saveStruct0_1_0(Project project, ZipOutputStream zout) throws IOException {
		//写入版本名称
		saveVersionInfo(zout, "0.1.0");
		//构造XML信息
		Document tagsXML = DocumentHelper.createDocument(constructTagsXML0_0_0(project.getTagMap()));
		Document notesbooksXML = null;
		try {
			notesbooksXML = DocumentHelper.createDocument(constructNotebooksXML0_1_0(
					project.getChildFromType(NotebookCol.class)
			));
		} catch (ClassNotFoundException e) {
			//因为工程中含有NotebookCol，所以不会出现异常。
		}
		tagsXML.setXMLEncoding("UTF-8");
		notesbooksXML.setXMLEncoding("UTF-8");
		OutputFormat format = OutputFormat.createPrettyPrint();
		
		//将XML文件写入压缩文件输出流。
		zout.putNextEntry(new ZipEntry("tags.xml"));
		XMLWriter writer = new XMLWriter(zout, format);
		writer.write(tagsXML);
		zout.putNextEntry(new ZipEntry("notebooks.xml"));
		writer.write(notesbooksXML);
	}
	
	/**
	 * 向指定Zip输出流输出工程文件的结构信息。
	 * <p> 适用的版本：0.2.0
	 * @param project 指定的工程文件。
	 * @param zout 指定的输出流。
	 * @throws IOException 通信异常。
	 */
	static void saveStruct0_2_0(Project project, ZipOutputStream zout) throws IOException {
		//写入版本名称
		saveVersionInfo(zout, "0.2.0");
		//构造XML信息
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
			//因为工程中含有NotebookCol，所以不会出现异常。
		}
		tagsXML.setXMLEncoding("UTF-8");
		notesbooksXML.setXMLEncoding("UTF-8");
		implantsXML.setXMLEncoding("UTF-8");
		OutputFormat format = OutputFormat.createPrettyPrint();
		
		//将XML文件写入压缩文件输出流。
		XMLWriter writer = new XMLWriter(zout, format);
		
		zout.putNextEntry(new ZipEntry("tags.xml"));
		writer.write(tagsXML);
		zout.putNextEntry(new ZipEntry("notebooks.xml"));
		writer.write(notesbooksXML);
		zout.putNextEntry(new ZipEntry("implants.xml"));
		writer.write(implantsXML);
	}

	/**
	 * 向指定的工作路径中解压文件。
	 * <p> 适用版本：0.0.0，0.1.0 , 0.2.0
	 * @param project 相关联的工程。
	 * @param file 相关联的Zip文件。
	 * @param scpath 指定的工作路径。
	 * @throws ZipException Zip文件异常。
	 * @throws IOException IO通信异常。
	 */
	static void unzipFile0_0_0(Project project, ZipFile file, Scpath scpath) throws IOException {
		
		OutputStream wout = null;
		try{
			//生成工作路径的输出流
			wout = ProjectIoHelper.getOutputStreamWC(project, scpath,true);
			
			//生成Zip的输入流
			Scpath thisVersionScpath = getScpathThisVersion0_0_0(scpath);
			String str = thisVersionScpath.getPathName();
			str = str.replace(File.separatorChar, '/');
			InputStream zin = file.getInputStream(file.getEntry(str));
			
			//输入流的内容传递到输出流
			IOFunc.trans(zin, wout, 4096);
			
		}finally{
			if(wout != null){
				try{
					wout.close();
				}catch(Exception e){
					e.printStackTrace();
					CT.trace("由于某些原因，工作路径输出流没有成功的被关闭");
				}
			}
		}
		
	}

	/**
	 * 向指定的压缩文件输出流中压缩工作路径文件。
	 * <p> 使用的版本：0.0.0，0.1.0 , 0.2.0
	 * @param project 相关的工程。
	 * @param zout 指定的zip输出流。
	 * @param scpath 指定的工作路径。
	 * @throws IOException  通信异常。
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
					CT.trace("由于某些原因，输入流没有正常关闭");
				}
			}
		}
		
	}
	
	/**
	 * 将指定的Scpath转换为最新版本的Scpath。
	 * <p> 适用版本：0.0.0，0.1.0 , 0.2.0
	 * @param scpath 指定的Scpath。
	 * @return 转换后的最新版本的Scpath。
	 */
	static Scpath getScpathLastVersion0_0_0(Scpath scpath) {
		return scpath;
	}
	
	/**
	 * 将最新版本的Scpath转换为指定的Scpath。
	 * <p>适用版本：0.0.0 , 0.1.0 , 0.2.0
	 * @param scpath 最新版本的Scpath。
	 * @return 指定版本的Scpath。
	 */
	static Scpath getScpathThisVersion0_0_0(Scpath scpath) {
		return scpath;
	}
	
	
	
	
	
	
	
	/*
	 * 以下方法是针对Tags的方法
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	
	
	/**
	 * 在tagXML文件中解析IDMap信息。
	 * <p> 适用的版本： 0.0.0，0.1.0
	 * @param element XML文本。
	 * @return 解析的TagID映射信息。
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
	 * 从ID-标签映射表中构造Tag的XML信息。
	 * <p> 适用的版本：0.0.0，0.1.0
	 * @param tagMap 指定的ID-标签映射表。
	 * @return 构造的XML信息。
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
	  * 以下方法是针对Notebooks的。
	  * 
	  * 
	  * 
	  * 
	  */
	
	/**
	 * 从指定的XML文档中解析笔记本。
	 * <p> 适用的版本：0.0.0
	 * @param element 指定的XML文档。
	 * @return 解析出的笔记本。
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
	 * 从指定的XML文档中解析笔记本。
	 * <p> 适用的版本：0.1.0
	 * @param element 指定的XML文档。
	 * @return 解析出的笔记本。
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
	 * 从指定的XML文档中解析笔记本。
	 * <p> 适用的版本：0.2.0
	 * @param element 指定的XML文档。
	 * @return 解析出的笔记本。
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
	 * 从笔记本集合中构造Notebooks的XML信息。
	 * <p>适用的版本：0.0.0
	 * @param notebooks 指定的笔记本集合。
	 * @return 构造的XML信息。
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
	 * 从笔记本集合中构造Notebooks的XML信息。
	 * <p>适用的版本：0.1.0
	 * @param notebooks 指定的笔记本集合。
	 * @return 构造的XML信息。
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
	 * 从笔记本集合中构造Notebooks的XML信息。
	 * <p>适用的版本：0.2.0
	 * @param notebooks 指定的笔记本集合。
	 * @return 构造的XML信息。
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
	 * 以下方法是针对Notebook的。
	 * 
	 * 
	 * 
	 */
	
	/**
	 * 在指定的XML元素中解析Notebook对象。
	 * <p> 适用的版本：0.0.0
	 * @param element 指定的XML元素。
	 * @return 解析的Notebook对象。
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
	 * 在指定的XML元素中解析Notebook对象。
	 * <p> 适用的版本：0.1.0
	 * @param element 指定的XML元素。
	 * @return 解析的Notebook对象。
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
	 * 在指定的XML元素中解析Notebook对象。
	 * <p> 适用的版本：0.2.0
	 * @param element 指定的XML元素。
	 * @return 解析的Notebook对象。
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
	 * 从笔记本中构造有关Notebook的XML信息。
	 * <p> 适用的版本：0.0.0
	 * @param notebook 指定的笔记本。
	 * @return 构造的XML信息。
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
	 * 从笔记本中构造有关Notebook的XML信息。
	 * <p> 适用的版本：0.1.0
	 * @param notebook 指定的笔记本。
	 * @return 构造的XML信息。
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
	 * 从笔记本中构造有关Notebook的XML信息。
	 * <p> 适用的版本：0.2.0
	 * @param notebook 指定的笔记本。
	 * @return 构造的XML信息。
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
	 * 以下方法是针对note的。
	 * 
	 * 
	 * 
	 */

	/**
	 * 在指定的XML元素中解析Note对象。
	 * <p> 适用的版本：0.0.0
	 * @param element 指定的XML元素。
	 * @return 解析的对象。
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
	 * 在指定的XML元素中解析Note对象。
	 * <p> 适用的版本：0.1.0
	 * @param element 指定的XML元素。
	 * @return 解析的对象。
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
	 * 在指定的XML元素中解析Note对象。
	 * <p> 适用的版本：0.2.0
	 * @param element 指定的XML元素。
	 * @return 解析的对象。
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
	 * 从笔记中构造相应的Note的XML信息。
	 * <p> 适用的版本：0.0.0
	 * @param note 指定的笔记对象。
	 * @return 构造的XML信息。
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
	 * 从笔记中构造相应的Note的XML信息。
	 * <p> 适用的版本：0.1.0
	 * @param note 指定的笔记对象。
	 * @return 构造的XML信息。
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
	 * 从笔记中构造相应的Note的XML信息。
	 * <p> 适用的版本：0.2.0
	 * @param note 指定的笔记对象。
	 * @return 构造的XML信息。
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
	 * 从指定的XML元素中解析相应的序列属性。
	 * <p> 适用的版本：0.2.0
	 * @param element 指定的XML元素。
	 * @return 解析的对象。
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
	 * 构造相应的序列属性对象。
	 * <p> 适用版本：0.2.0
	 * @param serialParam 指定的序列属性对象。
	 * @return 构造的XML信息。
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
	 * 从指定的XML元素中解析相应的即兴计划集合。
	 * <p> 适用的版本：0.2.0
	 * @param element 指定的XML元素。
	 * @return 解析的即兴计划集合对象。
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
	 * 从即兴计划集合中构造相应的即兴计划集合的XML信息。
	 * <p> 适用的版本：0.2.0
	 * @param improvisedPlantCol 指定的即兴计划集合。
	 * @return 构造的XML信息。
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
	 * 从指定的XML元素中解析相应的即兴计划。
	 * <p> 适用的版本：0.2.0
	 * @param element 指定的XML元素。
	 * @return 解析的即兴计划对象。
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
	 * 从即兴计划中构造相应的即兴计划的XML信息。
	 * <p> 适用的版本：0.2.0
	 * @param improvisedPlant 指定的即兴计划。
	 * @return 构造的XML信息。
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
		//禁止外部实例化
	}
}
