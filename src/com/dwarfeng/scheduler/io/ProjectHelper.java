package com.dwarfeng.scheduler.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
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
import com.dwarfeng.func.io.FileFunc;
import com.dwarfeng.func.io.IOFunc;
import com.dwarfeng.func.util.IDMap;
import com.dwarfeng.scheduler.core.Scheduler;
import com.dwarfeng.scheduler.project.Note;
import com.dwarfeng.scheduler.project.Notebook;
import com.dwarfeng.scheduler.project.NotebookCol;
import com.dwarfeng.scheduler.project.PlainNote;
import com.dwarfeng.scheduler.project.PlainTextAttachment;
import com.dwarfeng.scheduler.project.Project;
import com.dwarfeng.scheduler.project.RTFNote;
import com.dwarfeng.scheduler.project.StyledTextAttachment;
import com.dwarfeng.scheduler.project.Tag;
import com.dwarfeng.scheduler.project.TagMap;
import com.dwarfeng.scheduler.project.TextAttachment;
import com.dwarfeng.scheduler.typedef.exception.ProjectCloseException;
import com.dwarfeng.scheduler.typedef.exception.ProjectPathNotSuccessException;
import com.dwarfeng.scheduler.typedef.exception.ProjectPathNotSuccessException.FailedType;
import com.dwarfeng.scheduler.typedef.exception.StructFailedException;
import com.dwarfeng.scheduler.typedef.exception.UnhandledVersionException;

/**
 * 
 * @author DwArFeng
 * @since 1.8
 */
public final class ProjectHelper {

	// 静态代码块，用于初始化局部变量。
	static{
		//初始化pfr映射池
		pfrPool = new HashMap<Project, Pfr>();
		//初始化文件映射池
		filePool = new HashMap<Project, File>();
	}
	
	/**
	 * 压缩/解压模式锁代表的枚举。
	 * @author DwArFeng
	 * @since 1.8
	 */
	public enum Operate{
		/**代表前台操作（如压缩、解压）的标记*/
		FOREGROUND,
		/**代表后台操作（如压缩、解压）的标记*/
		BACKGROUND
	}
	
	/**
	 * 代表着存档版本的枚举。
	 * <p> 存档版本号是区别不同种类存档的方式。一般来说，随着程序版本的增加，新的功能被陆续的
	 * 添加到程序中，也需要存档的版本进行不断的更新。为了程序更好的向前兼容，需要用版本号来区分新旧存档。
	 * @author DwArFeng
	 * @since 1.8
	 */
	public enum Version{
		
		/**版本号0.0.0*/
		W0_0_0("0.0.0",new W2SF(){
			@Override
			public Project loadStruct(ZipFile file) throws ZipException, IOException, DocumentException {
				return W2SFCommonFunc.loadStruct0_0_0(file);
			}
			
			@Override
			public void saveStruct(Project project, ZipOutputStream zout) throws IOException {
				W2SFCommonFunc.saveStruct0_0_0(project, zout);
			}

			@Override
			public void unzipFile(Project project, ZipFile file, Scpath scpath) throws ZipException, IOException {
				W2SFCommonFunc.unzipFile0_0_0(project, file, scpath);
			}

			@Override
			public void zipFile(Project project, Scpath scpath, ZipOutputStream zout) throws IOException {
				W2SFCommonFunc.zipFile0_0_0(project, scpath, zout);
			}

			@Override
			public Scpath getScpathLastVersion(Scpath scpath) {
				return W2SFCommonFunc.getScpathLastVersion0_0_0(scpath);
			}

			@Override
			public Scpath getScpathThisVersion(Scpath scpath) {
				return W2SFCommonFunc.getScpathThisVersion0_0_0(scpath);
			}
		}),
		/**版本号0.1.0*/
		W0_1_0("0.1.0",new W2SF(){
			@Override
			public Project loadStruct(ZipFile file) throws ZipException, IOException,DocumentException {
				return W2SFCommonFunc.loadStruct0_1_0(file);
			}

			@Override
			public void saveStruct(Project project, ZipOutputStream zout)throws IOException {
				W2SFCommonFunc.saveStruct0_1_0(project, zout);
			}

			@Override
			public void unzipFile(Project project, ZipFile file, Scpath scpath)throws ZipException, IOException {
				W2SFCommonFunc.unzipFile0_0_0(project, file, scpath);
			}

			@Override
			public void zipFile(Project project, Scpath scpath, ZipOutputStream zout)throws IOException {
				W2SFCommonFunc.zipFile0_0_0(project, scpath, zout);
			}

			@Override
			public Scpath getScpathLastVersion(Scpath scpath) {
				return W2SFCommonFunc.getScpathLastVersion0_0_0(scpath);
			}

			@Override
			public Scpath getScpathThisVersion(Scpath scpath) {
				return W2SFCommonFunc.getScpathThisVersion0_0_0(scpath);
			}
		}),
		/**版本号0.2.0*/
		W0_2_0("0.2.0",new W2SF(){
			@Override
			public Project loadStruct(ZipFile file) throws ZipException, IOException,DocumentException {
				return W2SFCommonFunc.loadStruct0_0_0(file);
			}

			@Override
			public void saveStruct(Project project, ZipOutputStream zout)throws IOException {
				W2SFCommonFunc.saveStruct0_0_0(project, zout);
				//TODO
			}

			@Override
			public void unzipFile(Project project, ZipFile file, Scpath scpath)throws ZipException, IOException {
				W2SFCommonFunc.unzipFile0_0_0(project, file, scpath);
				//TODO
			}

			@Override
			public void zipFile(Project project, Scpath scpath, ZipOutputStream zout)throws IOException {
				W2SFCommonFunc.zipFile0_0_0(project, scpath, zout);
				//TODO
			}

			@Override
			public Scpath getScpathLastVersion(Scpath scpath) {
				return W2SFCommonFunc.getScpathLastVersion0_0_0(scpath);
				//TODO
			}

			@Override
			public Scpath getScpathThisVersion(Scpath scpath) {
				return W2SFCommonFunc.getScpathThisVersion0_0_0(scpath);
				//TODO
			}
		});
		
