package com.dwarfeng.scheduler.core;

import java.io.File;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import com.dwarfeng.func.io.CT;
import com.dwarfeng.scheduler.gui.JCrashFrame;
import com.dwarfeng.scheduler.gui.JSplashWindow;
import com.dwarfeng.scheduler.gui.SchedulerGui;
import com.dwarfeng.scheduler.info.AppearanceInfo;
import com.dwarfeng.scheduler.info.FileInfo;
import com.dwarfeng.scheduler.io.ConfigHelper;
import com.dwarfeng.scheduler.io.ProjectHelper;
import com.dwarfeng.scheduler.project.Project;
import com.dwarfeng.scheduler.typedef.ProjectCantConstructException;
import com.dwarfeng.scheduler.typedef.abstruct.ObjectInProjectTree;
import com.dwarfeng.scheduler.typedef.funcint.Deleteable;
import com.dwarfeng.scheduler.typedef.funcint.Moveable;

/**
 * �ƻ��������
 * <p> �ó������Ҫ�����ǽ���ƻ��Ĺ滮�����Լ��ʼ���������⡣�ó�����ҪΧ��������������б�д��
 * <br>&nbsp;&nbsp; 1.�ʼǼ�¼�Լ����������˲������������е��е��Ʊʼǵȱʼǹ��������������������ĳЩ���ܡ�
 * <br>&nbsp;&nbsp; 2.�������ͨ�������������ָ���ļƻ����񣬳������ϵͳʱ��������Щ������й���
 * <p>����Ϊ���������࣬��ѭ�������ģʽ��ͬʱ�������������������������Ϳ�����������
 * @author DwArFeng
 * @since 1.8
 */
public class Scheduler {

	/**
	 * ʹ��Builder��ܹ��ɵ���������
	 * @author DwArFeng��
	 * @since 1.8��
	 */
	public static class Luncher{
		
		private String workspacePath = "workspace" + File.separator;
		
		/**
		 * ��ʼ��������
		 */
		public Luncher(){
			//TODO
		}
		
		/**
		 * ���ó���Ĭ�ϵĹ���·����
		 * @param val Ĭ�ϵĹ���·����
		 * @return ����������
		 */
		public Luncher workspacePath(String val){
			this.workspacePath = val;
			return this;
		}
		
		/**
		 * ��������
		 * <p> ������������������Σ�������׳�{@linkplain IllegalStateException}��
		 */
		public void lunch(){
			if(runFlag) throw new IllegalStateException("Scheduler has already been lunched");
			runFlag = true;
			instance = new Scheduler(workspacePath);
		}
		
	}
	
	/**
	 * ���ظ����Ψһʵ����
	 * @return �����Ψһʵ����
	 */
	public static Scheduler getInstance(){
		if(!runFlag) throw new IllegalStateException("Scheduler hasn't been lunched yet");
		return instance;
	}
	
	/**���б�־���ñ�־�ڳ���������ʱ������Ϊtrue,֮���ٸ���*/
	private static boolean runFlag = false;
	/**�ƻ��������Ψһʵ�����*/
	private static Scheduler instance;
	
	
	/*
	 * 
	 * �����ǳ�Ա������
	 * 
	 */
	/**���汾�ţ�������ʾ�ڳ����������������ĵط�*/
	private final String longVersion = "alpha_0.2.2_20160610_build_A";
	/**�̰汾�ţ�������ʾ�ڱ���λ��*/
	private final String shortVersion = "alpha_0.2.2";
	/**Ĭ�ϵĹ���·��*/
	private final String workspacePath;
	/**Ĭ�ϵĹ����ĵ�Ŀ¼*/
	private final String archivePath = "archive\\";
	/**����������ļ����ڵ�Ŀ¼*/
	private final String configPath = "config" + File.separator;
	
	
	/**����һ���µļƻ�����������*/
	private Scheduler(String workspacePath){
		this.workspacePath = workspacePath;
		init();
	}
	
	/**������*/
	private SchedulerGui schedulerGui;
	/**��������*/
	private JCrashFrame jCrashFrame;
	/**���ִ���*/
	private JSplashWindow jSplashWindow;
	/**������й���Ϣ*/
	private FileInfo fileInfo;
	/**����ʹ�õĹ���*/
	private Project frontProject;
	
	
	/**
	 * ��ȡ����Ķ̰汾��
	 * <p> �̰汾��һ�����ڱ��������С�
	 * @return ����Ķ̰汾�š�
	 */
	public String getShortVersion(){
		return this.shortVersion;
	}
	
