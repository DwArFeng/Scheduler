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
 * 计划管理程序。
 * <p> 该程序的主要作用是解决计划的规划问题以及笔记整理的问题。该程序主要围绕着两个方向进行编写。
 * <br>&nbsp;&nbsp; 1.笔记记录以及整理工作，此部分类似于现有的有道云笔记等笔记管理程序，在其基础上再添加某些功能。
 * <br>&nbsp;&nbsp; 2.任务管理。通过想程序中输入指定的计划任务，程序根据系统时间来对这些任务进行管理。
 * <p>该类为程序的入口类，遵循单例设计模式，同时具有主方法，调用其主方法就可以启动程序。
 * <p> 该类中的所有方法均不是线程安全的，如需要多线程调用，请使用外部同步。
 * @author DwArFeng
 * @since 1.8
 */
public class Scheduler {

	/**
	 * 使用Builder框架构成的启动器。
	 * @author DwArFeng。
	 * @since 1.8。
	 */
	public static class Luncher{
		
		private String workspacePath = "workspace" + File.separator;
		private boolean showSplash = true;
		
		/**
		 * 初始化启动器
		 */
		public Luncher(){
			//TODO　以后在此添加启动时必要的参数。
		}
		
		/**
		 * 设置程序默认的工作路径。
		 * @param val 默认的工作路径。
		 * @return 启动器自身。
		 */
		public Luncher workspacePath(String val){
			this.workspacePath = val;
			return this;
		}
		
		/**
		 * 设置程序启动时是否显示闪现窗体。
		 * @param val <code>true</code>为显示闪现窗体，反之不显示闪现窗体。
		 * @return 启动器自身。
		 */
		public Luncher showSplash(boolean val){
			this.showSplash = val;
			return this;
		}
		
		/**
		 * 启动程序。
		 * <p> 程序不能连续被启动多次，否则会抛出{@linkplain IllegalStateException}。
		 */
		public void lunch(){
			if(getInstance() != null) throw new IllegalStateException("Scheduler has already run");
			instance = new Scheduler(workspacePath);
			instance.init(showSplash);
		}
		
	}
	
	/**
	 * 返回该类的唯一实例。
	 * <p> 实例只能在程序运行或崩溃的时候被返回。试图在程序未启动或启动中时调用此方法会导致{@linkplain IllegalStateException}异常。
	 * @return 该类的唯一实例。
	 * @throws IllegalStateException 在不正确的状态下调用此方法抛出的异常。
	 */
	public static Scheduler getInstance(){
		return instance;
	}
	
	/**
	 * 表示程序的状态。
	 * @author DwArFeng
	 * @since 1.8
	 */
	public enum State{
		/**代表程序还未启动和初始化的字段*/
		RAW("waiting for start"),
		/**代表程序正在启动的字段*/
		INIT("initializing"),
		/**代表程序正在运行的字段*/
		RUN("running"),
		/**代表程序崩溃的字段*/
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
	/**运行标志，该标志在程序被启动的时候设置为true,之后不再更改*/
	private State state = State.RAW;
	
	/**计划管理类的唯一实例标记*/
	private static Scheduler instance;
	
	/*
	 * 
	 * 以下是成员变量。
	 * 
	 */
	/**长版本号，用于显示在程序自述或者其他的地方*/
	private final String longVersion = "alpha_0.3.1_20160702_build_A";
	/**短版本号，用于显示在标题位置*/
	private final String shortVersion = "alpha_0.3.1";
	/**默认的工作路径*/
	private final String workspacePath;
	/**默认的工程文档目录*/
	private final String archivePath = "archive\\";
	/**程序的配置文件所在的目录*/
	private final String configPath = "config" + File.separator;
	/**存储JTree表的弱映射*/
	private final WeakHashMap<JProjectTree, Object> projectTreeMap = new WeakHashMap<JProjectTree, Object>();
	
	
	/**启动一个新的计划管理器程序*/
	private Scheduler(String workspacePath){
		this.workspacePath = workspacePath;
	}
	
	/**主界面*/
	private SchedulerGui schedulerGui;
	/**崩溃界面*/
	private JCrashFrame jCrashFrame;
	/**闪现窗体*/
	private JSplashWindow jSplashWindow;
	/**程序的有关信息*/
	private FileInfo fileInfo;
	/**正在使用的工程*/
	private Project frontProject;
	
	
	/**
	 * 返回程序的状态。
	 * @return 程序的状态枚举。
	 */
	public State getState(){
		return state;
	}

	/**
	 * 获取程序的短版本。
	 * <p> 短版本号一般用在标题栏当中。
	 * @return 程序的短版本号。
	 */
	public String getShortVersion(){
		return this.shortVersion;
	}
	
	/**
	 * 获取程序的长版本号。
	 * <p> 长版本号一般用于程序自述或需要详细版本信息的地方。
	 * @return 程序的长版本号。
	 */
	public String getLongVersion(){
		return this.longVersion;
	}
	
	/**
	 * 获取程序的默认文档路径。
	 * <p> 默认文档路径是指程序默认的文档存储位置，当生成新的保存或打开的文件对话框中
	 * ，起始文件夹就是默认的文档路径。
	 * @return 程序的默认文档路径。
	 */
	public String getArchivePath(){
		return this.archivePath;
	}
	
	/**
	 * 返回该程序的工作路径目录。
	 * <p> 程序的工作路径目录是存放工程文件解压后的临时文件用的。
	 * @return 程序的工作路径目录。
	 */
	public String getWorkspacePath(){
		return this.workspacePath;
	}
	