		private final String version;
		private final W2SF w2sf;
		
		private Version(String version,W2SF w2sf){
			this.version = version;
			this.w2sf = w2sf;
		}
		/**
		 * 获取版本枚举所代表的版本号的文本形式。
		 * @return 版本的文本形式。
		 */
		public String getVersionString(){
			return this.toString();
		}
		
	}
	/**最新的存档版本号*/
	public final static String LAST_ZIP_VERSION = "0.1.0";
	/**工程文件与Pfr对应的映射池*/
	private final static Map<Project, Pfr> pfrPool;
	/**工程与文件对应的映射池*/
	private final static Map<Project,File> filePool;
	
	/**
	 * 返回所有当下被加载的工程。
	 * @return 所有被加载的工程集合。
	 */
	public static Set<Project> getAssociatedProjects(){
		Set<Project> set = new HashSet<Project>();
		set.addAll(pfrPool.keySet());
		set.addAll(filePool.keySet());
		return set;
	}
	/**
	 * 获取指定工程对应的文件。
	 * @param project 指定的工程。
	 * @return 工程对应的文件。
	 */
	public static File getProjectFile(Project project){
		if(project == null) throw new NullPointerException("Project can't be null");
		if(filePool.get(project) == null && pfrPool.get(project) == null) 
			throw new IllegalStateException("Bad project.");
		return filePool.get(project);
	}
	
	/**
	 * 为指定的工程设定新的文件（多用于另存为等操作）。
	 * <p> 该操作之后重新建立工程与文件的链接，不会在第一时间对工程进行保存。
	 * <p> 该操作首先会确保工程与工程文件映射的映射关系，如果该工程没有已知的映射，则
	 * 会抛出异常。
	 * @param project 指定的工程。
	 * @param file 新的文件。
	 */
	public static void setProjectFile(Project project,File file){
		if(project == null) throw new NullPointerException("Project can't be null");
		if(file == null) throw new NullPointerException("File can't be null");
		if(pfrPool.get(project) == null)
			throw new IllegalStateException("Bad project - Can't find connected Pfr");
		filePool.put(project, file);
	}
	
	/**
	 * 获取该工程文件映射指定路径的文件输入流。
	 * <p> 该方法会在工作路径的文件准备好之前一直阻塞。
	 * @param project 指定的工程。
	 * @param scpath 指定的路径。
	 * @return 指定路径对应的工作路径输入流。
	 * @throws FileNotFoundException 当路径文件没有找到时抛出的异常。
	 */
	public static InputStream getInputStream(Project project,Scpath scpath) throws FileNotFoundException{
		if(scpath == null) throw new NullPointerException("Scpath can't be null");
		
		Pfr pfr = pfrPool.get(project);
		if(pfr == null) throw new NullPointerException("Pfr is null, may be the project wasn't generated by ProjectHelper");
		
		//TODO 这里应该加上一些有关于异步机制的判断。
		
		File file = new File(pfr.getWorkspaceFile(),scpath.getPathName());
		return new WorkspaceInputStream(file,pfr.getScpathLock(scpath));
	}
	
	/**
	 * 获取该工程文件映射指定路径的文件输入流。
	 * <p> 该方法无论在Project处于什么状态都不会阻塞，请谨慎调用，因为这可能会导致潜在的死锁。
	 * @param project 指定的工程。
	 * @param scpath 指定的路径。
	 * @return 指定路径对应的工作路径输入流。
	 * @throws FileNotFoundException 当路径文件没有找到时抛出的异常。
	 */
	public static InputStream getInputStreamWC(Project project,Scpath scpath) throws FileNotFoundException{
		if(scpath == null) throw new FileNotFoundException();
		Pfr pfr = pfrPool.get(project);
		if(pfr == null) throw new NullPointerException("Pfr is null, may be the project wasn't generated by ProjectHelper");
		File file = new File(pfr.getWorkspaceFile(),scpath.getPathName());
		return new WorkspaceInputStream(file,pfr.getScpathLock(scpath));
	}
	
	/**
	 * 获取该工程文件映射的指定路径的文件输出流。
	 * <p> 该方法会在工作路径的文件准备好之前一直阻塞。
	 * <br>当文件不存在时，该输出流不会自动创建文件。
	 * @param scpath 指定的路径。
	 * @return 指定路径对应的工作路径输出流。
	 * @throws FileNotFoundException 
	 */
	public static OutputStream getOutputStream(Project project,Scpath scpath) throws FileNotFoundException{
		return getOutputStream(project, scpath, false);
	}
	/**
	 * 获取该工程文件映射的指定路径的文件输出流。
	 * <p> 该方法会在工作路径的文件准备好之前一直阻塞。
	 * @param project 指定的工程。 
	 * @param scpath 指定的路径。
	 * @param autoCreateFile 是否自动创建不存在的文件。
	 * @return 指定路径对应的工作路径输出流。
	 * @throws FileNotFoundException 文件没找到时抛出的异常。
	 */
	public static OutputStream getOutputStream(Project project, Scpath scpath,boolean autoCreateFile) throws FileNotFoundException{
		if(scpath == null) throw new FileNotFoundException();
		
		Pfr pfr = pfrPool.get(project);
		if(pfr == null) throw new IllegalArgumentException("Pfr is null, may be the project wasn't generated by ProjectHelper");
		
		//TODO 这里应该加上一些有关于异步机制的判断。
		
		File file = new File(pfr.getWorkspaceFile(),scpath.getPathName());
		return new WorkspaceOutputStream(file,pfr.getScpathLock(scpath),autoCreateFile);
	}
	
