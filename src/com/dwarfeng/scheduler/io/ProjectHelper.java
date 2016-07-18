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

	// ��̬����飬���ڳ�ʼ���ֲ�������
	static{
		//��ʼ��pfrӳ���
		pfrPool = new HashMap<Project, Pfr>();
		//��ʼ���ļ�ӳ���
		filePool = new HashMap<Project, File>();
	}
	
	/**
	 * ѹ��/��ѹģʽ��������ö�١�
	 * @author DwArFeng
	 * @since 1.8
	 */
	public enum Operate{
		/**����ǰ̨��������ѹ������ѹ���ı��*/
		FOREGROUND,
		/**������̨��������ѹ������ѹ���ı��*/
		BACKGROUND
	}
	
	/**
	 * �����Ŵ浵�汾��ö�١�
	 * <p> �浵�汾��������ͬ����浵�ķ�ʽ��һ����˵�����ų���汾�����ӣ��µĹ��ܱ�½����
	 * ���ӵ������У�Ҳ��Ҫ�浵�İ汾���в��ϵĸ��¡�Ϊ�˳�����õ���ǰ���ݣ���Ҫ�ð汾���������¾ɴ浵��
	 * @author DwArFeng
	 * @since 1.8
	 */
	public enum Version{
		
		/**�汾��0.0.0*/
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
		/**�汾��0.1.0*/
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
		/**�汾��0.2.0*/
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
		 * ��ȡ�汾ö���������İ汾�ŵ��ı���ʽ��
		 * @return �汾���ı���ʽ��
		 */
		public String getVersionString(){
			return this.toString();
		}
		
	}
	/**���µĴ浵�汾��*/
	public final static String LAST_ZIP_VERSION = "0.1.0";
	/**�����ļ���Pfr��Ӧ��ӳ���*/
	private final static Map<Project, Pfr> pfrPool;
	/**�������ļ���Ӧ��ӳ���*/
	private final static Map<Project,File> filePool;
	
	/**
	 * �������е��±����صĹ��̡�
	 * @return ���б����صĹ��̼��ϡ�
	 */
	public static Set<Project> getAssociatedProjects(){
		Set<Project> set = new HashSet<Project>();
		set.addAll(pfrPool.keySet());
		set.addAll(filePool.keySet());
		return set;
	}
	/**
	 * ��ȡָ�����̶�Ӧ���ļ���
	 * @param project ָ���Ĺ��̡�
	 * @return ���̶�Ӧ���ļ���
	 */
	public static File getProjectFile(Project project){
		if(project == null) throw new NullPointerException("Project can't be null");
		if(filePool.get(project) == null && pfrPool.get(project) == null) 
			throw new IllegalStateException("Bad project.");
		return filePool.get(project);
	}
	
	/**
	 * Ϊָ���Ĺ����趨�µ��ļ�������������Ϊ�Ȳ�������
	 * <p> �ò���֮�����½����������ļ������ӣ������ڵ�һʱ��Թ��̽��б��档
	 * <p> �ò������Ȼ�ȷ�������빤���ļ�ӳ���ӳ���ϵ������ù���û����֪��ӳ�䣬��
	 * ���׳��쳣��
	 * @param project ָ���Ĺ��̡�
	 * @param file �µ��ļ���
	 */
	public static void setProjectFile(Project project,File file){
		if(project == null) throw new NullPointerException("Project can't be null");
		if(file == null) throw new NullPointerException("File can't be null");
		if(pfrPool.get(project) == null)
			throw new IllegalStateException("Bad project - Can't find connected Pfr");
		filePool.put(project, file);
	}
	
	/**
	 * ��ȡ�ù����ļ�ӳ��ָ��·�����ļ���������
	 * <p> �÷������ڹ���·�����ļ�׼����֮ǰһֱ������
	 * @param project ָ���Ĺ��̡�
	 * @param scpath ָ����·����
	 * @return ָ��·����Ӧ�Ĺ���·����������
	 * @throws FileNotFoundException ��·���ļ�û���ҵ�ʱ�׳����쳣��
	 */
	public static InputStream getInputStream(Project project,Scpath scpath) throws FileNotFoundException{
		if(scpath == null) throw new NullPointerException("Scpath can't be null");
		
		Pfr pfr = pfrPool.get(project);
		if(pfr == null) throw new NullPointerException("Pfr is null, may be the project wasn't generated by ProjectHelper");
		
		//TODO ����Ӧ�ü���һЩ�й����첽���Ƶ��жϡ�
		
		File file = new File(pfr.getWorkspaceFile(),scpath.getPathName());
		return new WorkspaceInputStream(file,pfr.getScpathLock(scpath));
	}
	
	/**
	 * ��ȡ�ù����ļ�ӳ��ָ��·�����ļ���������
	 * <p> �÷���������Project����ʲô״̬��������������������ã���Ϊ����ܻᵼ��Ǳ�ڵ�������
	 * @param project ָ���Ĺ��̡�
	 * @param scpath ָ����·����
	 * @return ָ��·����Ӧ�Ĺ���·����������
	 * @throws FileNotFoundException ��·���ļ�û���ҵ�ʱ�׳����쳣��
	 */
	public static InputStream getInputStreamWC(Project project,Scpath scpath) throws FileNotFoundException{
		if(scpath == null) throw new FileNotFoundException();
		Pfr pfr = pfrPool.get(project);
		if(pfr == null) throw new NullPointerException("Pfr is null, may be the project wasn't generated by ProjectHelper");
		File file = new File(pfr.getWorkspaceFile(),scpath.getPathName());
		return new WorkspaceInputStream(file,pfr.getScpathLock(scpath));
	}
	
	/**
	 * ��ȡ�ù����ļ�ӳ���ָ��·�����ļ��������
	 * <p> �÷������ڹ���·�����ļ�׼����֮ǰһֱ������
	 * <br>���ļ�������ʱ��������������Զ������ļ���
	 * @param scpath ָ����·����
	 * @return ָ��·����Ӧ�Ĺ���·���������
	 * @throws FileNotFoundException 
	 */
	public static OutputStream getOutputStream(Project project,Scpath scpath) throws FileNotFoundException{
		return getOutputStream(project, scpath, false);
	}
	/**
	 * ��ȡ�ù����ļ�ӳ���ָ��·�����ļ��������
	 * <p> �÷������ڹ���·�����ļ�׼����֮ǰһֱ������
	 * @param project ָ���Ĺ��̡� 
	 * @param scpath ָ����·����
	 * @param autoCreateFile �Ƿ��Զ����������ڵ��ļ���
	 * @return ָ��·����Ӧ�Ĺ���·���������
	 * @throws FileNotFoundException �ļ�û�ҵ�ʱ�׳����쳣��
	 */
	public static OutputStream getOutputStream(Project project, Scpath scpath,boolean autoCreateFile) throws FileNotFoundException{
		if(scpath == null) throw new FileNotFoundException();
		
		Pfr pfr = pfrPool.get(project);
		if(pfr == null) throw new IllegalArgumentException("Pfr is null, may be the project wasn't generated by ProjectHelper");
		
		//TODO ����Ӧ�ü���һЩ�й����첽���Ƶ��жϡ�
		
		File file = new File(pfr.getWorkspaceFile(),scpath.getPathName());
		return new WorkspaceOutputStream(file,pfr.getScpathLock(scpath),autoCreateFile);
	}
	
	/**
	 * ��ȡ�ù����ļ�ӳ���ָ��·�����ļ��������
	 * <p> �÷���������Project����ʲô״̬��������������������ã���Ϊ����ܻᵼ��Ǳ�ڵ�������
	 * @param project ָ���Ĺ��̡� 
	 * @param scpath ָ����·����
	 * @param autoCreateFile �Ƿ��Զ����������ڵ��ļ���
	 * @return ָ��·����Ӧ�Ĺ���·���������
	 * @throws FileNotFoundException �ļ�û�ҵ�ʱ�׳����쳣��
	 */
	public static OutputStream getOutputStreamWC(Project project, Scpath scpath,boolean autoCreateFile) throws FileNotFoundException{
		if(scpath == null) throw new FileNotFoundException();
		Pfr pfr = pfrPool.get(project);
		if(pfr == null) throw new IllegalArgumentException("Pfr is null, may be the project wasn't generated by ProjectHelper");
		File file = new File(pfr.getWorkspaceFile(),scpath.getPathName());
		return new WorkspaceOutputStream(file,pfr.getScpathLock(scpath),autoCreateFile);
	}
	
	/**
	 * Ϊĳ����������һ���µ�Scpath·����
	 * <p>���ɵ�Scpath����������ɣ�ǰ׺+���ɴ���+��׺��
	 * @param project ָ���Ĺ��̡�
	 * @param prefix ����·����ǰ׺��һ����˵�˴�Ӧ����дָ�����ļ��У���"docs\\"
	 * @param suffix ����·���ĺ�׺��һ����˵�˴�Ӧ����д�ļ��ĺ�׺������".rtf";
	 * @return �µ�Scpath·����
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
		CT.trace("Ϊ��ǰ��Ŀ�����µ��ļ����ӣ�Ϊ" + scpath.getPathName());
		File file = new File(pfr.getWorkspaceFile(),scpath.getPathName());
		File parent = file.getParentFile();
		if(parent != null && !parent.exists()) parent.mkdirs();
		try{
			if(!file.exists()){
				CT.trace("��·���Ĺ���Ŀ¼ӳ�䲻���ڣ������ļ���...");
				file.createNewFile();
				CT.trace("�ڹ���·���³ɹ��Ĵ����ļ�");
			}
		}catch (Exception e) {
			e.printStackTrace();
			CT.trace("�޷��ڹ���·���´����ļ�");
		}
		return scpath;
	}

	/**
	 * ����һ��Ĭ�ϵĹ��̡�
	 * @param file ���ļ���Ӧ���ļ���
	 * @return Ĭ�ϵĹ��̡�
	 * @throws Exception ���̴���ʧ�ܡ�
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
	 * ��ȡָ��·���Ĺ�����Ϣ��
	 * @param file ָ����ѹ����·����
	 * @param type ��ȡ�ķ�ʽ��ֻ��Ϊ{@linkplain ProjectHelper#FOREGROUND} �� {@linkplain ProjectHelper#BACKGROUND}�е�һ����
	 * @return ����ѹ���ļ�·����ȡ���Ĺ����ļ���
	 * @throws IOException ͨ���쳣��
	 * @throws UnhandledVersionExcepion �汾�쳣�� 
	 * @throws StructFailedException �ṹ�쳣��
	 * @throws ProjectPathNotSuccessException ·�����ɹ��쳣��
	 */
	public static Project loadProject(File file,Operate type) throws 
		IOException, 
		UnhandledVersionException, 
		StructFailedException, 
		ProjectPathNotSuccessException{
	
		//�ж��ļ�Ϊ�ǿ�
		if(file == null) throw new NullPointerException("File can't be null");
		//����ZipFile������������Σ����ļ���Ҫ���رա�
		ZipFile zipFile = null;
	
		try{
			try{
				//��ZipFile��ֵ
				zipFile = new ZipFile(file);
			}catch(IOException e){
				IOException ie = new IOException("Failed to link with Sch");
				ie.setStackTrace(e.getStackTrace());
				throw ie;
			}
			
			//��ȡ�汾�ţ�����ȡ��Ӧ��W2SF
			W2SF w2sf = null;
			try {
				w2sf = getW2sf(getZipVersion(zipFile));
			} catch (UnhandledVersionException e) {
				throw e;
			}
			
			//ʹ����Ӧ��W2SF��������Project
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
			
			//����Project��Pfr��ӳ���ϵ
			pfrPool.put(project, pfr);
			filePool.put(project, file);
			
			/*
			 * 
			 *  ֱ����ʱ���÷���һֱ�����߳����У���֮��Ķ�ȡ�������type���������ж�
			 * 
			 */
			if(type == Operate.FOREGROUND){
				//����ɹ���ʧ���б�
				List<Scpath> successList = new ArrayList<Scpath>();
				List<Scpath> failedList = new ArrayList<Scpath>();
				//ѭ����ѹ���е�·����ָʾ�ļ���
				for(Scpath scpath:project.getScpaths()){
					try{
						w2sf.unzipFile(project, zipFile, scpath);
						successList.add(scpath);
					}catch(Exception e){
						failedList.add(scpath);
					}
				}
				//�ж��Ƿ���ʧ�ܵĽ�ѹ�ļ�
				if(failedList.size() != 0){
					throw new ProjectPathNotSuccessException(project, successList, failedList, FailedType.LOAD);
				}
			}else{
				//TODO �����Ӻ�̨ѹ��������
			}
			
			//���һ��˳������˳������Project
			return project;
			
		}finally{
			if(zipFile != null){
				try{
					zipFile.close();
				}catch(IOException e){
					e.printStackTrace();
					CT.trace("����ĳЩԭ��ZIP�ļ�û�гɹ��ı��ر�");
				}
			}
		}
	}
	
	/**
	 * ��ָ���Ĺ����ļ���ָ���İ汾����ָ���Ĵ洢�����洢��ָ����ѹ���ļ��С�
	 * @param project ָ���Ĺ��̡�
	 * @param version ָ���İ汾�š�
	 * @param type ������ʽ��ֻ��Ϊ{@linkplain Operate#FOREGROUND} �� {@linkplain Operate#BACKGROUND}�е�һ����
	 * @throws IOException IO�쳣��
	 * @throws UnhandledVersionException �汾�쳣�� 
	 * @throws StructFailedException  �ṹ�쳣��
	 * @throws ProjectPathNotSuccessException ·�����ɹ��쳣��
	 * @throws IllegalStateException �Ƿ�״̬�쳣��
	 */
	public static void saveProject(Project project,String version,Operate type) throws 
		IOException, 
		UnhandledVersionException, 
		StructFailedException, 
		ProjectPathNotSuccessException,
		IllegalStateException{
		
		//����ѹ���ļ������
		ZipOutputStream zout = null;
		try{
			//�����жϸù����Ƿ���ڶ�Ӧ��pfr
			if(pfrPool.get(project) == null) throw new IllegalStateException("Can't get pfr,may the project has already benn disposed");
			
			try{
				//��zout��ֵ
				zout = new ZipOutputStream(new FileOutputStream(getProjectFile(project)));
			}catch(IOException e){
				IOException ie = new IOException("Failed to link with Sch");
				ie.setStackTrace(e.getStackTrace());
				throw ie;
			}

			W2SF w2sf = null;
			try{
				//��ȡ��Ӧ�汾��W2SF����
				w2sf = ProjectHelper.getW2sf(version);
			}catch(UnhandledVersionException e){
				throw e;
			}
			
			//����д���ļ��ṹ��
			try{
				w2sf.saveStruct(project, zout);
			}catch(Exception e){
				throw new StructFailedException();
			}
			
			/*
			 * 
			 *  ֱ����ʱ���÷���һֱ�����߳����У���֮��Ķ�ȡ�������type���������ж�
			 * 
			 */
			if(type == Operate.FOREGROUND){
				//����ɹ���ʧ���б�
				List<Scpath> successList = new ArrayList<Scpath>();
				List<Scpath> failedList = new ArrayList<Scpath>();
				//ѭ��ѹ�������ļ�
				for(Scpath scpath:project.getScpaths()){
					try{
						w2sf.zipFile(project, scpath, zout);
						successList.add(scpath);
					}catch(Exception e){
						failedList.add(scpath);
					}
				}
				//�ж��Ƿ���ʧ�ܵ�ѹ���ļ�
				if(failedList.size() != 0){
					throw new ProjectPathNotSuccessException(project, successList, failedList, FailedType.SAVE);
				}
			}else{
				//TODO �����Ӻ�̨ѹ��������
			}
			
		}finally{
			if(zout != null){
				try{
					zout.close();
				}catch(IOException e){
					e.printStackTrace();
					CT.trace("����ĳЩԭ�������û�йر�");
				}
			}
		}
		
	}
	/**
	 * ��ָ���Ĺ����ļ��������µİ汾��ָ���Ĵ洢�����洢��ָ����ѹ���ļ��С�
	 * @param project ָ���Ĺ��̡�
	 * @param type ������ʽ��ֻ��Ϊ{@linkplain Operate#FOREGROUND} �� {@linkplain Operate#BACKGROUND}�е�һ����
	 * @throws StructFailedException �ṹ�쳣��
	 * @throws UnhandledVersionException �汾�쳣��
	 * @throws IOException IO�쳣��
	 * @throws ProjectPathNotSuccessException ·��ʧ���쳣��
	 * @throws IllegalStateException ״̬�쳣��
	 */ 
	public static void saveProject(Project project ,Operate type) throws 
		IOException, 
		UnhandledVersionException,
		StructFailedException, 
		ProjectPathNotSuccessException{
		saveProject(project, LAST_ZIP_VERSION, type);
	}
	
	/**
	 * �ͷ�ָ���Ĺ����ļ���
	 * <p> �÷������жϹ��̺��ļ����Լ�����ӳ��ص���ϵ������ͼɾ���乤��·����һ���ļ���
	 * <br> �÷����ᾡһ��Ŭ��ȥ�ͷ��ļ���
	 * @param project ָ���Ĺ����ļ���
	 * @throws ProjectCloseException 
	 * @throws NullPointerException ��ڲ���Ϊ�յ�ʱ���׳��쳣��
	 */
	public static void disposeProject(Project project) throws ProjectCloseException{
		if(project == null) throw new NullPointerException("Project can't be null");
		filePool.remove(project);
		Pfr pfr = pfrPool.remove(project);
		if(pfr == null) throw new ProjectCloseException(project, null,"Pfr is null, may be the project wasn't generated by ProjectHelper");
		FileFunc.deleteFile(pfr.getWorkspaceFile());
	}
	
	/**
	 * ����ָ�����̴洢�ļ����ļ��汾��
	 * <p>ע�⣬�÷�������ر���ڲ���ZipFile���÷���һ����϶�ȡ�ʹ洢һ��ʹ�ã�����ʹ��ʱ��Ҫ������ṩZipFile�Ĺرշ�����
	 * @param file ָ�����ļ���
	 * @return �ļ��İ汾��
	 */
	private static String getZipVersion(ZipFile file){
		
		try{
			//�ҵ�version.xml��version.xml��λ������ʲô�汾���ǹ̶��ģ���ԭʼ�İ汾û�С�
			//version.xml�Ǽ�¼�汾��Ϣ��xml�ļ������۰汾��Σ����ļ�λ�ò��䡣
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
	 * ����ָ���汾��Ӧ��ͨ�ŷ�����
	 * @param version ָ���İ汾��
	 * @return ��汾��Ӧ��ͨ�ŷ�����
	 * @throws UnhandledVersionException �������޷������İ汾��
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
		// ��ֹ�ⲿʵ�������ࡣ
	}

}