	/**
	 * ��ȡ����ĳ��汾�š�
	 * <p> ���汾��һ�����ڳ�����������Ҫ��ϸ�汾��Ϣ�ĵط���
	 * @return ����ĳ��汾�š�
	 */
	public String getLongVersion(){
		return this.longVersion;
	}
	
	/**
	 * ��ȡ�����Ĭ���ĵ�·����
	 * <p> Ĭ���ĵ�·����ָ����Ĭ�ϵ��ĵ��洢λ�ã��������µı����򿪵��ļ��Ի�����
	 * ����ʼ�ļ��о���Ĭ�ϵ��ĵ�·����
	 * @return �����Ĭ���ĵ�·����
	 */
	public String getArchivePath(){
		return this.archivePath;
	}
	
	/**
	 * ���ظó���Ĺ���·��Ŀ¼��
	 * <p> ����Ĺ���·��Ŀ¼�Ǵ�Ź����ļ���ѹ�����ʱ�ļ��õġ�
	 * @return ����Ĺ���·��Ŀ¼��
	 */
	public String getWorkspacePath(){
		return this.workspacePath;
	}
	
	/**
	 * ���س��������·����
	 * <p> ���������·���Ǵ�ų������ۡ����á��������п��������Ե�·����
	 * @return  ���������·����
	 */
	public String getConfigPath(){
		return this.configPath;
	}
	
	
	/**
	 * ��ȡ�ó����ǰ̨���̡�
	 * @return �ó����ǰ̨���̡�
	 */
	public Project getFrontProject(){
		return this.frontProject;
	}
	
	/**
	 * ���ع��̵Ľ��档
	 * @return ���̵Ľ��档
	 */
	public SchedulerGui getGui(){
		return schedulerGui;
	}

	
	/**
	 * ����ǰ̨�Ĺ��̡�
	 * <p> ��������Ǹ������̵Ļ�������ỹԭ֮ǰ��ǰ̨���̣��Ҳ����׳��쳣��
	 * @param project ����ǰ̨�Ĺ��̡�
	 */
	public void setFrontProject(Project project){
		Project temp = getFrontProject();
		try{
			String path = project == null ? null : ProjectHelper.getProjectFile(project).getAbsolutePath();
			this.fileInfo = new FileInfo.Productor().lastProjectPath(transPath(path)).product();
			this.frontProject = project;
		}catch(Exception e){
			e.printStackTrace();
			CT.trace("����ĳЩԭ�򣬲�������ָ���Ĺ��̵�ǰ̨�����ܵ�ԭ���ǹ�����");
			String path = temp == null ? null : ProjectHelper.getProjectFile(temp).getAbsolutePath();
			this.fileInfo = new FileInfo.Productor().lastProjectPath(transPath(path)).product();
			this.frontProject = temp;
		}
		schedulerGui.refreshData();
	}

	/**
	 * ��ȡ����ָ������µĹ���·����
	 * @return ����ָ������µĹ���·����
	 */
	public String getLastPath(){
		return this.fileInfo.getLastProjectPath();
	}
	
	
	
/**
 * ת���ļ�·����
 * <p>ת���������£�
 * <br>����ļ������·���Ļ���ֱ�ӷ�������
 * <br> ����ļ��Ǿ���·���Ļ��������ļ��Ƿ��ڳ��������·��֮�ڣ������
 * ��������֮��Ӧ�����·����������ǣ��򷵻ؾ���·����
 * @param pathname �����·����
 * @return ת�����·����
 */
	private String transPath(String pathname){
		if(pathname == null) return null;
		File file = new File(pathname);
		if(file.isAbsolute()){
			String currentPath = System.getProperty("user.dir");
			if(pathname.startsWith(currentPath)){
				return pathname.substring(currentPath.length() + 1);
			}else{
				return pathname;
			}
		}else{
			return pathname;
		}
	}
	
	/**
	 * ѯ�ʲ�ɾ���������ļ��ķ�����
	 * @param deleteable
	 */
	public void requestDelete(Deleteable deleteable){
		String confirmWord = deleteable.getConfirmWord() == null ?
				"����ɾ�����̶���" + deleteable.toString() + "\n"
				+ "��ǰ�������ɻָ�" 
				: 
				deleteable.getConfirmWord();
		
		int sel = JOptionPane.showConfirmDialog(
				schedulerGui,
				confirmWord, 
				"ɾ��ȷ��", 
				JOptionPane.YES_NO_OPTION, 
				JOptionPane.INFORMATION_MESSAGE, 
				null
		);
		if(sel == JOptionPane.YES_OPTION){
			CT.trace("����ɾ������...");
			deleteable.delete();
			CT.trace("�����Ѿ��ɹ��ı�ɾ��");
		}
	
	}
	