	/**
	 * 获取该工程文件映射的指定路径的文件输出流。
	 * <p> 该方法无论在Project处于什么状态都不会阻塞，请谨慎调用，因为这可能会导致潜在的死锁。
	 * @param project 指定的工程。 
	 * @param scpath 指定的路径。
	 * @param autoCreateFile 是否自动创建不存在的文件。
	 * @return 指定路径对应的工作路径输出流。
	 * @throws FileNotFoundException 文件没找到时抛出的异常。
	 */
	public static OutputStream getOutputStreamWC(Project project, Scpath scpath,boolean autoCreateFile) throws FileNotFoundException{
		if(scpath == null) throw new FileNotFoundException();
		Pfr pfr = pfrPool.get(project);
		if(pfr == null) throw new IllegalArgumentException("Pfr is null, may be the project wasn't generated by ProjectHelper");
		File file = new File(pfr.getWorkspaceFile(),scpath.getPathName());
		return new WorkspaceOutputStream(file,pfr.getScpathLock(scpath),autoCreateFile);
	}
	
	/**
	 * 为某个工程生成一个新的Scpath路径。
	 * <p>生成的Scpath由三部分组成，前缀+生成串码+后缀。
	 * @param project 指定的工程。
	 * @param prefix 生成路径的前缀，一般来说此处应该填写指定的文件夹，如"docs\\"
	 * @param suffix 生成路径的后缀，一般来说此处应该填写文件的后缀名，如".rtf";
	 * @return 新的Scpath路径。
	 */
	public static Scpath genSchedulerURL(Project project,String prefix,String suffix){
		
		Pfr pfr = pfrPool.get(project);
		if(pfr == null) throw new IllegalArgumentException("Pfr is null, may be the project wasn't generated by ProjectHelper");
		
		long l = Calendar.getInstance().getTimeInMillis();
		int tryTimes = 0;
		Set<Scpath> scpaths  = project.getScpaths();
		Scpath scpath;
		do{
			scpath = new Scpath(prefix + l + (tryTimes++) + suffix);
		}while(scpaths.contains(scpath));
		CT.trace("为当前项目创建新的文件链接，为" + scpath.getPathName());
		File file = new File(pfr.getWorkspaceFile(),scpath.getPathName());
		File parent = file.getParentFile();
		if(parent != null && !parent.exists()) parent.mkdirs();
		try{
			if(!file.exists()){
				CT.trace("新路径的工作目录映射不存在，创建文件中...");
				file.createNewFile();
				CT.trace("在工作路径下成功的创建文件");
			}
		}catch (Exception e) {
			e.printStackTrace();
			CT.trace("无法在工作路径下创建文件");
		}
		return scpath;
	}

	/**
	 * 创建一个默认的工程。
	 * @param file 该文件对应的文件。
	 * @return 默认的工程。
	 * @throws Exception 工程创建失败。
	 */
	public static Project createDefaultProject(File file) throws Exception{
		if(file == null) throw new NullPointerException("File can't be null");
		
		Project project = null;
		Pfr pfr = null;
		try{
			project = new Project.Productor().product();
			pfr = new Pfr();
			pfrPool.put(project, pfr);
			filePool.put(project, file);
			return project;
		}catch(Exception e){
			disposeProject(project);
			throw new Exception("Failed to create project");
		}
		
	}

	/**
	 * 读取指定路径的工程信息。
	 * @param file 指定的压缩包路径。
	 * @param type 读取的方式，只能为{@linkplain ProjectHelper#FOREGROUND} 和 {@linkplain ProjectHelper#BACKGROUND}中的一个。
	 * @return 根据压缩文件路径读取出的工程文件。
	 * @throws IOException 通信异常。
	 * @throws UnhandledVersionExcepion 版本异常。 
	 * @throws StructFailedException 结构异常。
	 * @throws ProjectPathNotSuccessException 路径不成功异常。
	 */
	public static Project loadProject(File file,Operate type) throws 
		IOException, 
		UnhandledVersionException, 
		StructFailedException, 
		ProjectPathNotSuccessException{
	
		//判断文件为非空
		if(file == null) throw new NullPointerException("File can't be null");
		//生成ZipFile，并且无论如何，该文件都要被关闭。
		ZipFile zipFile = null;
	
		try{
			try{
				//给ZipFile赋值
				zipFile = new ZipFile(file);
			}catch(IOException e){
				IOException ie = new IOException("Failed to link with Sch");
				ie.setStackTrace(e.getStackTrace());
				throw ie;
			}
			
			//读取版本号，并拉取相应的W2SF
			W2SF w2sf = null;
			try {
				w2sf = getW2sf(getZipVersion(zipFile));
			} catch (UnhandledVersionException e) {
				throw e;
			}
			
			//使用相应的W2SF方法生成Project
			Project project = null;			
			Pfr pfr = null;
			
			try{
				pfr = new Pfr();
			}catch(IOException e){
				IOException ie = new IOException("Failed to create Pfr");
				ie.setStackTrace(e.getStackTrace());
				throw ie;
			}
			
			try{
				project = w2sf.loadStruct(zipFile);
			}catch(Exception e){
				throw new StructFailedException();
			}
			
			//建立Project与Pfr的映射关系
			pfrPool.put(project, pfr);
			filePool.put(project, file);
			
			/*
			 * 
			 *  直到此时，该方法一直由主线程运行，而之后的读取，则根据type的类型来判断
			 * 
			 */
			if(type == Operate.FOREGROUND){
				//定义成功与失败列表
				List<Scpath> successList = new ArrayList<Scpath>();
				List<Scpath> failedList = new ArrayList<Scpath>();
				//循环解压所有的路径的指示文件。
				for(Scpath scpath:project.getScpaths()){
					try{
						w2sf.unzipFile(project, zipFile, scpath);
						successList.add(scpath);
					}catch(Exception e){
						failedList.add(scpath);
					}
				}
				//判断是否有失败的解压文件
				if(failedList.size() != 0){
					throw new ProjectPathNotSuccessException(project, successList, failedList, FailedType.LOAD);
				}
			}else{
				//TODO 请添加后台压缩方法。
			}
			
			//如果一切顺利，则顺利返回Project
			return project;
			
		}finally{
			if(zipFile != null){
				try{
					zipFile.close();
				}catch(IOException e){
					e.printStackTrace();
					CT.trace("由于某些原因，ZIP文件没有成功的被关闭");
				}
			}
		}
	}
	