/**
 * ���ඨ���˹������ļ���ӳ�䣬�ṩ�˹������ļ����������з�����
 * @author DwArFeng��
 * @since 1.8��
 */
class Pfr{
	
	/**����·��*/
	private final File workSpaceFile;
	/**����·��������*/
	private final Map<Scpath, ReadWriteLock> scpathLockPool;
	
	/**
	 * ���ɱ�׼�Ĺ����ļ�ӳ�䡣
	 * @throws IOException �����ɹ����ļ�ӳ��ʱ���ļ����������쳣ʱ�׳����쳣��
	 */
	public Pfr() throws IOException{
		//���ɹ���·����
		this.workSpaceFile = genWorkspaceFile();
		//��������
		scpathLockPool = new HashMap<Scpath, ReadWriteLock>();
		
	}
	
	/**
	 * ��ȡ�ù����ļ�ӳ���еĹ���Ŀ¼��ָʾ���ļ���
	 * @return ����Ŀ¼��ָʾ���ļ���
	 */
	public File getWorkspaceFile(){
		return this.workSpaceFile;
	}
	
	/**
	 * ��ȡָ��·���ڸù����ļ�ӳ���е�ͬ������
	 * <b> �����֮ǰ�����ļ�ӳ���е�������û�ж�Ӧ��·����������ᴴ���������������ӶԸ�����ӳ�䡣
	 * <b> ֻҪ·����������ͬ���ͻ���ͬһ������
	 * @param scpath ָ����·����
	 * @return ��ָ����·�����Ӧ��ͬ������
	 */
	public ReadWriteLock getScpathLock(Scpath scpath){
		scpathLockPool.putIfAbsent(scpath, new ReentrantReadWriteLock());
		return scpathLockPool.get(scpath);
	}
	