	/**
	 * ��ָ���Ŀ��ƶ�������ͬ��֮������һλ�ķ�����
	 * <p> ����Ԫ��ǿ�Ʋ����ƶ������������Ԫ���Ѿ�λ����λ��Ҳ�����ƶ���
	 * @param moveable ָ���Ŀ��ƶ�����
	 */
	public void moveUp(Moveable moveable){
		ObjectInProjectTree parent = moveable.getParent();
		//����Ԫ��ǿ�Ʋ����ƶ��������Ƿ�ʵ��Moveable�ӿ�
		if(parent == null) return;
		int index = parent.getIndex(moveable);
		//����Ѿ��ڶ����ˣ��򲻿����ƶ�
		if(index == 0)return;
		parent.remove(moveable);
		parent.insert(moveable, index - 1);
	}
	
	/**
	 * ��ָ���Ŀ��ƶ�������ͬ��֮������һλ�ķ�����
	 * <p> ����Ԫ��ǿ�Ʋ����ƶ������������Ԫ���Ѿ�λ����λ��Ҳ�����ƶ���
	 * @param moveable ָ���Ŀ��ƶ�����
	 */
	public void moveDown(Moveable moveable){
		ObjectInProjectTree parent = moveable.getParent();
		//����Ԫ��ǿ�Ʋ����ƶ��������Ƿ�ʵ��Moveable�ӿ�
		if(parent == null) return;
		int index = parent.getIndex(moveable);
		//����Ѿ���ĩ���ˣ��򲻿����ƶ�
		if(index == parent.getChildCount()-1)return;
		parent.remove(moveable);
		parent.insert(moveable, index + 1);
	}
	