	/**
	 * 将指定的工程文件以指定的版本按照指定的存储方法存储到指定的压缩文件中。
	 * @param project 指定的工程。
	 * @param version 指定的版本号。
	 * @param type 操作方式，只能为{@linkplain Operate#FOREGROUND} 和 {@linkplain Operate#BACKGROUND}中的一个。
	 * @throws IOException IO异常。
	 * @throws UnhandledVersionException 版本异常。 
	 * @throws StructFailedException  结构异常。
	 * @throws ProjectPathNotSuccessException 路径不成功异常。
	 * @throws IllegalStateException 非法状态异常。
	 */
	public static void saveProject(Project project,String version,Operate type) throws 
		IOException, 
		UnhandledVersionException, 
		StructFailedException, 
		ProjectPathNotSuccessException,
		IllegalStateException{
		
		//声明压缩文件输出流
		ZipOutputStream zout = null;
		try{
			//首先判断该工程是否存在对应的pfr
			if(pfrPool.get(project) == null) throw new IllegalStateException("Can't get pfr,may the project has already benn disposed");
			
			try{
				//给zout赋值
				zout = new ZipOutputStream(new FileOutputStream(getProjectFile(project)));
			}catch(IOException e){
				IOException ie = new IOException("Failed to link with Sch");
				ie.setStackTrace(e.getStackTrace());
				throw ie;
			}

			W2SF w2sf = null;
			try{
				//拉取相应版本的W2SF方法
				w2sf = ProjectHelper.getW2sf(version);
			}catch(UnhandledVersionException e){
				throw e;
			}
			
			//尝试写入文件结构。
			try{
				w2sf.saveStruct(project, zout);
			}catch(Exception e){
				throw new StructFailedException();
			}
			
			/*
			 * 
			 *  直到此时，该方法一直由主线程运行，而之后的读取，则根据type的类型来判断
			 * 
			 */
			if(type == Operate.FOREGROUND){
				//定义成功与失败列表
				List<Scpath> successList = new ArrayList<Scpath>();
				List<Scpath> failedList = new ArrayList<Scpath>();
				//循环压缩所有文件
				for(Scpath scpath:project.getScpaths()){
					try{
						w2sf.zipFile(project, scpath, zout);
						successList.add(scpath);
					}catch(Exception e){
						failedList.add(scpath);
					}
				}
				//判断是否有失败的压缩文件
				if(failedList.size() != 0){
					throw new ProjectPathNotSuccessException(project, successList, failedList, FailedType.SAVE);
				}
			}else{
				//TODO 请添加后台压缩方法。
			}
			
		}finally{
			if(zout != null){
				try{
					zout.close();
				}catch(IOException e){
					e.printStackTrace();
					CT.trace("由于某些原因，输出流没有关闭");
				}
			}
		}
		
	}
	/**
	 * 将指定的工程文件按照最新的版本以指定的存储方法存储到指定的压缩文件中。
	 * @param project 指定的工程。
	 * @param type 操作方式，只能为{@linkplain Operate#FOREGROUND} 和 {@linkplain Operate#BACKGROUND}中的一个。
	 * @throws StructFailedException 结构异常。
	 * @throws UnhandledVersionException 版本异常。
	 * @throws IOException IO异常。
	 * @throws ProjectPathNotSuccessException 路径失败异常。
	 * @throws IllegalStateException 状态异常。
	 */ 
	public static void saveProject(Project project ,Operate type) throws 
		IOException, 
		UnhandledVersionException,
		StructFailedException, 
		ProjectPathNotSuccessException{
		saveProject(project, LAST_ZIP_VERSION, type);
	}
	
	/**
	 * 释放指定的工程文件。
	 * <p> 该方法会切断工程和文件池以及工程映射池的联系，并试图删除其工作路径的一切文件。
	 * <br> 该方法会尽一切努力去释放文件。
	 * @param project 指定的工程文件。
	 * @throws ProjectCloseException 
	 * @throws NullPointerException 入口参数为空的时候抛出异常。
	 */
	public static void disposeProject(Project project) throws ProjectCloseException{
		if(project == null) throw new NullPointerException("Project can't be null");
		filePool.remove(project);
		Pfr pfr = pfrPool.remove(project);
		if(pfr == null) throw new ProjectCloseException(project, null,"Pfr is null, may be the project wasn't generated by ProjectHelper");
		FileFunc.deleteFile(pfr.getWorkspaceFile());
	}
	
