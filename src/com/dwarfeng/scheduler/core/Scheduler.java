package com.dwarfeng.scheduler.core;

import java.io.File;
import java.util.WeakHashMap;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import com.dwarfeng.dwarffunction.io.CT;
import com.dwarfeng.scheduler.gui.JCrashFrame;
import com.dwarfeng.scheduler.gui.JProjectTree;
import com.dwarfeng.scheduler.gui.JSplashWindow;
import com.dwarfeng.scheduler.gui.SchedulerGui;
import com.dwarfeng.scheduler.info.AppearanceInfo;
import com.dwarfeng.scheduler.info.FileInfo;
import com.dwarfeng.scheduler.io.ConfigHelper;
import com.dwarfeng.scheduler.io.ProjectIoHelper;
import com.dwarfeng.scheduler.project.Project;
import com.dwarfeng.scheduler.tools.ProjectOperationHelper;
import com.dwarfeng.scheduler.typedef.abstruct.ObjectInProjectTree;
import com.dwarfeng.scheduler.typedef.exception.ProjectCloseException;
import com.dwarfeng.scheduler.typedef.exception.ProjectPathNotSuccessException;

/**
 * �ƻ��������
 * <p> �ó������Ҫ�����ǽ���ƻ��Ĺ滮�����Լ��ʼ���������⡣�ó�����ҪΧ��������������б�д��
 * <br>&nbsp;&nbsp; 1.�ʼǼ�¼�Լ����������˲������������е��е��Ʊʼǵȱʼǹ��������������������ĳЩ���ܡ�
 * <br>&nbsp;&nbsp; 2.�������ͨ�������������ָ���ļƻ����񣬳������ϵͳʱ��������Щ������й���
 * <p>����Ϊ���������࣬��ѭ�������ģʽ��ͬʱ�������������������������Ϳ�����������
 * <p> �����е����з����������̰߳�ȫ�ģ�����Ҫ���̵߳��ã���ʹ���ⲿͬ����
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
		private boolean showSplash = true;
		
		/**
		 * ��ʼ��������
		 */
		public Luncher(){
			//TODO���Ժ��ڴ��������ʱ��Ҫ�Ĳ�����
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
		 * ���ó�������ʱ�Ƿ���ʾ���ִ��塣
		 * @param val <code>true</code>Ϊ��ʾ���ִ��壬��֮����ʾ���ִ��塣
		 * @return ����������
		 */
		public Luncher showSplash(boolean val){
			this.showSplash = val;
			return this;
		}
		
		/**
		 * ��������
		 * <p> ������������������Σ�������׳�{@linkplain IllegalStateException}��
		 */
		public void lunch(){
			if(getInstance() != null) throw new IllegalStateException("Scheduler has already run");
			instance = new Scheduler(workspacePath);
			instance.init(showSplash);
		}
		
	}
	
	/**
	 * ���ظ����Ψһʵ����
	 * <p> ʵ��ֻ���ڳ������л������ʱ�򱻷��ء���ͼ�ڳ���δ������������ʱ���ô˷����ᵼ��{@linkplain IllegalStateException}�쳣��
	 * @return �����Ψһʵ����
	 * @throws IllegalStateException �ڲ���ȷ��״̬�µ��ô˷����׳����쳣��
	 */
	public static Scheduler getInstance(){
		return instance;
	}
	
	/**
	 * ��ʾ�����״̬��
	 * @author DwArFeng
	 * @since 1.8
	 */
	public enum State{
		/**�������δ�����ͳ�ʼ�����ֶ�*/
		RAW("waiting for start"),
		/**������������������ֶ�*/
		INIT("initializing"),
		/**��������������е��ֶ�*/
		RUN("running"),
		/**�������������ֶ�*/
		CRASHED("crashed");
		
		private final String label;
		
		private State(String label){
			this.label = label;
		}
		
		/*
		 * (non-Javadoc)
		 * @see java.lang.Enum#toString()
		 */
		@Override
		public String toString(){
			return this.label;
		}
	}
	/**���б�־���ñ�־�ڳ���������ʱ������Ϊtrue,֮���ٸ���*/
	private State state = State.RAW;
	
	/**�ƻ��������Ψһʵ�����*/
	private static Scheduler instance;
	
	/*
	 * 
	 * �����ǳ�Ա������
	 * 
	 */
	/**���汾�ţ�������ʾ�ڳ����������������ĵط�*/
	private final String longVersion = "alpha_0.3.1_20160702_build_A";
	/**�̰汾�ţ�������ʾ�ڱ���λ��*/
	private final String shortVersion = "alpha_0.3.1";
	/**Ĭ�ϵĹ���·��*/
	private final String workspacePath;
	/**Ĭ�ϵĹ����ĵ�Ŀ¼*/
	private final String archivePath = "archive\\";
	/**����������ļ����ڵ�Ŀ¼*/
	private final String configPath = "config" + File.separator;
	/**�洢JTree�����ӳ��*/
	private final WeakHashMap<JProjectTree, Object> projectTreeMap = new WeakHashMap<JProjectTree, Object>();
	
	
	/**����һ���µļƻ�����������*/
	private Scheduler(String workspacePath){
		this.workspacePath = workspacePath;
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
	 * ���س����״̬��
	 * @return �����״̬ö�١�
	 */
	public State getState(){
		return state;
	}

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
	 * ��ȡ����ָ������µĹ���·����
	 * @return ����ָ������µĹ���·����
	 */
	public String getLastPath(){
		return this.fileInfo.getLastProjectPath();
	}

	/**
	 * ����ǰ̨�Ĺ��̡�
	 * <p> ��������Ǹ������̵Ļ�������ỹԭ֮ǰ��ǰ̨���̣��Ҳ����׳��쳣��
	 * @param project ����ǰ̨�Ĺ��̡�
	 */
	public void setFrontProject(Project project){
		if(schedulerGui == null) throw new IllegalStateException("Bad state : " + getState());
		
		Project temp = getFrontProject();
		try{
			String path = project == null ? null : ProjectIoHelper.getProjectFile(project).getAbsolutePath();
			this.fileInfo = new FileInfo.Productor().lastProjectPath(transPath(path)).product();
			this.frontProject = project;
		}catch(Exception e){
			e.printStackTrace();
			CT.trace("����ĳЩԭ�򣬲�������ָ���Ĺ��̵�ǰ̨�����ܵ�ԭ���ǹ�����");
			String path = temp == null ? null : ProjectIoHelper.getProjectFile(temp).getAbsolutePath();
			this.fileInfo = new FileInfo.Productor().lastProjectPath(transPath(path)).product();
			this.frontProject = temp;
		}
		schedulerGui.refreshData();
	}
	
	/**
	 * �������йط�����
	 * TODO �÷������ڼ������ơ�
	 */
	public void crash(){
		
		AppearanceInfo appearanceSet = null;
		try{
			if(frontProject != null){
				ProjectIoHelper.saveProject(frontProject, getLastPath(),ProjectIoHelper.Operate.FOREGROUND);
			}
			if(schedulerGui != null){
				appearanceSet = schedulerGui.getAppearanceInfo();
				schedulerGui.dispose();
				schedulerGui = null;
			}
			if(jSplashWindow != null){
				jSplashWindow.dispose();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		jCrashFrame = new JCrashFrame(appearanceSet);
		state = State.CRASHED;
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
		
		//TODO ��Ҫ�Բ�ͬ״̬�����жϡ�
		ConfigHelper.saveApperanceInfo(schedulerGui.getAppearanceInfo());
		ConfigHelper.saveFileInfo(fileInfo);
		try{
			for(Project project : ProjectIoHelper.getAssociatedProjects()){
				Project front = Scheduler.getInstance().getFrontProject();
				if(project != front){
					ProjectOperationHelper.forceDisposeEditor(project);
					ProjectOperationHelper.saveProject(project);
					ProjectOperationHelper.closeProject(project);
				}else{
					if(!ProjectOperationHelper.disposeEditor(project,0)){
						JOptionPane.showMessageDialog(
								schedulerGui, 
								"һ�������༭���˳�ʱ�����쳣�������˳���ֹ\n"
								+ "�����Ʊ�������", 
								"������ֹ", 
								JOptionPane.INFORMATION_MESSAGE, 
								null
						);
						//��ֹ�˳�����
						return;
					}else{
						ProjectOperationHelper.saveProject(project);
						ProjectOperationHelper.closeProject(project);
					}
				}
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
					ProjectOperationHelper.closeProject(frontProject);
				}finally{
					System.exit(1);
				}
			}else{
				e.printStackTrace();
			}
		}
	}
	

	/**
	 * �Գ�����������<code>project</code>�йصĹ��������и��£�����չ����ѡ��ָ���Ľڵ㡣
	 * @param project �йصĹ��̡�
	 * @param expand ָ���Ľڵ㡣
	 */
	//XXX �Ƿ��װΪ�ӿڴ����ۡ�
	public void refreshProjectTrees(Project project,ObjectInProjectTree expand){
		
		if(project == null) throw new NullPointerException("Project can't be null");
		
		for(JProjectTree tree:projectTreeMap.keySet()){
			if(project.equals(tree.getProject())){
				tree.refresh(expand);
			}
		}
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
		
	/**����ĳ�ʼ������*/
	private void init(boolean showSplash){
		state = State.INIT;
		RunnerQueue.invoke(new Runnable() {
			
			@Override
			public void run() {
				try{
					//��¼��ʼʱ��
					long l = - System.currentTimeMillis();
					//��¼������Ϣ
					Project project = null;
					//���������ʽ
					UIManager.setLookAndFeel(new NimbusLookAndFeel());
					
					//�������ִ���
					if(showSplash){
						jSplashWindow = new JSplashWindow();
						jSplashWindow.setVisible(true);	
					}
					
					//�����ʼ��
					if(showSplash) jSplashWindow.setMessage("���ڳ�ʼ������");
					schedulerGui = new SchedulerGui(ConfigHelper.loadAppearanceInfo());
					projectTreeMap.put(schedulerGui.getProjectTree(),null);
					
					//��ȡ������Ϣ
					if(showSplash) jSplashWindow.setMessage("���ڶ�ȡ�ļ���Ϣ");
					fileInfo = ConfigHelper.loadFileInfo();
					
					if(showSplash) jSplashWindow.setMessage("��ȡ������Ϣ...");
					//��������Ĵ򿪹��̣����������
					if(fileInfo.getLastProjectPath() != null){
						if(showSplash) jSplashWindow.setMessage("����һ������...");
						try{
							CT.trace("���ڴ���һ������");
							project = ProjectIoHelper.loadProject(new File(fileInfo.getLastProjectPath()), ProjectIoHelper.Operate.FOREGROUND);
							CT.trace("���̴򿪳ɹ�");
						}catch(Exception e){
							if(e instanceof ProjectPathNotSuccessException){
								try{
									ProjectIoHelper.closeProject(((ProjectPathNotSuccessException) e).getProject());
								}catch(ProjectCloseException e1){
									e1.printStackTrace();
								}
							}
							frontProject = null;
							e.printStackTrace();
							CT.trace("�ڶ�ȡ�ļ�ʱ�������⣬��ȡ����ǰ�ļ����Զ���ȡ");
						}
					}
					//�ػ����
					setFrontProject(project);
					if(showSplash) jSplashWindow.setMessage("���ƽ���...");
					schedulerGui.refreshData();
					//ȷ�����ִ�������ʱ��ﵽ2000ms XXX �ò�����������Ϊ�ɱ��
					if(showSplash){
						l += System.currentTimeMillis();
						if(l > 0 && l < 2000) Thread.sleep(2000-l);
					}
					//�ͷ����ִ���
					if(showSplash) jSplashWindow.dispose();
					//��ʾ����
					schedulerGui.setVisible(true);
					schedulerGui.setAlwaysOnTop(true);
					schedulerGui.setAlwaysOnTop(false);
					state = State.RUN;
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