	/**
	 * �½�һ����׼�Ĺ����ĵ���
	 */
	public Project createNewProject(File file ){
		try{
			CT.trace("���ڴ������ļ�...");
			return ProjectHelper.createDefaultProject(file);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * ��ȡָ��·���Ĺ����ĵ���
	 * <p> �ڳ����ж�ȡ���̣��÷���Ҫ������{@linkplain ProjectHelper#loadProject(String, com.dwarfeng.scheduler.io.ProjectHelper.Operate)}
	 * ��Ϊ�÷������쳣�жϸ�Ϊȫ�档
	 * @param pathname ָ����·����
	 * @return ��ȡ�Ĺ��̡�
	 */
	public Project loadProject(String pathname){
		
		CT.trace("���ڶ�ȡ�ļ�");
		Project project = null;
		try{
			project = ProjectHelper.loadProject(pathname, ProjectHelper.Operate.FOREGROUND);
			CT.trace("�ļ���ȡ�ɹ�");
//			this.frontProject = project;
//			this.zipPathname = pathname;
//			this.fileInfo = new FileInfo.Productor().lastProjectPath(transPath(pathname)).product();
			
		}catch(ProjectCantConstructException e){
			//TODO ��������쳣���зǳ���ϸ���ж�
//			this.frontProject = null;
//			this.zipPathname = null;
			e.printStackTrace();
		}
		return project;
	}
	
	/**
	 * ����ָ���Ĺ��̡�
	 * <p>������λ�ñ��������µ�·���ϡ�
	 * @param project ָ���Ĺ��̡�
	 */
	public void saveProject(Project project){
		if(project == null) return;
		CT.trace("���ڽ�����ѹ���洢");
		try{
			ProjectHelper.saveProject(project,ProjectHelper.Operate.FOREGROUND);
		}catch(ProjectCantConstructException e){
			CT.trace("���̴洢ʧ��");
		}
	}
	
	/**
	 * �ͷ�ָ����������������ϵ����б༭����
	 * @param project ָ���Ĺ��̡�
	 */
	public void disposeEditor(Project project){
		if(project == null) return;
		schedulerGui.getDesktopPane().disposeEditor(project);
	}
	
	/**
	 * �رգ��ͷţ�ָ���Ĺ��̡�
	 * <p> �ù��̲���ر���������ϵı༭����Ҳ���ᱣ�湤�̣�ֻ�ǵ����Ĺرա�
	 * �������������ʹ�á�
	 * @param project ָ���Ĺ��̡�
	 */
	public void closeProject(Project project){
		CT.trace("�����ͷŹ����ļ�");
		if(project == null) return;
		try{
			ProjectHelper.disposeProject(project);
			CT.trace("�����ļ��ͷ����");
		}catch(Exception e){
			e.printStackTrace();	//TODO �����ø��õķ�ʽȥ�����Ŀǰֻ�ǵ������׳�ȥ����
			CT.trace("����ĳЩԭ�򣬹���û�������ı��ر�");
		}
	}
	
	/**
	 * �������йط�����
	 * TODO �÷������ڼ������ơ�
	 */
	public void crash(){
		AppearanceInfo appearanceSet = null;
		try{
			if(frontProject != null){
				ProjectHelper.saveProject(frontProject, getLastPath(),ProjectHelper.Operate.FOREGROUND);
			}
			if(schedulerGui != null){
				appearanceSet = schedulerGui.getAppearanceInfo();
				schedulerGui.dispose();
			}
			if(jSplashWindow != null){
				jSplashWindow.dispose();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		jCrashFrame = new JCrashFrame(appearanceSet);
		jCrashFrame.setVisible(true);
		jCrashFrame.setAlwaysOnTop(true);
		jCrashFrame.setAlwaysOnTop(false);
	}
	/**
	 * ���Թرճ���
	 * <p>�����ǰ����û�����ڽ��еĹ��̣�����������رա�
	 * <br>�����ǰ���������ڽ��еĹ��̣���������ͼ���浱ǰ�Ĺ��̣�Ȼ��رա�
	 * <br>������浱ǰ����ʱ�����쳣�������ѯ���û��Ǽ����˳����ǲ��˳���
	 */
	public void exitProgram(){
		ConfigHelper.saveApperanceInfo(schedulerGui.getAppearanceInfo());
		ConfigHelper.saveFileInfo(fileInfo);
		try{
			for(Project project : ProjectHelper.getAssociatedProjects()){
				disposeEditor(project);
				saveProject(project);
				closeProject(project);
			}
			System.exit(0);
		} catch (Exception e) {
			int sel = JOptionPane.showConfirmDialog(
					schedulerGui, 
					"�����ļ�����ʱ���������⣬�Ƿ�Ҫ�����˳���\n"
					+ "ѡ��\"��\"�˳�����\n"
					+ "ѡ��\"��\"��ֹ�������̲����������Ϣ",
					"���湤��ʱ�����쳣", 
					JOptionPane.YES_NO_OPTION, 
					JOptionPane.WARNING_MESSAGE, 
					null
			);
			if(sel == JOptionPane.YES_OPTION){
				try{
					closeProject(frontProject);
				}finally{
					System.exit(1);
				}
			}else{
				e.printStackTrace();
			}
		}
	}
	
	/**����ĳ�ʼ������*/
	private void init(){
		RunnerQueue.invoke(new Runnable() {
			
			@Override
			public void run() {
				try{
					//��¼��ʼʱ��
					long l = - System.currentTimeMillis();
					//���������ʽ
					UIManager.setLookAndFeel(new NimbusLookAndFeel());
					//�������ִ���
					jSplashWindow = new JSplashWindow();
					jSplashWindow.setVisible(true);		
					//��ȡ�ļ���Ϣ
					fileInfo = ConfigHelper.loadFileInfo();
					//�����ʼ��
					jSplashWindow.setMessage("��ʼ������...");
					schedulerGui = new SchedulerGui(ConfigHelper.loadAppearanceInfo());
					//��ȡ������Ϣ
					jSplashWindow.setMessage("��ȡ������Ϣ...");
					//��������Ĵ򿪹��̣����������
					if(fileInfo.getLastProjectPath() != null){
						Project project = null;
						jSplashWindow.setMessage("����һ������...");
						try{
							project = loadProject(getLastPath());
							setFrontProject(project);
						}catch(Exception e){
							frontProject = null;
						}
					}
					//�ػ����
					jSplashWindow.setMessage("���ƽ���...");
					schedulerGui.refreshData();
					//ȷ�����ִ�������ʱ��ﵽ2000ms XXX �ò�����������Ϊ�ɱ��
					l += System.currentTimeMillis();
					if(l > 0 && l < 2000) Thread.sleep(2000-l);
					//�ͷ����ִ���
					jSplashWindow.dispose();
					//��ʾ����
					schedulerGui.setVisible(true);
					schedulerGui.setAlwaysOnTop(true);
					schedulerGui.setAlwaysOnTop(false);
				}catch(Exception e){
					//���ñ�������
					crash();
					//�������
					e.printStackTrace();
				}
			}
		});
	}
}
