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
 * 计划管理程序。
 * <p> 该程序的主要作用是解决计划的规划问题以及笔记整理的问题。该程序主要围绕着两个方向进行编写。
 * <br>&nbsp;&nbsp; 1.笔记记录以及整理工作，此部分类似于现有的有道云笔记等笔记管理程序，在其基础上再添加某些功能。
 * <br>&nbsp;&nbsp; 2.任务管理。通过想程序中输入指定的计划任务，程序根据系统时间来对这些任务进行管理。
 * <p>该类为程序的入口类，遵循单例设计模式，同时具有主方法，调用其主方法就可以启动程序。
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
		
		/**
		 * 初始化启动器
		 */
		public Luncher(){
			//TODO
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
		 * 启动程序。
		 * <p> 程序不能连续被启动多次，否则会抛出{@linkplain IllegalStateException}。
		 */
		public void lunch(){
			if(runFlag) throw new IllegalStateException("Scheduler has already been lunched");
			runFlag = true;
			instance = new Scheduler(workspacePath);
		}
		
	}
	
	/**
	 * 返回该类的唯一实例。
	 * @return 该类的唯一实例。
	 */
	public static Scheduler getInstance(){
		if(!runFlag) throw new IllegalStateException("Scheduler hasn't been lunched yet");
		return instance;
	}
	
	/**运行标志，该标志在程序被启动的时候设置为true,之后不再更改*/
	private static boolean runFlag = false;
	/**计划管理类的唯一实例标记*/
	private static Scheduler instance;
	
	
	/*
	 * 
	 * 以下是成员变量。
	 * 
	 */
	/**长版本号，用于显示在程序自述或者其他的地方*/
	private final String longVersion = "alpha_0.2.2_20160610_build_A";
	/**短版本号，用于显示在标题位置*/
	private final String shortVersion = "alpha_0.2.2";
	/**默认的工作路径*/
	private final String workspacePath;
	/**默认的工程文档目录*/
	private final String archivePath = "archive\\";
	/**程序的配置文件所在的目录*/
	private final String configPath = "config" + File.separator;
	
	
	/**启动一个新的计划管理器程序*/
	private Scheduler(String workspacePath){
		this.workspacePath = workspacePath;
		init();
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
	 * 设置前台的工程。
	 * <p> 如果工程是个坏工程的话，程序会还原之前的前台工程，且不会抛出异常。
	 * @param project 设置前台的工程。
	 */
	public void setFrontProject(Project project){
		Project temp = getFrontProject();
		try{
			String path = project == null ? null : ProjectHelper.getProjectFile(project).getAbsolutePath();
			this.fileInfo = new FileInfo.Productor().lastProjectPath(transPath(path)).product();
			this.frontProject = project;
		}catch(Exception e){
			e.printStackTrace();
			CT.trace("由于某些原因，不能设置指定的工程到前台，可能的原因是工程损坏");
			String path = temp == null ? null : ProjectHelper.getProjectFile(temp).getAbsolutePath();
			this.fileInfo = new FileInfo.Productor().lastProjectPath(transPath(path)).product();
			this.frontProject = temp;
		}
		schedulerGui.refreshData();
	}

	/**
	 * 获取程序指向的最新的工程路径。
	 * @return 程序指向的最新的工程路径。
	 */
	public String getLastPath(){
		return this.fileInfo.getLastProjectPath();
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
	
	/**
	 * 询问并删除工程中文件的方法。
	 * @param deleteable
	 */
	public void requestDelete(Deleteable deleteable){
		String confirmWord = deleteable.getConfirmWord() == null ?
				"即将删除工程对象：" + deleteable.toString() + "\n"
				+ "当前操作不可恢复" 
				: 
				deleteable.getConfirmWord();
		
		int sel = JOptionPane.showConfirmDialog(
				schedulerGui,
				confirmWord, 
				"删除确认", 
				JOptionPane.YES_NO_OPTION, 
				JOptionPane.INFORMATION_MESSAGE, 
				null
		);
		if(sel == JOptionPane.YES_OPTION){
			CT.trace("正在删除对象...");
			deleteable.delete();
			CT.trace("对象已经成功的被删除");
		}
	
	}
	
	/**
	 * 将指定的可移动对象在同级之间上移一位的方法。
	 * <p> 顶级元素强制不可移动，并且如果该元素已经位于首位，也不可移动。
	 * @param moveable 指定的可移动对象。
	 */
	public void moveUp(Moveable moveable){
		ObjectInProjectTree parent = moveable.getParent();
		//顶级元素强制不可移动，不管是否实现Moveable接口
		if(parent == null) return;
		int index = parent.getIndex(moveable);
		//如果已经在顶端了，则不可以移动
		if(index == 0)return;
		parent.remove(moveable);
		parent.insert(moveable, index - 1);
	}
	
	/**
	 * 将指定的可移动对象在同级之间下移一位的方法。
	 * <p> 顶级元素强制不可移动，并且如果该元素已经位于首位，也不可移动。
	 * @param moveable 指定的可移动对象。
	 */
	public void moveDown(Moveable moveable){
		ObjectInProjectTree parent = moveable.getParent();
		//顶级元素强制不可移动，不管是否实现Moveable接口
		if(parent == null) return;
		int index = parent.getIndex(moveable);
		//如果已经在末端了，则不可以移动
		if(index == parent.getChildCount()-1)return;
		parent.remove(moveable);
		parent.insert(moveable, index + 1);
	}
	
	/**
	 * 新建一个标准的工程文档。
	 */
	public Project createNewProject(File file ){
		try{
			CT.trace("正在创建新文件...");
			return ProjectHelper.createDefaultProject(file);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 读取指定路径的工程文档。
	 * <p> 在程序中读取工程，该方法要优先于{@linkplain ProjectHelper#loadProject(String, com.dwarfeng.scheduler.io.ProjectHelper.Operate)}
	 * 因为该方法的异常判断更为全面。
	 * @param pathname 指定的路径。
	 * @return 读取的工程。
	 */
	public Project loadProject(String pathname){
		
		CT.trace("正在读取文件");
		Project project = null;
		try{
			project = ProjectHelper.loadProject(pathname, ProjectHelper.Operate.FOREGROUND);
			CT.trace("文件读取成功");
//			this.frontProject = project;
//			this.zipPathname = pathname;
//			this.fileInfo = new FileInfo.Productor().lastProjectPath(transPath(pathname)).product();
			
		}catch(ProjectCantConstructException e){
			//TODO 将来会对异常进行非常详细的判定
//			this.frontProject = null;
//			this.zipPathname = null;
			e.printStackTrace();
		}
		return project;
	}
	
	/**
	 * 保存指定的工程。
	 * <p>将工程位置保存在最新的路径上。
	 * @param project 指定的工程。
	 */
	public void saveProject(Project project){
		if(project == null) return;
		CT.trace("正在将工程压缩存储");
		try{
			ProjectHelper.saveProject(project,ProjectHelper.Operate.FOREGROUND);
		}catch(ProjectCantConstructException e){
			CT.trace("工程存储失败");
		}
	}
	
	/**
	 * 释放指定工程在桌面面板上的所有编辑器。
	 * @param project 指定的工程。
	 */
	public void disposeEditor(Project project){
		if(project == null) return;
		schedulerGui.getDesktopPane().disposeEditor(project);
	}
	
	/**
	 * 关闭（释放）指定的工程。
	 * <p> 该过程不会关闭桌面面板上的编辑器，也不会保存工程，只是单纯的关闭。
	 * 请配合其它方法使用。
	 * @param project 指定的工程。
	 */
	public void closeProject(Project project){
		CT.trace("正在释放工程文件");
		if(project == null) return;
		try{
			ProjectHelper.disposeProject(project);
			CT.trace("工程文件释放完毕");
		}catch(Exception e){
			e.printStackTrace();	//TODO 将来用更好的方式去解决，目前只是单纯的抛出去而已
			CT.trace("由于某些原因，工程没有正常的被关闭");
		}
	}
	
	/**
	 * 崩溃的有关方法。
	 * TODO 该方法还在继续完善。
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
	 * 尝试关闭程序。
	 * <p>如果当前程序没有正在进行的工程，则程序正常关闭。
	 * <br>如果当前程序有正在进行的工程，则程序会试图保存当前的工程，然后关闭。
	 * <br>如果保存当前工程时出现异常，程序会询问用户是继续退出还是不退出。
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
					closeProject(frontProject);
				}finally{
					System.exit(1);
				}
			}else{
				e.printStackTrace();
			}
		}
	}
	
	/**程序的初始化方法*/
	private void init(){
		RunnerQueue.invoke(new Runnable() {
			
			@Override
			public void run() {
				try{
					//记录初始时间
					long l = - System.currentTimeMillis();
					//设置外观样式
					UIManager.setLookAndFeel(new NimbusLookAndFeel());
					//生成闪现窗体
					jSplashWindow = new JSplashWindow();
					jSplashWindow.setVisible(true);		
					//读取文件信息
					fileInfo = ConfigHelper.loadFileInfo();
					//界面初始化
					jSplashWindow.setMessage("初始化界面...");
					schedulerGui = new SchedulerGui(ConfigHelper.loadAppearanceInfo());
					//读取程序信息
					jSplashWindow.setMessage("读取程序信息...");
					//如果有最后的打开工程，打开这个工程
					if(fileInfo.getLastProjectPath() != null){
						Project project = null;
						jSplashWindow.setMessage("打开上一个工程...");
						try{
							project = loadProject(getLastPath());
							setFrontProject(project);
						}catch(Exception e){
							frontProject = null;
						}
					}
					//重绘界面
					jSplashWindow.setMessage("绘制界面...");
					schedulerGui.refreshData();
					//确保闪现窗体生成时间达到2000ms XXX 该参数可以设置为可变的
					l += System.currentTimeMillis();
					if(l > 0 && l < 2000) Thread.sleep(2000-l);
					//释放闪现窗体
					jSplashWindow.dispose();
					//显示界面
					schedulerGui.setVisible(true);
					schedulerGui.setAlwaysOnTop(true);
					schedulerGui.setAlwaysOnTop(false);
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
