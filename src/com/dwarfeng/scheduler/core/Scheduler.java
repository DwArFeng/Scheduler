package com.dwarfeng.scheduler.core;

import java.awt.Image;
import java.util.Locale;
import java.util.Queue;

import com.dwarfeng.dwarffunction.program.MvcProgram;
import com.dwarfeng.dwarffunction.program.Version;
import com.dwarfeng.dwarffunction.threads.NadeRunner;
import com.dwarfeng.dwarffunction.threads.RunnerQueue;
import com.dwarfeng.scheduler.info.ImageKeys;
import com.dwarfeng.scheduler.info.StringFieldKey;
import com.dwarfeng.scheduler.module.SModuleManager;
import com.dwarfeng.scheduler.module.SModuleControlPort;
import com.dwarfeng.scheduler.project.abstruct.SControlPort;
import com.dwarfeng.scheduler.project.abstruct.SViewControlPort;
import com.dwarfeng.scheduler.view.SViewManager;

/**
 * 计划管理程序。
 * <p> 该程序的主要作用是解决计划的规划问题以及笔记整理的问题。该程序主要围绕着两个方向进行编写。
 * <br>&nbsp;&nbsp; 1.笔记记录以及整理工作，此部分类似于现有的有道云笔记等笔记管理程序，在其基础上再添加某些功能。
 * <br>&nbsp;&nbsp; 2.任务管理。通过想程序中输入指定的计划任务，程序根据系统时间来对这些任务进行管理。
 * <p> 程序结构采用MVC结构，并且尽可能的做到相互分离。
 * <br> <code>Scheduler</code>本身拥有一个后台维护器，任何需要调用后台的方法都可以托管到这个维护器上。
 * @author DwArFeng
 * @since 1.8
 */
public final class Scheduler extends MvcProgram<SProgramControlPort, SModuleControlPort, SViewControlPort, SControlPort, SProgramConstField>{
	
	private final SProgramControlPort programControlPort = new SProgramControlPort() {
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.scheduler.typedef.cabstruct.SchedulerControlPort#backgroundInvoke(com.dwarfeng.dwarffunction.threads.NadeRunner)
		 */
		@Override
		public void backgroundInvoke(NadeRunner runner) {
			background.invoke(runner);
		}
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.scheduler.typedef.cabstruct.SchedulerControlPort#getBackgroundQueueSize()
		 */
		@Override
		public int getBackgroundQueueSize() {
			return background.getQueueSize();
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.scheduler.typedef.cabstruct.SchedulerControlPort#getBackgroundQueue()
		 */
		@Override
		public Queue<NadeRunner> getBackgroundQueue() {
			return background.getWaitingQueue();
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.scheduler.core.SProgramControlPort#setLocale(java.util.Locale)
		 */
		@Override
		public void setLocale(Locale locale) {
			stringFields.setLocale(locale);
		}
		
	};
	private final SProgramConstField programConstField = new SProgramConstField() {
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.scheduler.core.SProgramConstField#getVersion()
		 */
		@Override
		public Version getVersion() {
			// TODO Auto-generated method stub
			return null;
		}
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.scheduler.core.SProgramConstField#getAuthor()
		 */
		@Override
		public String getAuthor() {
			// TODO Auto-generated method stub
			return null;
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.scheduler.core.SProgramConstField#getText(com.dwarfeng.scheduler.info.StringFieldKey)
		 */
		@Override
		public String getText(StringFieldKey key) {
			return stringFields.getText(key);
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.scheduler.core.SProgramConstField#getImage(com.dwarfeng.scheduler.info.ImageKeys)
		 */
		@Override
		public Image getImage(ImageKeys key) {
			return imageFields.getImage(key);
		}
		
	};
	
	
	/**有关的后台*/
	private final static RunnerQueue<NadeRunner> background = new RunnerQueue<NadeRunner>();
	


	
	
	
	
	
	

	/**文本字段*/
	private StringFields stringFields = new StringFields();
	

	
	/**图片字段*/
	private ImageFields imageFields = new ImageFields();
	



	
	
	private Scheduler(SModuleManager moduleManager, SViewManager viewManager, SControlManager controlManager){
		super(moduleManager, viewManager, controlManager);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dwarffunction.program.MvcProgram#getProgramControlPort()
	 */
	@Override
	public SProgramControlPort getProgramControlPort() {
		return programControlPort;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dwarffunction.program.mvc.ProgramManager#getProgramConstField()
	 */
	@Override
	public SProgramConstField getProgramConstField() {
		return programConstField;
	}
}