	/**
	 * �ڹ���Ŀ¼������һ���ļ�����Ϊ������̵Ĺ���Ŀ¼�ļ��С�
	 * @return ָ����Ŀ¼�Ĺ���Ŀ¼�ļ���
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
		//������������Ŀ¼���򴴽�����
		file.mkdirs();
		return file;
	}
}





/**
 * �����ļ���������
 * <p> ������������ͳ�����������ṩ�˶��̵߳İ�ȫ�ԡ�������Ϊ{@linkplain InputStream}����
 * �������{@linkplain ProjectHelper#getInputStream(Project, Scpath)} ʱ���ء�
 * @author DwArFeng
 * @since 1.8
 */
class WorkspaceInputStream extends InputStream{
	
	private FileInputStream fin;
	private ReadWriteLock lock;
	
	
	/**
	 * ����һ������ָ��·���Ĺ����ļ���������
	 * <p>����ڳ�ʼ��ʱ����ͨ�����⣬��������ȷ�ı��ͷš�
	 * @param file ָ����·����
	 * @throws FileNotFoundException ��·���ļ�������ʱ�׳����쳣��
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
					CT.trace("����ĳЩԭ���ļ�������û�гɹ��Ĺر�");
				}
			}
			if(e instanceof FileNotFoundException) throw (FileNotFoundException) e;
		}

	}

	/**
	 * �رո�����
	 * <p>��ʹ�����ر�ʱ����ͨ���쳣����Ҳ�ܱ���ȷ���ͷš�
	 * @throws IOException ͨ���쳣��
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
 * ����·���������
 * ����ͳ��������������˶��̵߳İ�ȫ�ԣ�������Ϊ{@linkplain OutputStream}��
 * �ڳ������{@linkplain ProjectHelper#getOutputStream(Project, Scpath)} ʱ���ء�
 * @author DwArFeng��
 * @since 1.8��
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
					CT.trace("����ĳЩԭ���ļ������û�гɹ��Ĺر�");
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
 * ���幤�̹���Ŀ¼���빤��ѹ�����ĵ���֮�������ͨ�ŷ�����
 * <p> �÷��������� Workspace to sch file function��ȡ����ĸ������F�����ظ���ֻȡһ��F��
 * <br> �ýṹ�ķ����ɲ�ͬ�汾�Ķ�ȡ���ֱ�ʵ�֡� 
 * <br> ��ͬ�汾�Ķ�ȡ������������ "W" + versionName.replace('.','_');
 * @author DwArFeng��
 * @since 1.8��
 */
interface W2SF{
	