	/**
	 * 返回指定工程存储文件的文件版本。
	 * <p>注意，该方法不会关闭入口参数ZipFile，该方法一般配合读取和存储一并使用，单独使用时需要额外的提供ZipFile的关闭方法。
	 * @param file 指定的文件。
	 * @return 文件的版本。
	 */
	private static String getZipVersion(ZipFile file){
		
		try{
			//找到version.xml，version.xml的位置无论什么版本都是固定的，最原始的版本没有。
			//version.xml是记录版本信息的xml文件，无论版本如何，该文件位置不变。
			ZipEntry entry = file.getEntry("version.xml");
			InputStream in = file.getInputStream(entry);
			SAXReader reader = new SAXReader();
			Document document = reader.read(in);
			Element version = (Element) document.selectNodes("/version").get(0);
			return version.attribute("value").getValue();
		}catch(Exception e){
			return "0.0.0";
		}
	}
	
	/**
	 * 返回指定版本对应的通信方法。
	 * @param version 指定的版本。
	 * @return 与版本对应的通信方法。
	 * @throws UnhandledVersionException 遇到了无法处理的版本。
	 */
 	private static W2SF getW2sf(String versionString) throws UnhandledVersionException{
		for(Version version:Version.values()){
			if(version.version.equals(versionString)) return version.w2sf;
		}
		List<String> sl = new ArrayList<String>();
		for(Version version:Version.values()){
			sl.add(version.version);
		}
		throw new UnhandledVersionException(versionString,sl.toArray(new String[0]));
	}
	
	private ProjectHelper() {
		// 禁止外部实例化该类。
	}

}























/**
 * 该类定义了工程与文件的映射，提供了工程与文件交互的所有方法。
 * @author DwArFeng。
 * @since 1.8。
 */
class Pfr{
	
	/**工作路径*/
	private final File workSpaceFile;
	/**工程路径的锁池*/
	private final Map<Scpath, ReadWriteLock> scpathLockPool;
	
	/**
	 * 生成标准的工作文件映射。
	 * @throws IOException 当生成工作文件映射时与文件交互发生异常时抛出的异常。
	 */
	public Pfr() throws IOException{
		//生成工作路径。
		this.workSpaceFile = genWorkspaceFile();
		//生成锁池
		scpathLockPool = new HashMap<Scpath, ReadWriteLock>();
		
	}
	
	/**
	 * 获取该工程文件映射中的工作目录所指示的文件。
	 * @return 工作目录所指示的文件。
	 */
	public File getWorkspaceFile(){
		return this.workSpaceFile;
	}
	
	/**
	 * 获取指定路径在该工程文件映射中的同步锁。
	 * <b> 如果在之前，该文件映射中的锁池中没有对应的路径的锁，则会创建新锁，并且添加对该锁的映射。
	 * <b> 只要路径的名称相同，就会获得同一把锁。
	 * @param scpath 指定的路径。
	 * @return 与指定的路径向对应的同步锁。
	 */
	public ReadWriteLock getScpathLock(Scpath scpath){
		scpathLockPool.putIfAbsent(scpath, new ReentrantReadWriteLock());
		return scpathLockPool.get(scpath);
	}
	
	/**
	 * 在工作目录下申请一个文件夹作为这个工程的工作目录文件夹。
	 * @return 指向工作目录的工作目录文件。
	 * @throws IOException 
	 */
	private static File genWorkspaceFile() throws IOException {
		int tryTimes = 0;
		long l = Calendar.getInstance().getTimeInMillis();
		File file;
		String path;
		do{
			path = "" + l + tryTimes + File.separator;
			file = new File(Scheduler.getInstance().getWorkspacePath()  + path + File.separator);
		}while(file.exists());
		//如果不存在这个目录，则创建它。
		file.mkdirs();
		return file;
	}
}





/**
 * 工作文件输入流。
 * <p> 该输入流比起传统的输入流，提供了多线程的安全性。该类作为{@linkplain InputStream}，在
 * 程序调用{@linkplain ProjectHelper#getInputStream(Project, Scpath)} 时返回。
 * @author DwArFeng
 * @since 1.8
 */
class WorkspaceInputStream extends InputStream{
	
	private FileInputStream fin;
	private ReadWriteLock lock;
	
	
	/**
	 * 生成一个具有指定路径的工程文件输入流。
	 * <p>如果在初始化时遇到通信问题，锁将会正确的被释放。
	 * @param file 指定的路径。
	 * @throws FileNotFoundException 当路径文件不存在时抛出的异常。
	 */
	public WorkspaceInputStream(File file,ReadWriteLock lock) throws FileNotFoundException {
		if(lock == null) throw new NullPointerException("Lock can't be null");
		
		this.fin = null;
		this.lock = lock;
		
		try{
			lock.readLock().lock();
			fin = new FileInputStream(file);
		}catch(Exception e){
			lock.readLock().unlock();
			if(fin != null){
				try {
					fin.close();
				} catch (IOException e2) {
					e2.printStackTrace();
					CT.trace("由于某些原因，文件输入流没有成功的关闭");
				}
			}
			if(e instanceof FileNotFoundException) throw (FileNotFoundException) e;
		}

	}