	/**
	 * 返回程序的配置路径。
	 * <p> 程序的配置路径是存放程序的外观、配置、将来还有可能是语言的路径。
	 * @return  程序的配置路径。
	 */
	public String getConfigPath(){
		return this.configPath;
	}
	
	
	/**
	 * 获取该程序的前台工程。
	 * @return 该程序的前台工程。
	 */
	public Project getFrontProject(){
		return this.frontProject;
	}
	
	/**
	 * 返回工程的界面。
	 * @return 工程的界面。
	 */
	public SchedulerGui getGui(){
		return schedulerGui;
	}
	
	/**
	 * 获取程序指向的最新的工程路径。
	 * @return 程序指向的最新的工程路径。
	 */
	public String getLastPath(){
		return this.fileInfo.getLastProjectPath();
	}

	/**
	 * 设置前台的工程。
	 * <p> 如果工程是个坏工程的话，程序会还原之前的前台工程，且不会抛出异常。
	 * @param project 设置前台的工程。
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
			CT.trace("由于某些原因，不能设置指定的工程到前台，可能的原因是工程损坏");
			String path = temp == null ? null : ProjectIoHelper.getProjectFile(temp).getAbsolutePath();
			this.fileInfo = new FileInfo.Productor().lastProjectPath(transPath(path)).product();
			this.frontProject = temp;
		}
		schedulerGui.refreshData();
	}
	
	/**
	 * 崩溃的有关方法。
	 * TODO 该方法还在继续完善。
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
	 * 尝试关闭程序。
	 * <p>如果当前程序没有正在进行的工程，则程序正常关闭。
	 * <br>如果当前程序有正在进行的工程，则程序会试图保存当前的工程，然后关闭。
	 * <br>如果保存当前工程时出现异常，程序会询问用户是继续退出还是不退出。
	 */
	public void exitProgram(){
		
		//TODO 需要对不同状态进行判断。
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
								"一个或多个编辑器退出时发生异常，程序退出中止\n"
								+ "请妥善保存数据", 
								"过程中止", 
								JOptionPane.INFORMATION_MESSAGE, 
								null
						);
						//中止退出过程
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
					"工程文件保存时出现了问题，是否要继续退出？\n"
					+ "选择\"是\"退出程序\n"
					+ "选择\"否\"终止结束过程并输出报错信息",
					"保存工程时发生异常", 
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
	 * 对程序中所有与<code>project</code>有关的工程树进行更新，而且展开并选中指定的节点。
	 * @param project 有关的工程。
	 * @param expand 指定的节点。
	 */
	//XXX 是否封装为接口待讨论。
	public void refreshProjectTrees(Project project,ObjectInProjectTree expand){
		
		if(project == null) throw new NullPointerException("Project can't be null");
		
		for(JProjectTree tree:projectTreeMap.keySet()){
			if(project.equals(tree.getProject())){
				tree.refresh(expand);
			}
		}
	}
	
	/**
	 * 转换文件路径。
	 * <p>转换方法如下：
	 * <br>如果文件是相对路径的话，直接返回自身。
	 * <br> 如果文件是绝对路径的话，分析文件是否在程序的自身路径之内，如果是
	 * ，返回与之对应的相对路径；如果不是，则返回绝对路径。
	 * @param pathname 传入的路径。
	 * @return 转换后的路径。
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
		
	/**程序的初始化方法*/
	private void init(boolean showSplash){
		state = State.INIT;
		RunnerQueue.invoke(new Runnable() {
			
			@Override
			public void run() {
				try{
					//记录初始时间
					long l = - System.currentTimeMillis();
					//记录工程信息
					Project project = null;
					//设置外观样式
					UIManager.setLookAndFeel(new NimbusLookAndFeel());
					
					//生成闪现窗体
					if(showSplash){
						jSplashWindow = new JSplashWindow();
						jSplashWindow.setVisible(true);	
					}
					
					//界面初始化
					if(showSplash) jSplashWindow.setMessage("正在初始化界面");
					schedulerGui = new SchedulerGui(ConfigHelper.loadAppearanceInfo());
					projectTreeMap.put(schedulerGui.getProjectTree(),null);
					
					//读取程序信息
					if(showSplash) jSplashWindow.setMessage("正在读取文件信息");
					fileInfo = ConfigHelper.loadFileInfo();
					
					if(showSplash) jSplashWindow.setMessage("读取程序信息...");
					//如果有最后的打开工程，打开这个工程
					if(fileInfo.getLastProjectPath() != null){
						if(showSplash) jSplashWindow.setMessage("打开上一个工程...");
						try{
							CT.trace("正在打开上一个工程");
							project = ProjectIoHelper.loadProject(new File(fileInfo.getLastProjectPath()), ProjectIoHelper.Operate.FOREGROUND);
							CT.trace("工程打开成功");
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
							CT.trace("在读取文件时发生问题，已取消当前文件的自动读取");
						}
					}
					//重绘界面
					setFrontProject(project);
					if(showSplash) jSplashWindow.setMessage("绘制界面...");
					schedulerGui.refreshData();
					//确保闪现窗体生成时间达到2000ms XXX 该参数可以设置为可变的
					if(showSplash){
						l += System.currentTimeMillis();
						if(l > 0 && l < 2000) Thread.sleep(2000-l);
					}
					//释放闪现窗体
					if(showSplash) jSplashWindow.dispose();
					//显示界面
					schedulerGui.setVisible(true);
					schedulerGui.setAlwaysOnTop(true);
					schedulerGui.setAlwaysOnTop(false);
					state = State.RUN;
				}catch(Exception e){
					//调用崩溃方法
					crash();
					//输出错误
					e.printStackTrace();
				}
			}
		});
	}
}