	/**
	 * ��ѹ���ļ��ж�ȡ���̵ĹǼ���Ϣ��
	 * @param file ָ����ѹ���ļ���
	 * @return �����ļ��ĹǼ���Ϣ������Ĺ��̡�
	 * @throws DocumentException XML�ṹ�쳣��
	 * @throws IOException ͨ���쳣��
	 * @throws ZipException ѹ���ļ��쳣��
	 */
	public Project loadStruct(ZipFile file) throws ZipException, IOException, DocumentException;

	/**
	 * ��ѹ���ļ��������д�����̵ĹǼ��ļ���
	 * @param project ָ���Ĺ����ļ���
	 * @param zout ѹ�����������
	 * @throws IOException ͨ���쳣��
	 */
	public void saveStruct(Project project,ZipOutputStream zout) throws IOException;
	
	/**
	 * ��ָ����·���н�ѹָ���ļ���
	 * @param project ��ѹ��صĹ��̶���
	 * @param file ���ѹ�йص�ѹ���ļ���
	 * @param scpath ��ѹ��صĹ���Ŀ¼·����
	 * @throws IOException ͨ���쳣��
	 * @throws ZipException ѹ���ļ��쳣��
	 */
	public void unzipFile(Project project,ZipFile file,Scpath scpath) throws ZipException, IOException;
	
