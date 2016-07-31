package com.dwarfeng.scheduler.module;

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
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.dwarfeng.dwarffunction.io.CT;
import com.dwarfeng.dwarffunction.io.FileFunction;
import com.dwarfeng.scheduler.core.Scheduler133;
import com.dwarfeng.scheduler.module.project.Project;
import com.dwarfeng.scheduler.typedef.exception.ProjectCloseException;
import com.dwarfeng.scheduler.typedef.exception.ProjectPathNotSuccessException;
import com.dwarfeng.scheduler.typedef.exception.ProjectPathNotSuccessException.FailedType;
import com.dwarfeng.scheduler.typedef.exception.UnhandledVersionException;
import com.dwarfeng.scheduler.typedef.exception.UnstructFailedException;

/**
 * 提供工程与文件通信的常用的方法的工厂类。
 * 这个工厂类提供了所有的工程与文件通信的方法，这些方法都是基本方法，是不可代替的。因此，该类和某些只提供方法整合的工厂类不一样，
 * 是一个有大量独立方法实现的工厂类。
 * @author DwArFeng
 * @since 1.8
 */
public final class SProjectIoHelper {

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
	
	/**最新的存档版本号*/
	public final static String LAST_ZIP_VERSION = "0.2.0";
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
		filePool.put(project, file.getAbsoluteFile());
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
		
		File file = getScpathFile(pfr, scpath);
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
		File file = getScpathFile(pfr, scpath);
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
		
		File file = getScpathFile(pfr, scpath);
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
		File file = getScpathFile(pfr, scpath);
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
		File file = getScpathFile(pfr, scpath);
		try{
			if(!file.exists()){
				CT.trace("新路径的工作目录映射不存在，创建文件中...");
				FileFunction.createFileIfNotExists(file);
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
			//默认创建。
			project = new Project.Productor().product();
			pfr = new Pfr();
			//对文件进行连接
			pfrPool.put(project, pfr);
			filePool.put(project, file.getAbsoluteFile());
			//创建必要的初始文件
			PImprovisedPlantCol improvisedPlantCol = null;
			try{
				improvisedPlantCol = project.getChildFromType(PImprovisedPlantCol.class);
			}catch(ClassNotFoundException e){
				//因为工程中存在ImprovisedPlantCol子节点，所以不会抛出异常。
			}
			improvisedPlantCol.getAttachment().save(improvisedPlantCol.getAttachment().createDefaultObject());
			return project;
		}catch(Exception e){
			closeProject(project);
			throw new Exception("Failed to create project");
		}
		
	}