	/**
	 * 关闭该流。
	 * <p>即使该流关闭时发生通信异常，锁也能被正确的释放。
	 * @throws IOException 通信异常。
	 */
	public void close() throws IOException{
		try{
			super.close();
			fin.close();
		}finally{
			lock.readLock().unlock();
		}
	}
	
	@Override
	public int read() throws IOException{
		return fin.read();
	}
	@Override
	public int read(byte[] b) throws IOException {
		return fin.read(b);
	}
	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		return fin.read(b, off, len);
	}
	
}

/**
 * 工作路径输出流。
 * 比起传统的输出流，增加了多线程的安全性，该流作为{@linkplain OutputStream}，
 * 在程序调用{@linkplain ProjectHelper#getOutputStream(Project, Scpath)} 时返回。
 * @author DwArFeng。
 * @since 1.8。
 */
class WorkspaceOutputStream extends OutputStream{
	private FileOutputStream fout;
	private final ReadWriteLock lock;
	
	/**
	 * 
	 * @param file
	 * @throws FileNotFoundException
	 */
	public WorkspaceOutputStream(File file,ReadWriteLock lock) throws FileNotFoundException {
		this(file,lock,false);
	}
	/**
	 * 
	 * @param pathname
	 * @param autoCreateFile
	 * @throws FileNotFoundException
	 */
	public WorkspaceOutputStream(File file,ReadWriteLock lock,boolean autoCreateFile) throws FileNotFoundException{
		if(lock == null) throw new NullPointerException("Lock can't be null");
		
		this.lock = lock;
		this.fout = null;
		
		lock.writeLock().lock();
		try{
			if(autoCreateFile) FileFunc.createFileIfNotExists(file);
			fout = new FileOutputStream(file);
		}catch(Exception e){
			lock.writeLock().unlock();
			if(fout != null){
				try {
					fout.close();
				} catch (IOException e2) {
					e2.printStackTrace();
					CT.trace("由于某些原因，文件输出流没有成功的关闭");
				}
			}
			if(e instanceof FileNotFoundException) throw (FileNotFoundException) e;
		}	
	}

	@Override
	public void close() throws IOException{
		try{
			super.close();
			fout.close();
		}finally{
			lock.writeLock().unlock();
		}

	}
	@Override
	public void write(int b) throws IOException {
		fout.write(b);
	}
	@Override
	public void write(byte[] b) throws IOException{
		fout.write(b);
	}
	@Override
	public void write(byte[] b, int off, int len) throws IOException{
		fout.write(b, off, len);
	}
}










/**
 * 定义工程工作目录层与工作压缩包文档层之间的所有通信方法。
 * <p> 该方法名称是 Workspace to sch file function，取首字母，两个F避免重复，只取一个F。
 * <br> 该结构的方法由不同版本的读取器分别实现。 
 * <br> 不同版本的读取器的命名规则 "W" + versionName.replace('.','_');
 * @author DwArFeng。
 * @since 1.8。
 */
interface W2SF{
	
	/**
	 * 在压缩文件中读取工程的骨架信息。
	 * @param file 指定的压缩文件。
	 * @return 根据文件的骨架信息构造出的工程。
	 * @throws DocumentException XML结构异常。
	 * @throws IOException 通信异常。
	 * @throws ZipException 压缩文件异常。
	 */
	public Project loadStruct(ZipFile file) throws ZipException, IOException, DocumentException;

	/**
	 * 向压缩文件输出流中写出工程的骨架文件。
	 * @param project 指定的工程文件。
	 * @param zout 压缩包输出流。
	 * @throws IOException 通信异常。
	 */
	public void saveStruct(Project project,ZipOutputStream zout) throws IOException;
	
	/**
	 * 从指定的路径中解压指定文件。
	 * @param project 解压相关的工程对象。
	 * @param file 与解压有关的压缩文件。
	 * @param scpath 解压相关的工作目录路径。
	 * @throws IOException 通信异常。
	 * @throws ZipException 压缩文件异常。
	 */
	public void unzipFile(Project project,ZipFile file,Scpath scpath) throws ZipException, IOException;
	
	/**
	 * 想指定的Zip输出流添加指定的文件。
	 * @param pfr 与压缩有关的工程文件映射。
	 * @param zout 与压缩有关的。
	 * @param scpath 指定的工作目录。
	 * @throws IOException 通信异常。
	 */
	public void zipFile(Project project, Scpath scpath, ZipOutputStream zout) throws IOException;
	
	/**
	 * 将该版本的Scpath按照规则转换为这个版本的Scpath。
	 * <p> 注意，必须要保证 {@code scpath.equals(getScpathThisVersion(getScpathLastVersion(scpath)));}
	 * ，也就是说，Scpath转换为最新的版本之后再转换为该版本，所得到的Scpath必须与转换前的一致。
	 * @param scpath 指定的工作目录路径。
	 * @return 转换后的最新版本的工作目录路径。
	 */
	public Scpath getScpathLastVersion(Scpath scpath);
	/**
	 * 将最新版本的Scpath转换为这个版本的Scpath。
	 * <p> 注意，必须要保证 {@code scpath.equals(getScpathThisVersion(getScpathLastVersion(scpath)));}
	 * ，也就是说，Scpath转换为最新的版本之后再转换为该版本，所得到的Scpath必须与转换前的一致。
	 * @param scpath 指定的最新版本的工作目录路径。
	 * @return 这个版本的工作目录路径。
	 */
	public Scpath getScpathThisVersion(Scpath scpath);
}