	/**
	 * ��ָ����Zip���������ָ�����ļ���
	 * @param pfr ��ѹ���йصĹ����ļ�ӳ�䡣
	 * @param zout ��ѹ���йصġ�
	 * @param scpath ָ���Ĺ���Ŀ¼��
	 * @throws IOException ͨ���쳣��
	 */
	public void zipFile(Project project, Scpath scpath, ZipOutputStream zout) throws IOException;
	
	/**
	 * ���ð汾��Scpath���չ���ת��Ϊ����汾��Scpath��
	 * <p> ע�⣬����Ҫ��֤ {@code scpath.equals(getScpathThisVersion(getScpathLastVersion(scpath)));}
	 * ��Ҳ����˵��Scpathת��Ϊ���µİ汾֮����ת��Ϊ�ð汾�����õ���Scpath������ת��ǰ��һ�¡�
	 * @param scpath ָ���Ĺ���Ŀ¼·����
	 * @return ת��������°汾�Ĺ���Ŀ¼·����
	 */
	public Scpath getScpathLastVersion(Scpath scpath);
	/**
	 * �����°汾��Scpathת��Ϊ����汾��Scpath��
	 * <p> ע�⣬����Ҫ��֤ {@code scpath.equals(getScpathThisVersion(getScpathLastVersion(scpath)));}
	 * ��Ҳ����˵��Scpathת��Ϊ���µİ汾֮����ת��Ϊ�ð汾�����õ���Scpath������ת��ǰ��һ�¡�
	 * @param scpath ָ�������°汾�Ĺ���Ŀ¼·����
	 * @return ����汾�Ĺ���Ŀ¼·����
	 */
	public Scpath getScpathThisVersion(Scpath scpath);
}























/**
 * ������һЩ��{@linkplain W2SF} ʹ�õĹ���������
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
	 * @throws ZipException ѹ���ļ��쳣��
	 */
	static Project loadStruct0_0_0(ZipFile file) throws ZipException, IOException, DocumentException{
		//��ȡ�ļ�
		InputStream tagIn = file.getInputStream(file.getEntry("tags.xml"));
		SAXReader tagReader = new SAXReader();
		Element tagElement = null;
		tagElement = (Element) tagReader.read(tagIn).getRootElement();
		
		InputStream notebooksIn = file.getInputStream(file.getEntry("notebooks.xml"));
		SAXReader notebooksReader = new SAXReader();
		Element notebooksElement = notebooksReader.read(notebooksIn).getRootElement();
		
		//�����ļ�
		TagMap tagMap = unconstructTagsXML0_0_0(tagElement);
		NotebookCol notebookCol = unconstructNotebooks0_0_0(notebooksElement);
		return new Project.Productor().tagMap(tagMap).notebookCol(notebookCol).product();
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
		Document notesbooksXML = DocumentHelper.createDocument(constructNotebooksXML0_0_0(
				(NotebookCol) project.getChildFrom(Project.ChildType.NOTEBOOK_COL))
		);
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
	 * ��ָ���Ĺ���·���н�ѹ�ļ���
	 * <p> ���ð汾��0.0.0��0.1.0
	 * @param project ������Ĺ��̡�
	 * @param file �������Zip�ļ���
	 * @param scpath ָ���Ĺ���·����
	 * @throws ZipException Zip�ļ��쳣��
	 * @throws IOException IOͨ���쳣��
	 */
	static void unzipFile0_0_0(Project project, ZipFile file, Scpath scpath) throws ZipException, IOException {
		
		CT.trace("���ڽ�ѹ�ļ���" + scpath + "\t�߳�������" + Thread.currentThread());
		OutputStream wout = null;
		try{
			//���ɹ���·���������
			wout = ProjectHelper.getOutputStreamWC(project, scpath,true);
			
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
	 * <p> ʹ�õİ汾��0.0.0��0.1.0
	 * @param project ��صĹ��̡�
	 * @param zout ָ����zip�������
	 * @param scpath ָ���Ĺ���·����
	 * @throws IOException  ͨ���쳣��
	 */
	static void zipFile0_0_0(Project project, Scpath scpath, ZipOutputStream zout) throws IOException {
		
		CT.trace("����ѹ���ļ���" + scpath + "\t�߳�������" + Thread.currentThread());
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
					CT.trace("����ĳЩԭ��������û�������ر�");
				}
			}
		}
		
	}
	