	/**
	 * 读取指定路径的工程信息。
	 * @param file 指定的压缩包路径。
	 * @param type 读取的方式，为泛型{@linkplain Operate}中的值。
	 * @return 根据压缩文件路径读取出的工程文件。
	 * @throws IOException 通信异常。
	 * @throws UnhandledVersionExcepion 版本异常。 
	 * @throws UnstructFailedException 结构异常。
	 * @throws ProjectPathNotSuccessException 路径不成功异常。
	 */
	public static Project loadProject(File file,Operate type) throws 
		IOException, 
		UnhandledVersionException, 
		UnstructFailedException, 
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
			}catch(IOException e){
				IOException ioe = new IOException("Failed to read internal sch file");
				ioe.setStackTrace(e.getStackTrace());
				throw ioe;
			}catch (UnstructFailedException e) {
				throw e;
			}
			
			//建立Project与Pfr的映射关系
			pfrPool.put(project, pfr);
			filePool.put(project, file.getAbsoluteFile());
			
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
						CT.trace("正在解压文件：" + scpath + "\t线程描述：" + Thread.currentThread());
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
	 * @throws ProjectPathNotSuccessException 路径不成功异常。
	 * @throws IllegalStateException 非法状态异常。
	 */
	public static void saveProject(Project project,String version,Operate type) throws 
		IOException, 
		UnhandledVersionException, 
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
				w2sf = SProjectIoHelper.getW2sf(version);
			}catch(UnhandledVersionException e){
				throw e;
			}
			
			//尝试写入文件结构。
			try{
				w2sf.saveStruct(project, zout);
			}catch(IOException e){
				IOException ioe = new IOException("Failed to write internal sch file");
				ioe.setStackTrace(e.getStackTrace());
				throw ioe;
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
						CT.trace("正在压缩文件：" + scpath + "\t线程描述：" + Thread.currentThread());
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
	 * @throws IOException IO异常。
	 * @throws ProjectPathNotSuccessException 路径失败异常。
	 * @throws IllegalStateException 状态异常。
	 */ 
	public static void saveProject(Project project ,Operate type) throws 
		IOException, 
		ProjectPathNotSuccessException{
		try {
			saveProject(project, LAST_ZIP_VERSION, type);
		}catch (UnhandledVersionException e) {
			e.printStackTrace();
			CT.trace("无法从LAST_ZIP_VERSION字段描述的版本中找到构造器，请联系开发人员");
		}
	}
	
	/**
	 * 释放指定的工程文件。
	 * <p> 该方法会切断工程和文件池以及工程映射池的联系，并试图删除其工作路径的一切文件。
	 * <br> 该方法会尽一切努力去释放文件。
	 * @param project 指定的工程文件。
	 * @throws ProjectCloseException 文件不能正常关闭时抛出的异常。
	 * @throws NullPointerException 入口参数为空的时候抛出异常。
	 */
	public static void closeProject(Project project) throws ProjectCloseException{
		if(project == null) throw new NullPointerException("Project can't be null");
		filePool.remove(project);
		Pfr pfr = pfrPool.remove(project);
		if(pfr == null) throw new ProjectCloseException(project, null,"Pfr is null, may be the project wasn't generated by ProjectHelper");
		if(!FileFunction.deleteFile(pfr.getWorkspaceFile())) 
			throw new ProjectCloseException(project, getScpathFile(pfr, new Scpath("")),"Can't delete workspace completely");
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
		for(ProjectVersion version:ProjectVersion.values()){
			if(version.getVersionString().equals(versionString)) return version.getW2sf();
		}
		List<String> sl = new ArrayList<String>();
		for(ProjectVersion version:ProjectVersion.values()){
			sl.add(version.getVersionString());
		}
		throw new UnhandledVersionException(versionString,sl.toArray(new String[0]));
	}
 	
 	/**
 	 * 通过工程文件映射和工程路径返回文件。
 	 * @param pfr 工程文件映射。
 	 * @param scpath 工程路径。
 	 * @return 返回的文件。
 	 */
 	private static File getScpathFile(Pfr pfr,Scpath scpath){
 		return new File(pfr.getWorkspaceFile(),scpath.getPathName());
 	}
	
	private SProjectIoHelper() {
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
			file = new File(Scheduler133.getInstance().getWorkspacePath()  + path + File.separator);
		}while(file.exists());
		//如果不存在这个目录，则创建它。
		file.mkdirs();
		return file;
	}
}





/**
 * 工作文件输入流。
 * <p> 该输入流比起传统的输入流，提供了多线程的安全性。该类作为{@linkplain InputStream}，在
 * 程序调用{@linkplain SProjectIoHelper#getInputStream(Project, Scpath)} 时返回。
 * @author DwArFeng
 * @since 1.8
 */
class WorkspaceInputStream extends InputStream{
	