/**
 * 定义了一些供{@linkplain W2SF} 使用的公共方法。
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
	 * @throws ZipException 压缩文件异常。
	 */
	static Project loadStruct0_0_0(ZipFile file) throws ZipException, IOException, DocumentException{
		//读取文件
		InputStream tagIn = file.getInputStream(file.getEntry("tags.xml"));
		SAXReader tagReader = new SAXReader();
		Element tagElement = null;
		tagElement = (Element) tagReader.read(tagIn).getRootElement();
		
		InputStream notebooksIn = file.getInputStream(file.getEntry("notebooks.xml"));
		SAXReader notebooksReader = new SAXReader();
		Element notebooksElement = notebooksReader.read(notebooksIn).getRootElement();
		
		//解析文件
		TagMap tagMap = unconstructTagsXML0_0_0(tagElement);
		NotebookCol notebookCol = unconstructNotebooks0_0_0(notebooksElement);
		return new Project.Productor().tagMap(tagMap).notebookCol(notebookCol).product();
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
		Document notesbooksXML = DocumentHelper.createDocument(constructNotebooksXML0_0_0(
				(NotebookCol) project.getChildFrom(Project.ChildType.NOTEBOOK_COL))
		);
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
	 * 向指定的工作路径中解压文件。
	 * <p> 适用版本：0.0.0，0.1.0
	 * @param project 相关联的工程。
	 * @param file 相关联的Zip文件。
	 * @param scpath 指定的工作路径。
	 * @throws ZipException Zip文件异常。
	 * @throws IOException IO通信异常。
	 */
	static void unzipFile0_0_0(Project project, ZipFile file, Scpath scpath) throws ZipException, IOException {
		
		CT.trace("正在解压文件：" + scpath + "\t线程描述：" + Thread.currentThread());
		OutputStream wout = null;
		try{
			//生成工作路径的输出流
			wout = ProjectHelper.getOutputStreamWC(project, scpath,true);
			
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
	 * <p> 使用的版本：0.0.0，0.1.0
	 * @param project 相关的工程。
	 * @param zout 指定的zip输出流。
	 * @param scpath 指定的工作路径。
	 * @throws IOException  通信异常。
	 */
	static void zipFile0_0_0(Project project, Scpath scpath, ZipOutputStream zout) throws IOException {
		
		CT.trace("正在压缩文件：" + scpath + "\t线程描述：" + Thread.currentThread());
		InputStream in = null;
		try{
			in = ProjectHelper.getInputStreamWC(project, scpath);
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
	 * <p> 适用版本：0.0.0，0.1.0
	 * @param scpath 指定的Scpath。
	 * @return 转换后的最新版本的Scpath。
	 */
	static Scpath getScpathLastVersion0_0_0(Scpath scpath) {
		return scpath;
	}
	
	/**
	 * 将最新版本的Scpath转换为指定的Scpath。
	 * <p>适用版本：0.0.0,0.1.0
	 * @param scpath 最新版本的Scpath。
	 * @return 指定版本的Scpath。
	 */
	static Scpath getScpathThisVersion0_0_0(Scpath scpath) {
		return scpath;
	}
	
	/**
	 * 读取文件结构并生成文件的方法。
	 * <p> 适用版本：0.1.0
	 * @param file 指定的压缩文件。
	 * @return 生成的工程。
	 * @throws DocumentException XML结构异常。
	 * @throws IOException  IO通信异常。
	 * @throws ZipException 压缩文件异常。
	 */
	static Project loadStruct0_1_0(ZipFile file) throws ZipException, IOException, DocumentException{
		//读取文件
		InputStream tagIn = file.getInputStream(file.getEntry("tags.xml"));
		SAXReader tagReader = new SAXReader();
		Element tagElement = null;
		tagElement = (Element) tagReader.read(tagIn).getRootElement();
		
		InputStream notebooksIn = file.getInputStream(file.getEntry("notebooks.xml"));
		SAXReader notebooksReader = new SAXReader();
		Element notebooksElement = notebooksReader.read(notebooksIn).getRootElement();
		
		//解析文件
		TagMap tagMap = unconstructTagsXML0_0_0(tagElement);
		NotebookCol notebookCol = unconstructNotebooks0_1_0(notebooksElement);
		return new Project.Productor().tagMap(tagMap).notebookCol(notebookCol).product();
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
		Document notesbooksXML = DocumentHelper.createDocument(constructNotebooksXML0_1_0(
				(NotebookCol) project.getChildFrom(Project.ChildType.NOTEBOOK_COL))
		);
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
		return new NotebookCol(notebooks);
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
		return new NotebookCol(notebooks);
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
		List<Note> notes = new Vector<Note>();
		
		
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
		return new Notebook(name, describe, ids, notes);
	}
	
	/**
	 * 从笔记本中构造有关Notebook的XML信息。
	 * <p> 适用的版本：0.0.0
	 * @param notebook 指定的笔记本。
	 * @return 构造的XML信息。
	 */
	@SuppressWarnings("unchecked")
	static Element constructNotebookXML0_0_0(Notebook notebook) {
		Element notebookElement = DocumentHelper.createElement("notebook");
		Element notebookNameElement = DocumentHelper.createElement("name");
		Element notebookDescribeElement = DocumentHelper.createElement("describe");
		notebookNameElement.addCDATA((String)notebook.getParam(Notebook.NAME));
		notebookDescribeElement.addCDATA((String)notebook.getParam(Notebook.DESCRIBE));
		notebookElement.add(notebookNameElement);
		notebookElement.add(notebookDescribeElement);
		for(Integer id:((List<Integer>)notebook.getParam(Notebook.TAGS)).toArray(new Integer[0])){
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
	 * 在指定的XML元素中解析Notebook对象。
	 * <p> 适用的版本：0.1.0
	 * @param element 指定的XML元素。
	 * @return 解析的Notebook对象。
	 */
	static Notebook unconstructNotebook0_1_0 (Element element){
		String name;
		String describe;
		List<Integer> ids = new Vector<Integer>();
		List<Note> notes = new Vector<Note>();
		
		
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
		return new Notebook(name, describe, ids, notes);
	}
	
	/**
	 * 从笔记本中构造有关Notebook的XML信息。
	 * <p> 适用的版本：0.0.0
	 * @param notebook 指定的笔记本。
	 * @return 构造的XML信息。
	 */
	@SuppressWarnings("unchecked")
	static Element constructNotebookXML0_1_0(Notebook notebook) {
		Element notebookElement = DocumentHelper.createElement("notebook");
		Element notebookNameElement = DocumentHelper.createElement("name");
		Element notebookDescribeElement = DocumentHelper.createElement("describe");
		notebookNameElement.addCDATA((String)notebook.getParam(Notebook.NAME));
		notebookDescribeElement.addCDATA((String)notebook.getParam(Notebook.DESCRIBE));
		notebookElement.add(notebookNameElement);
		notebookElement.add(notebookDescribeElement);
		for(Integer id:((List<Integer>)notebook.getParam(Notebook.TAGS)).toArray(new Integer[0])){
			Element notebookTagElement = DocumentHelper.createElement("tag");
			notebookTagElement.add(DocumentHelper.createAttribute(notebookTagElement, "id", id.intValue() + ""));
			notebookElement.add(notebookTagElement);
		}
		for(int o = 0 ; o<notebook.getChildCount() ; o ++){
			Note note = (Note) notebook.getChildAt(o);
			notebookElement.add(constructNoteXML0_1_0(note));
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
		StyledTextAttachment attachment;
		
		
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
		attachment = new StyledTextAttachment(getScpathLastVersion0_0_0( new Scpath(docElement.getText())));
		return new RTFNote(attachment,name, describe, ids);
	}
	
	/**
	 * 从笔记中构造相应的Note的XML信息。
	 * <p> 适用的版本：0.0.0
	 * @param note 指定的笔记对象。
	 * @return 构造的XML信息。
	 */
	@SuppressWarnings("unchecked")
	static Element constructNoteXML0_0_0(RTFNote note) {
		Element noteElement = DocumentHelper.createElement("note");
		Element noteNameElement = DocumentHelper.createElement("name");
		Element noteDescribeElement = DocumentHelper.createElement("describe");
		noteNameElement.addCDATA((String)note.getParam(RTFNote.NAME));
		noteDescribeElement.addCDATA((String)note.getParam(RTFNote.DESCRIBE));
		noteElement.add(noteNameElement);
		noteElement.add(noteDescribeElement);
		for(Integer id:((List<Integer>)note.getParam(RTFNote.TAGS)).toArray(new Integer[0])){
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
	 * 在指定的XML元素中解析Note对象。
	 * <p> 适用的版本：0.1.0
	 * @param element 指定的XML元素。
	 * @return 解析的对象。
	 */
	static Note unconstructNote0_1_0(Element element){
		String type;
		String name;
		String describe;
		boolean lineWarp;
		List<Integer> ids = new Vector<Integer>();
		TextAttachment attachment;
		
		
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
		attachment = null;
		
		type = element.attribute("type").getValue();
		
		lineWarp = new Boolean(element.attribute("linewarp").getValue()).booleanValue();
		
		switch (type) {
			case "txt":
				attachment = new PlainTextAttachment(getScpathLastVersion0_0_0( new Scpath(docElement.getText())));
				return new PlainNote((PlainTextAttachment) attachment,lineWarp,name,describe,ids);
			default:
				attachment = new StyledTextAttachment(getScpathLastVersion0_0_0( new Scpath(docElement.getText())));
				return new RTFNote((StyledTextAttachment) attachment,lineWarp,name, describe, ids);
		}
	}
	
	/**
	 * 从笔记中构造相应的Note的XML信息。
	 * <p> 适用的版本：0.1.0
	 * @param note 指定的笔记对象。
	 * @return 构造的XML信息。
	 */
	@SuppressWarnings("unchecked")
	static Element constructNoteXML0_1_0(Note note) {
		
		Element noteElement = DocumentHelper.createElement("note");
		Element noteNameElement = DocumentHelper.createElement("name");
		Element noteDescribeElement = DocumentHelper.createElement("describe");
		if(note instanceof PlainNote){
			noteElement.addAttribute("type", "txt");
		}else{
			noteElement.addAttribute("type", "rtf");
		}
		noteElement.addAttribute("linewarp", note.isLineWrap() + "");
		noteNameElement.addCDATA((String)note.getParam(RTFNote.NAME));
		noteDescribeElement.addCDATA((String)note.getParam(RTFNote.DESCRIBE));
		noteElement.add(noteNameElement);
		noteElement.add(noteDescribeElement);
		for(Integer id:((List<Integer>)note.getParam(RTFNote.TAGS)).toArray(new Integer[0])){
			Element noteTagElement = DocumentHelper.createElement("tag");
			noteTagElement.add(DocumentHelper.createAttribute(noteTagElement, "id", id.intValue() + ""));
			noteElement.add(noteTagElement);
		}
		Element noteDocElement = DocumentHelper.createElement("att");
		noteDocElement.addCDATA((getScpathThisVersion0_0_0(note.getTextAttachment().getScpath()).getPathName()));
		noteElement.add(noteDocElement);
		return noteElement;
	}
	
	private W2SFCommonFunc(){
		//禁止外部实例化
	}
}