	/**
	 * ��ָ����Scpathת��Ϊ���°汾��Scpath��
	 * <p> ���ð汾��0.0.0��0.1.0
	 * @param scpath ָ����Scpath��
	 * @return ת��������°汾��Scpath��
	 */
	static Scpath getScpathLastVersion0_0_0(Scpath scpath) {
		return scpath;
	}
	
	/**
	 * �����°汾��Scpathת��Ϊָ����Scpath��
	 * <p>���ð汾��0.0.0,0.1.0
	 * @param scpath ���°汾��Scpath��
	 * @return ָ���汾��Scpath��
	 */
	static Scpath getScpathThisVersion0_0_0(Scpath scpath) {
		return scpath;
	}
	
	/**
	 * ��ȡ�ļ��ṹ�������ļ��ķ�����
	 * <p> ���ð汾��0.1.0
	 * @param file ָ����ѹ���ļ���
	 * @return ���ɵĹ��̡�
	 * @throws DocumentException XML�ṹ�쳣��
	 * @throws IOException  IOͨ���쳣��
	 * @throws ZipException ѹ���ļ��쳣��
	 */
	static Project loadStruct0_1_0(ZipFile file) throws ZipException, IOException, DocumentException{
		//��ȡ�ļ�
		InputStream tagIn = file.getInputStream(file.getEntry("tags.xml"));
		SAXReader tagReader = new SAXReader();
		Element tagElement = null;
		tagElement = (Element) tagReader.read(tagIn).getRootElement();
		
		InputStream notebooksIn = file.getInputStream(file.getEntry("notebooks.xml"));
		SAXReader notebooksReader = new SAXReader();
		Element notebooksElement = notebooksReader.read(notebooksIn).getRootElement();
		
		//�����ļ�
		TagMap tagMap = unconstructTagsXML0_0_0(tagElement);
		NotebookCol notebookCol = unconstructNotebooks0_1_0(notebooksElement);
		return new Project.Productor().tagMap(tagMap).notebookCol(notebookCol).product();
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
		Document notesbooksXML = DocumentHelper.createDocument(constructNotebooksXML0_1_0(
				(NotebookCol) project.getChildFrom(Project.ChildType.NOTEBOOK_COL))
		);
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
	 * @param tagMap ָ����ID-��ǩӳ�����
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
		return new NotebookCol(notebooks);
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
		return new NotebookCol(notebooks);
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
	 * �ӱʼǱ��й����й�Notebook��XML��Ϣ��
	 * <p> ���õİ汾��0.0.0
	 * @param notebook ָ���ıʼǱ���
	 * @return �����XML��Ϣ��
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
	 * ��ָ����XMLԪ���н���Notebook����
	 * <p> ���õİ汾��0.1.0
	 * @param element ָ����XMLԪ�ء�
	 * @return ������Notebook����
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
	 * �ӱʼǱ��й����й�Notebook��XML��Ϣ��
	 * <p> ���õİ汾��0.0.0
	 * @param notebook ָ���ıʼǱ���
	 * @return �����XML��Ϣ��
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
	 * �ӱʼ��й�����Ӧ��Note��XML��Ϣ��
	 * <p> ���õİ汾��0.0.0
	 * @param note ָ���ıʼǶ���
	 * @return �����XML��Ϣ��
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
	 * ��ָ����XMLԪ���н���Note����
	 * <p> ���õİ汾��0.1.0
	 * @param element ָ����XMLԪ�ء�
	 * @return �����Ķ���
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
	 * �ӱʼ��й�����Ӧ��Note��XML��Ϣ��
	 * <p> ���õİ汾��0.1.0
	 * @param note ָ���ıʼǶ���
	 * @return �����XML��Ϣ��
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
		//��ֹ�ⲿʵ����
	}
}