	private FileInputStream fin;
	private final ReadWriteLock lock;
	
	
	/**
	 * 生成一个具有指定输入文件的工程文件输入流。
	 * <p>如果在初始化时遇到通信问题，锁将会正确的被释放。
	 * @param file 指定的输入文件。
	 * @param lock 同步读写锁。
	 * @throws FileNotFoundException 当路径文件不存在时抛出的异常。
	 */
	public WorkspaceInputStream(File file,ReadWriteLock lock) throws FileNotFoundException {
		if(lock == null) throw new NullPointerException("Lock can't be null");
		
		this.fin = null;
		this.lock = lock;
		
		try{
			fin = new FileInputStream(file);
		}catch(FileNotFoundException e){
			if(fin != null){
				try {
					fin.close();
				} catch (IOException e2) {
					e2.printStackTrace();
					CT.trace("由于某些原因，文件输入流没有成功的关闭");
				}
			}
			throw e;
		}

	}

	/**
	 * 关闭该流。
	 * <p>即使该流关闭时发生通信异常，锁也能被正确的释放。
	 * @throws IOException 通信异常。
	 */
	@Override
	public void close() throws IOException{
		super.close();
		fin.close();
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.io.InputStream#read()
	 */
	@Override
	public int read() throws IOException{
		lock.readLock().lock();
		try{
			return fin.read();
		}finally{
			lock.readLock().unlock();
		}
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.io.InputStream#read(byte[])
	 */
	@Override
	public int read(byte[] b) throws IOException {
		lock.readLock().lock();
		try{
			return fin.read(b);
		}finally{
			lock.readLock().unlock();
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.io.InputStream#read(byte[], int, int)
	 */
	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		lock.readLock().lock();
		try{
			return fin.read(b, off, len);
		}finally{
			lock.readLock().unlock();
		}
		
	}
	
}

/**
 * 工作路径输出流。
 * 比起传统的输出流，增加了多线程的安全性，该流作为{@linkplain OutputStream}，
 * 在程序调用{@linkplain SProjectIoHelper#getOutputStream(Project, Scpath)} 时返回。
 * @author DwArFeng。
 * @since 1.8。
 */
class WorkspaceOutputStream extends OutputStream{
	private FileOutputStream fout;
	private final ReadWriteLock lock;
	
	/**
	 * 生成一个具有指定输出文件文件的工作路径输入流。
	 * <p> 该方法不会自动创建文件。
	 * @param file 指定的输出文件。
	 * @param lock 指定的同步读写锁。
	 * @throws FileNotFoundException 文件不存在时抛出的异常。
	 */
	public WorkspaceOutputStream(File file,ReadWriteLock lock) throws FileNotFoundException {
		this(file,lock,false);
	}

	/**
	 * 生成一个具有指定输出文件的工作路径输出流。
	 * @param file 指定的输出文件。
	 * @param lock 指定的同步锁。
	 * @param autoCreateFile 是否在文件不存在时自动创建文件。
	 * @throws FileNotFoundException 当不自动创建文件，且文件为找到时抛出异常。
	 */
	public WorkspaceOutputStream(File file,ReadWriteLock lock,boolean autoCreateFile) throws FileNotFoundException{
		if(lock == null) throw new NullPointerException("Lock can't be null");
		
		this.lock = lock;
		this.fout = null;
		
		try{
			if(autoCreateFile) FileFunction.createFileIfNotExists(file);
			fout = new FileOutputStream(file);
		}catch(Exception e){
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

	/*
	 * (non-Javadoc)
	 * @see java.io.OutputStream#close()
	 */
	@Override
	public void close() throws IOException{
		super.close();
		fout.close();
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.io.OutputStream#write(int)
	 */
	@Override
	public void write(int b) throws IOException {
		lock.writeLock().lock();
		try{
			fout.write(b);
		}finally{
			lock.writeLock().unlock();
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.io.OutputStream#write(byte[])
	 */
	@Override
	public void write(byte[] b) throws IOException{
		lock.writeLock().lock();
		try{
			fout.write(b);
		}finally{
			lock.writeLock().unlock();
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.io.OutputStream#write(byte[], int, int)
	 */
	@Override
	public void write(byte[] b, int off, int len) throws IOException{
		lock.writeLock().lock();
		try{
			fout.write(b, off, len);
		}finally{
			lock.writeLock().unlock();
		}
	}
}
